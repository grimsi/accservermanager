package grimsi.accservermanager.backend.dto;

import grimsi.accservermanager.backend.enums.SessionType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class SessionDto {
    private int hourOfDay;
    private int dayOfWeekend;
    private float timeMultiplier;
    private SessionType sessionType;
    private int sessionDurationMinutes;
}
