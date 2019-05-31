package grimsi.accservermanager.backend.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Settings {
    private String serverName;
    private String password;
    private String adminPassword;
    private int spectatorSlots;
    private String spectatorPassword;
    private int trackMedalsRequirement;
    private int safetyRatingRequirement;
    private int racecraftRatingRequirement;
    private int configVersion = 1;
}
