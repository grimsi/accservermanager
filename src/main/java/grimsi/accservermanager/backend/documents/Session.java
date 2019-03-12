package grimsi.accservermanager.backend.documents;

import grimsi.accservermanager.backend.enums.SessionType;

public class Session {
    public int hourOfDay;
    public int dayOfWeekend;
    public float timeMultiplier;
    public SessionType sessionType;
    public int sessionDurationMinutes;
}
