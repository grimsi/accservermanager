package grimsi.accservermanager.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class SettingsJsonDto {
    private String serverName;
    private String password;
    private String adminPassword;
    private int configVersion = 1;
}
