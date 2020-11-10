package grimsi.accservermanager.backend.service;

import grimsi.accservermanager.backend.dto.SystemInfoDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

    @Value("${build.version}")
    private String buildVersion;

    public SystemInfoDto getSystemInfo() {
        SystemInfoDto systemInfo = new SystemInfoDto();

        systemInfo.setVersion(buildVersion);
        systemInfo.setSupportedAccVersions(fileSystemService.getInstalledServerVersions());
        systemInfo.setMetricsEnabled(areMetricsEnabled());
        systemInfo.setActiveInstances(instanceService.getActiveInstanceCount());

        return systemInfo;
    }

    private boolean areMetricsEnabled() {
        return !env.getProperty("management.server.port", String.class).isEmpty();
    }
}
