package grimsi.accservermanager.backend.entity;

import grimsi.accservermanager.backend.enums.SessionType;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class Session {
    private int hourOfDay;
    private int dayOfWeekend;
    private BigDecimal timeMultiplier;
    private SessionType sessionType;
    private int sessionDurationMinutes;
}
