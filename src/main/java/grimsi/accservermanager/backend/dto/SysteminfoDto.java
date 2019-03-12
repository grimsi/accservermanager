package grimsi.accservermanager.backend.dto;

import org.springframework.validation.annotation.Validated;

@Validated
public class SysteminfoDto {
  private String version;
  private String accVersion;
  private Boolean metricsEnabled;
}
