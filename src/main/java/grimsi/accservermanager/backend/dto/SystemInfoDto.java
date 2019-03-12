package grimsi.accservermanager.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class SystemInfoDto {
    private String version;
    private String accVersion;
    private Boolean metricsEnabled;
}
