package grimsi.accservermanager.backend.dto;

import grimsi.accservermanager.backend.enums.Track;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EventDto {

    private final int configVersion = 1;

    @Id
    private String id;

    @NotBlank(message = "name is required.")
    @Pattern(regexp = "[a-zA-Z0-9_-]*", message = "invalid name. Allowed characters: a-z, A-Z, 0-9, '_', '-'")
    private String name;

    @NotNull(message = "track is required.")
    @Valid
    private Track track;

    @NotNull(message = "preRaceWaitingTimeSeconds is required.")
    private int preRaceWaitingTimeSeconds;

    @NotNull(message = "sessionOverTimeSeconds is required.")
    private int sessionOverTimeSeconds;

    @NotNull(message = "postQualySeconds is required.")
    private int postQualySeconds;

    @NotNull(message = "postQualySeconds is required.")
    private int postRaceSeconds;

    @NotNull(message = "ambientTemp is required.")
    @Min(value = -100, message = "ambientTemp has to be between -100 and 100.")
    @Max(value = 100, message = "ambientTemp has to be between -100 and 100.")
    private int ambientTemp;

    @NotNull(message = "trackTemp is required.")
    @Min(value = -100, message = "trackTemp has to be between -100 and 100.")
    @Max(value = 100, message = "trackTemp has to be between -100 and 100.")
    private int trackTemp;

    @NotNull(message = "cloudLevel is required.")
    @DecimalMin(value = "0", message = "cloudLevel has to be between 0 and 1.")
    @DecimalMax(value = "1", message = "cloudLevel has to be between 0 and 1.")
    private BigDecimal cloudLevel;

    @NotNull(message = "rain is required.")
    @DecimalMin(value = "0", message = "rain has to be between 0 and 1.")
    @DecimalMax(value = "1", message = "rain has to be between 0 and 1.")
    private BigDecimal rain;

    @NotNull(message = "weatherRandomness is required.")
    @DecimalMin(value = "0", message = "weatherRandomness has to be between 0 and 10.")
    @DecimalMax(value = "10", message = "weatherRandomness has to be between 0 and 10.")
    private BigDecimal weatherRandomness;

    @DecimalMin(value = "0", message = "simracerWeatherConditions has to be between 0 and 3.")
    @DecimalMax(value = "3", message = "simracerWeatherConditions has to be between 0 and 3.")
    private int simracerWeatherConditions;

    @DecimalMin(value = "0", message = "isFixedConditionQualification has to be between 0 and 1.")
    @DecimalMax(value = "1", message = "isFixedConditionQualification has to be between 0 and 1.")
    private int isFixedConditionQualification;

    @NotNull(message = "sessions is required.")
    @Valid
    private List<SessionDto> sessions;
}
