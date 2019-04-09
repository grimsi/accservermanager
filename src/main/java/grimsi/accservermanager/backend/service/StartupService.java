package grimsi.accservermanager.backend.service;

import com.spotify.docker.client.messages.Container;
import grimsi.accservermanager.backend.configuration.ApplicationConfiguration;
import grimsi.accservermanager.backend.dto.InstanceDto;
import grimsi.accservermanager.backend.dto.UserDto;
import grimsi.accservermanager.backend.enums.InstanceState;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class StartupService {

    private static final Logger log = LoggerFactory.getLogger(StartupService.class);

    @Autowired
    ApplicationConfiguration config;

    @Autowired
    UserService userService;

    @Autowired
    InstanceService instanceService;

    @Autowired
    FileSystemService fileSystemService;

    @Autowired
    ContainerService containerService;

    public void initialize() {

        initUsers();
        log.info("Created user with username '" + config.getUsername() + "' and user-defined password.");

        initFileSystem();
        log.info("Initialized filesystem.");

        initInstances();
        log.info("Initialized instances.");

        initContainers();
        log.info("Initialized containers.");
    }

    private void initFileSystem() {
        fileSystemService.initFileSystem();
    }

    private void initUsers() {
        userService.deleteAllUsers();
        userService.registerUser(new UserDto(config.getUsername(), config.getPassword()));
    }

    private void initInstances() {

        /* Pull the latest image version */
        log.info("Pulling latest container images. This may take a while.");
        containerService.pullImage(config.getContainerImage());

        List<InstanceDto> instances = instanceService.findAll();

        /* Delete all instances that dont have a folder (maybe the user deleted the folder) */
        instances.stream().filter(
                instance -> !fileSystemService.instanceHasValidFolder(instance)
        ).forEach(
                instance -> {
                    boolean containerRestartNecessary = instance.getState() == InstanceState.RUNNING && containerService.instanceHasContainer(instance);

                    if (containerRestartNecessary) {
                        log.warn("Folder of instance '" + instance.getName() + "' is corrupt. Stopping container to recreate the folder.");
                        containerService.stopInstance(instance);
                    }

                    fileSystemService.createInstanceFolder(instance);
                    log.warn("Folder of instance '" + instance.getName() + "' was corrupt, recreated the folder.");

                    if (containerRestartNecessary) {
                        log.warn("Fixed folder of instance '" + instance.getName() + ". Restarting the container.");
                        containerService.startInstance(instance);
                    }
                }
        );

        instances = instanceService.findAll();

        /* Check if every instance has a container and if not, create it (maybe the user deleted the container) */
        instances.stream().filter(
                instance -> (!containerService.instanceHasContainer(instance))
        ).forEach(
                instance -> {
                    containerService.deployInstance(instance);
                    log.warn("Could not find container for instance '" + instance.getName() + "', deployed new container.");

                    if (instance.getState() == InstanceState.RUNNING) {
                        containerService.startInstance(instance);
                        log.warn("Started container for instance '" + instance.getName() + "'.");
                    }
                }
        );
    }

    private void initContainers() {

        List<InstanceDto> instances = instanceService.findAll();

        /* Get a list of all containers that use the accServer container image but are not assigned to any instance */
        List<Container> unassignedContainers = containerService.getAllContainers().stream().filter(
                container -> (
                        container.image().equals(config.getContainerImage()) &&
                                (instances.isEmpty() ||
                                        instances.stream().noneMatch(
                                                instance -> instance.getContainer().equals(container.id())
                                        )
                                )
                )
        ).collect(Collectors.toList());

        /* now delete all unassigned containers or just warn the user of unassigned containers depending on the configuraion */
        if (config.isDeleteUnassignedContainersEnabled() && !unassignedContainers.isEmpty()) {
            unassignedContainers.stream().forEach(
                    container -> {
                        containerService.deleteContainer(container.id());
                        log.warn("Could not find instance for container '" + container.id() + "', deleting container.\n" +
                                "You can change this behaviour by setting the configuration property 'accservermanager.delete-unassigned-containers-enabled' to 'false'.");
                    }
            );
        } else if (!unassignedContainers.isEmpty()) {
            String warning = "The following containers are not assigned to any instance in the system, but use the '" + config.getContainerImage() + "' image.\n" +
                    "This can lead to errors in the ACC Server Manager, for example if the container shares a name with an instance.\n" +
                    "Set the configuration property 'accservermanager.delete-unassigned-containers-enabled' to 'true' to automatically delete such containers to prevent errors.";

            for (Container c : unassignedContainers) {
                warning = warning.concat("\nContainer Name: " + c.names().get(0).substring(1) + "\t\tContainer ID: " + c.id());
            }

            log.warn(warning);
        }
    }

}
