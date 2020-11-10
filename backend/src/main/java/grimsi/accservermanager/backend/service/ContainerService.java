package grimsi.accservermanager.backend.service;

import com.google.gson.Gson;
import com.spotify.docker.client.DefaultDockerClient;
import com.spotify.docker.client.DockerClient;
import com.spotify.docker.client.exceptions.DockerCertificateException;
import com.spotify.docker.client.exceptions.DockerException;
import com.spotify.docker.client.messages.*;
import grimsi.accservermanager.backend.configuration.ApplicationConfiguration;
import grimsi.accservermanager.backend.dto.InstanceDto;
import grimsi.accservermanager.backend.exception.ContainerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ContainerService {

    @Autowired
    InstanceService instanceService;
    @Autowired
    FileSystemService fileSystemService;
    @Autowired
    Environment env;
    @Autowired
    ApplicationConfiguration config;
    @Autowired
    Gson gson;
    private DockerClient docker;
    private Logger log = LoggerFactory.getLogger(ContainerService.class);

    public ContainerService() {
        try {
            docker = DefaultDockerClient.fromEnv().build();
        } catch (DockerCertificateException e) {
            log.error(e.getMessage());
        }
    }

    public List<Container> getAllContainers() {
        try {
            return docker.listContainers(DockerClient.ListContainersParam.allContainers());
        } catch (DockerException e) {
            throw new ContainerException("Could not get a list of containers: " + e.getMessage());
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        return Collections.emptyList();
    }

    public void deployInstance(InstanceDto instance) {

        String[] ports = {
                (instance.getConfiguration().getTcpPort() + "/tcp"),
                (instance.getConfiguration().getUdpPort() + "/udp")
        };

        Map<String, List<PortBinding>> portBindings = new HashMap<>();
        for (String port : ports) {
            portBindings.put(port, Collections.singletonList(PortBinding.of("0.0.0.0", port)));
        }

        try {
            List<Image> images = docker.listImages().stream().filter(
                    image -> image.repoTags().stream()
                            .anyMatch(tag -> tag.equals(config.getContainerImage())
                            )
            ).collect(Collectors.toList());

            if (images.isEmpty()) {
                docker.pull(config.getContainerImage());
            }
        } catch (DockerException e) {
            throw new ContainerException("Could not find/pull image '" + config.getContainerImage() + "': " + e.getMessage());
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        log.debug("Mounting dir: '" + fileSystemService.getInstanceFolderPath(instance, true) + ":" + config.getFolderPathContainerized() + "'.");

        HostConfig hostConfig = HostConfig.builder()
                .portBindings(portBindings)
                .binds(fileSystemService.getInstanceFolderPath(instance, true) + ":" + config.getFolderPathContainerized())
                .restartPolicy(HostConfig.RestartPolicy.unlessStopped())
                .build();

        ContainerConfig containerConfig = ContainerConfig.builder()
                .hostConfig(hostConfig)
                .image(config.getContainerImage())
                .exposedPorts(ports)
                .build();

        try {
            log.debug("Container config: \n\n" + gson.toJson(containerConfig) + "\n\n");

            String containerName = buildContainerName(instance);
            ContainerCreation container = docker.createContainer(containerConfig, containerName);

            instance.setContainer(container.id());
            instanceService.save(instance);

        } catch (DockerException e) {
            throw new ContainerException("Could not create container for instance '" + instance.getId() + "': " + e.getMessage());
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public void startInstance(InstanceDto instance) {
        try {
            docker.startContainer(instance.getContainer());
        } catch (DockerException e) {
            throw new ContainerException("Could not start container '" + instance.getContainer() + "': " + e.getMessage());
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public void stopInstance(InstanceDto instance) {
        try {
            docker.stopContainer(instance.getContainer(), 0);
        } catch (DockerException e) {
            throw new ContainerException("Could not stop container '" + instance.getContainer() + "': " + e.getMessage());
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public void pauseInstance(InstanceDto instance) {
        try {
            docker.pauseContainer(instance.getContainer());
        } catch (DockerException e) {
            throw new ContainerException("Could not pause container '" + instance.getContainer() + "': " + e.getMessage());
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public void resumeInstance(InstanceDto instance) {
        try {
            docker.unpauseContainer(instance.getContainer());
        } catch (DockerException e) {
            throw new ContainerException("Could not resume container '" + instance.getContainer() + "': " + e.getMessage());
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public void deleteInstance(InstanceDto instance) {
        deleteContainer(instance.getContainer());
    }

    public void deleteContainer(String id) {
        try {
            docker.stopContainer(id, 0);
            docker.removeContainer(id);
        } catch (DockerException | NullPointerException e) {
            throw new ContainerException("Cant delete container '" + id + "': " + e.getMessage());
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public void pullImage(String imageName) {
        try {
            docker.pull(imageName);
        } catch (DockerException e) {
            throw new ContainerException("Cant pull image '" + imageName + "': " + e.getMessage());
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public boolean instanceHasContainer(InstanceDto instance) {
        List<Container> containers = getAllContainers();
        return containers.stream().anyMatch(
                container -> (container.id().equals(instance.getContainer())
                )) && !containers.isEmpty();
    }

    public ContainerStats getContainerStats(InstanceDto instance) {
        try {
            return docker.stats(instance.getContainer());
        } catch (DockerException e) {
            log.error(e.toString());
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        return null;
    }

    public String buildContainerName(InstanceDto instance) {
        /* main part */
        String name = instance.getName();

        /* Postfix */
        if (config.isContainerNamePostfixEnabled()) {
            name = name.concat("-v").concat(instance.getVersion());
        }

        return name;
    }
}
