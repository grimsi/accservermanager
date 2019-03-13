package grimsi.accservermanager.backend.documents;

import grimsi.accservermanager.backend.enums.SessionType;
import org.springframework.data.annotation.Id;

public class Session {
    @Id
    public String id;

    public String name;
    public int hourOfDay;
    public int dayOfWeekend;
    public float timeMultiplier;
    public SessionType sessionType;
    public int sessionDurationMinutes;
}
