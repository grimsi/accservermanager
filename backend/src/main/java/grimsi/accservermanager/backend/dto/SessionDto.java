package grimsi.accservermanager.backend.dto;

import grimsi.accservermanager.backend.enums.SessionType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SessionDto {

    @NotNull(message = "hourOfDay is required.")
    @Min(value = 0, message = "hourOfDay has to be between 0 and 23.")
    @Max(value = 23, message = "hourOfDay has to be between 0 and 23.")
    private int hourOfDay;

    @NotNull(message = "dayOfWeekend is required.")
    @Min(value = 1, message = "dayOfWeekend has to be between 1 and 3.")
    @Max(value = 3, message = "dayOfWeekend has to be between 1 and 3.")
    private int dayOfWeekend;

    @NotNull(message = "timeMultiplier is required.")
    @Min(value = 0, message = "timeMultiplier has to be between 0 and 24.")
    @Max(value = 24, message = "timeMultiplier has to be between 0 and 24")
    private int timeMultiplier;

    @NotNull(message = "sessionType is required.")
    @Valid
    private SessionType sessionType;

    @NotNull(message = "sessionDurationMinutes is required.")
    @Min(value = 1, message = "sessionDurationMinutes has to be at least 1.")
    private int sessionDurationMinutes;
}
