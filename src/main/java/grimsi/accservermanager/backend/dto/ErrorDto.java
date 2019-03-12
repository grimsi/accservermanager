package grimsi.accservermanager.backend.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.validation.annotation.Validated;

@Getter
@Setter
@Validated
public class ErrorDto {
  private Integer code;
  private String message;
}
