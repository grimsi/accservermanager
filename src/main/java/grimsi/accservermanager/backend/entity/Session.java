package grimsi.accservermanager.backend.entity;

import grimsi.accservermanager.backend.enums.SessionType;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class Session {
    public int hourOfDay;
    public int dayOfWeekend;
    public BigDecimal timeMultiplier;
    public SessionType sessionType;
    public int sessionDurationMinutes;
}
