package grimsi.accservermanager.backend.dto;

import grimsi.accservermanager.backend.enums.InstanceState;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.validation.annotation.Validated;

@Getter
@Setter
@Validated
public class InstanceDto {
  @Id
  private String id;
  private String name;
  private InstanceState state;
  private String container;
  private ConfigDto config;
}
