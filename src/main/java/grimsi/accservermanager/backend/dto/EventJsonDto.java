package grimsi.accservermanager.backend.dto;

import grimsi.accservermanager.backend.enums.EventType;
import grimsi.accservermanager.backend.enums.Track;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EventJsonDto {

    @NotNull(message = "track is required.")
    @Valid
    private Track track;

    @NotNull(message = "eventType is required.")
    @Valid
    private EventType eventType;

    @NotNull(message = "preRaceWaitingTimeSeconds is required.")
    private int preRaceWaitingTimeSeconds;

    @NotNull(message = "sessionOverTimeSeconds is required.")
    private int sessionOverTimeSeconds;

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

    @NotNull(message = "sessions is required.")
    @Valid
    private List<SessionDto> sessions;

    private int configVersion = 1;
}
