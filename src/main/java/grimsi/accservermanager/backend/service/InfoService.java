package grimsi.accservermanager.backend.service;

import grimsi.accservermanager.backend.dto.SystemInfoDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

@Service
public class InfoService {

    @Autowired
    Environment env;

    @Autowired
    FileSystemService fileSystemService;

    @Autowired
    InstanceService instanceService;

    public SystemInfoDto getSystemInfo(){
        SystemInfoDto systemInfo = new SystemInfoDto();

        // TODO: get maven build-info plugin to work so this can be dynamically loaded
        systemInfo.setVersion("0.1.0");
        systemInfo.setSupportedAccVersions(fileSystemService.getInstalledServerVersions());
        systemInfo.setMetricsEnabled(this.areMetricsEnabled());
        systemInfo.setActiveInstances(instanceService.getActiveInstanceCount());

        return systemInfo;
    }

    private boolean areMetricsEnabled(){
        return !env.getProperty("management.server.port", String.class).isEmpty();
    }
}
