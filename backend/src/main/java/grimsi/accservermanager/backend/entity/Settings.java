package grimsi.accservermanager.backend.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Settings {
    private String serverName;
    private String password;
    private String adminPassword;
    private int trackMedalsRequirement;
    private int safetyRatingRequirement;
    private int configVersion = 1;
    private int ignorePrematureDisconnects;
}
