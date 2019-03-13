package grimsi.accservermanager.backend.documents;

import grimsi.accservermanager.backend.enums.EventType;
import grimsi.accservermanager.backend.enums.Track;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document("event")
public class EventJson {
    @Id
    public String id;

    public String name;
    public Track track;
    public EventType eventType;
    public int preRaceWaitingTimeSeconds;
    public int sessionOverTimeSeconds;
    public int ambientTemp;
    public int trackTemp;
    public float cloudLevel;
    public float rain;
    public List<Session> sessions;
    public int configVersion = 1;
}