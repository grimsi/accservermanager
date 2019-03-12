package grimsi.accservermanager.backend.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.validation.annotation.Validated;

@Getter
@Setter
@Validated
public class UserDto {
  private String username;
  private String password;

  public UserDto(){
  }

  public UserDto(String username, String password) {
    this.username = username;
    this.password = password;
  }
}
