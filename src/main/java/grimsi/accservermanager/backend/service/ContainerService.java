package grimsi.accservermanager.backend.service;

import com.spotify.docker.client.DefaultDockerClient;
import com.spotify.docker.client.DockerClient;
import com.spotify.docker.client.exceptions.DockerException;
import com.spotify.docker.client.messages.*;
import grimsi.accservermanager.backend.configuration.ApplicationConfiguration;
import grimsi.accservermanager.backend.dto.InstanceDto;
import grimsi.accservermanager.backend.dto.InstanceStatsDto;
import grimsi.accservermanager.backend.enums.InstanceState;
import grimsi.accservermanager.backend.exception.ContainerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

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

    public ContainerService() {
        docker = DefaultDockerClient.builder().build();
    }

    public void deployInstance(InstanceDto instance) {

        String[] ports = {
                String.valueOf(instance.getConfig().getConfigurationJson().getTcpPort()),
                String.valueOf(instance.getConfig().getConfigurationJson().getUdpPort()),
                env.getProperty("management.server.port", String.class)};

        Map<String, List<PortBinding>> portBindings = new HashMap<>();
        for (String port : ports) {
            portBindings.put(port, Collections.singletonList(PortBinding.of("0.0.0.0", port)));
        }

        HostConfig hostConfig = HostConfig.builder().portBindings(portBindings).build();

        ContainerConfig containerConfig = ContainerConfig.builder()
                .image(config.getContainerImage())
                .hostConfig(hostConfig)
                .exposedPorts(ports)
                .build();

        try {
            ContainerCreation container = docker.createContainer(containerConfig, instance.getName());

            instance.setContainer(container.id());
            instanceService.updateById(instance.getId(), instance);

        } catch (DockerException | InterruptedException e) {
            log.error(e.toString());
        }
    }

    public void startInstance(InstanceDto instance) {
        try {
            docker.startContainer(instance.getContainer());
        } catch (DockerException | InterruptedException e) {
            log.error(e.toString());
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

    public void resumeInstance(InstanceDto instance) throws ContainerException {
        if (instance.getState() != InstanceState.PAUSED) {
            throw new ContainerException("Only paused instances can be resumed");
        } else {
            startInstance(instance);
        }
    }

    public ContainerStats getContainerStats(InstanceDto instance) {
        try {
            return docker.stats(instance.getContainer());
        } catch (DockerException | InterruptedException e) {
            log.error(e.toString());
        }
        return null;
    }

    public InstanceStatsDto getInstanceStats() {
        throw new NotImplementedException();
    }
}
