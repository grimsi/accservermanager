package grimsi.accservermanager.backend.entity;

import grimsi.accservermanager.backend.enums.SessionType;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class Session {
    private int hourOfDay;
    private int dayOfWeekend;
    private int timeMultiplier;
    private SessionType sessionType;
    private int sessionDurationMinutes;
}
