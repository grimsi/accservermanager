package grimsi.accservermanager.backend.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Settings {
    public String serverName;
    public String password;
    public String adminPassword;
    public int trackMedalsRequirement;
    public int safetyRatingRequirement;
    public int configVersion = 1;
}
