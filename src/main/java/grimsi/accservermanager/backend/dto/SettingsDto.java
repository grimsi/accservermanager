package grimsi.accservermanager.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SettingsDto {
    @NotBlank(message = "serverName is required.")
    private String serverName;

    private String password;

    @NotBlank(message = "adminPassword is required.")
    private String adminPassword;

    private int configVersion = 1;
}
