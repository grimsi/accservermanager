package grimsi.accservermanager.backend.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Settings {
    private String serverName;
    private String password;
    private String adminPassword;
    private String carGroup;
    private int trackMedalsRequirement;
    private int safetyRatingRequirement;
    private int configVersion = 1;
    private int racecraftRatingRequirement;
    private int maxCarSlots;
    private int isRaceLocked;
    private int allowAutoDQ;
    private int shortFormationLap;
    private int formationLapType;
    private int ignorePrematureDisconnects;
}
