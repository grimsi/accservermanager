package grimsi.accservermanager.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SystemInfoDto {
    private String version;
    private Boolean metricsEnabled;
    private List<String> supportedAccVersions;
    private int activeInstances;
}
