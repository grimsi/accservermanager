package grimsi.accservermanager.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SettingsDto {

    private final int configVersion = 1;

    @NotBlank(message = "serverName is required.")
    private String serverName;

    private String password;

    @NotBlank(message = "adminPassword is required.")
    private String adminPassword;

    @NotNull(message = "trackMedalsRequirement is required.")
    @Min(value = 0, message = "trackMedalsRequirement has to be between 0 and 3.")
    @Max(value = 3, message = "trackMedalsRequirement has to be between 0 and 3.")
    private int trackMedalsRequirement;

    @NotNull(message = "safetyRatingRequirement is required.")
    @Min(value = -1, message = "safetyRatingRequirement has to be between -1 and 99.")
    @Max(value = 99, message = "safetyRatingRequirement has to be between -1 and 99.")
    private int safetyRatingRequirement;

    @NotNull(message = "ignorePrematureDisconnects is required.")
    @Min(value = 0, message = "ignorePrematureDisconnects has to be between 0 and 1.")
    @Max(value = 1, message = "ignorePrematureDisconnects has to be between 0 and 1.")
    private int ignorePrematureDisconnects;
}
