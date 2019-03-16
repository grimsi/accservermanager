package grimsi.accservermanager.backend.service;

import com.spotify.docker.client.DefaultDockerClient;
import com.spotify.docker.client.DockerClient;
import com.spotify.docker.client.exceptions.DockerException;
import com.spotify.docker.client.messages.*;
import grimsi.accservermanager.backend.configuration.ApplicationConfiguration;
import grimsi.accservermanager.backend.dto.InstanceDto;
import grimsi.accservermanager.backend.enums.OperatingSystem;
import grimsi.accservermanager.backend.exception.CouldNotCreateContainerException;
import grimsi.accservermanager.backend.exception.CouldNotStartContainerException;
import org.modelmapper.internal.bytebuddy.asm.Advice;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ContainerService {

    private final DockerClient docker;
    @Autowired
    InstanceService instanceService;
    @Autowired
    Environment env;
    @Autowired
    ApplicationConfiguration config;
    private Logger log = LoggerFactory.getLogger(ContainerService.class);

    @Autowired
    public ContainerService(UtilityService utilityService) {
        switch (utilityService.getHostOS()){
            case WINDOWS:
                docker = new DefaultDockerClient("http://localhost:2375");
                break;
            case UNIX:
                docker = new DefaultDockerClient("unix:///var/run/docker.sock");
                break;
            case MAC:
                docker = new DefaultDockerClient("unix:///var/run/docker.sock");
                break;
            default:
                docker = new DefaultDockerClient("unix:///var/run/docker.sock");
                break;
        }
    }

    public void deployInstance(InstanceDto instance) {

        String[] ports = {
                String.valueOf(instance.getConfig().getConfigurationJson().getTcpPort()),
                String.valueOf(instance.getConfig().getConfigurationJson().getUdpPort())};

        Map<String, List<PortBinding>> portBindings = new HashMap<>();
        for (String port : ports) {
            portBindings.put(port, Collections.singletonList(PortBinding.of("0.0.0.0", port)));
        }

        HostConfig hostConfig = HostConfig.builder().portBindings(portBindings).build();

       try{
           docker.pull(config.getContainerImage());
       } catch (DockerException | InterruptedException e){
           throw new CouldNotCreateContainerException(e.getMessage());
       }

        final ContainerConfig containerConfig = ContainerConfig.builder()
                .hostConfig(hostConfig)
                .image(config.getContainerImage())
                .exposedPorts(ports)
                .cmd("sh", "-c", "while :; do sleep 1; done")
                .build();

        try {
            String containerName = buildContainerName(instance);
            ContainerCreation container = docker.createContainer(containerConfig, containerName);

            instance.setContainer(container.id());
            instanceService.save(instance);

        } catch (DockerException | InterruptedException e) {
            throw new CouldNotCreateContainerException(e.getMessage());
        }
    }

    public void startInstance(InstanceDto instance) {
        try {
            docker.startContainer(instance.getContainer());
        } catch (DockerException | InterruptedException e) {
            throw new CouldNotStartContainerException(instance.getContainer(), e.getMessage());
        }
    }

    public void stopInstance(InstanceDto instance) {
        try {
            docker.stopContainer(instance.getContainer(), 10);
        } catch (DockerException | InterruptedException e) {
            log.error(e.toString());
        }
    }

    public void pauseInstance(InstanceDto instance) {
        try {
            docker.pauseContainer(instance.getContainer());
        } catch (DockerException | InterruptedException e) {
            log.error(e.toString());
        }
    }

    public void resumeInstance(InstanceDto instance) {

    }

    public ContainerStats getContainerStats(InstanceDto instance) {
        try {
            return docker.stats(instance.getContainer());
        } catch (DockerException | InterruptedException e) {
            log.error(e.toString());
        }
        return null;
    }

    public String buildContainerName(InstanceDto instance){
        String name = "";

        /* Prefix */
        name = name.concat(config.getContainerNamePrefix()).concat("_");

        /* main part */
        name = name.concat(instance.getName());

        /* Postfix */
        if(config.isContainerNamePostfixEnabled()){
            name = name.concat("_").concat(instance.getVersion());
        }

        return name;
    }
}
