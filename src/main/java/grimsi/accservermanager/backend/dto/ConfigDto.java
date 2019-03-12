package grimsi.accservermanager.backend.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.validation.annotation.Validated;

@Getter
@Setter
@Validated
public class ConfigDto {
  @Id
  private Long id;
  private String name;
  private String tag;
}
