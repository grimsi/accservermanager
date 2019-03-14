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
public class SettingsJsonDto {

    private String id;

    @NotBlank(message = "serverName is required.")
    private String serverName;

    private String password;

    private String adminPassword;

    private int configVersion = 1;
}
