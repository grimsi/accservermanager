package grimsi.accservermanager.backend.dto;

import grimsi.accservermanager.backend.enums.SessionType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

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
    @Min(value = 0, message = "dayOfWeekend has to be between 0 and 2.")
    @Max(value = 2, message = "dayOfWeekend has to be between 0 and 2.")
    private int dayOfWeekend;

    @NotNull(message = "timeMultiplier is required.")
    @DecimalMin(value = "0.1", message = "timeMultiplier has to be at least 0.1.")
    private BigDecimal timeMultiplier;

    @NotNull(message = "sessionType is required.")
    @Valid
    private SessionType sessionType;

    @NotNull(message = "sessionDurationMinutes is required.")
    @Min(value = 1, message = "sessionDurationMinutes has to be at least 1.")
    private int sessionDurationMinutes;
}
