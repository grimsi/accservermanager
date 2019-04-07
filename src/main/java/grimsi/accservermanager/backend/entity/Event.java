package grimsi.accservermanager.backend.entity;

import grimsi.accservermanager.backend.enums.EventType;
import grimsi.accservermanager.backend.enums.Track;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.util.List;

@Document("event")
@Getter
@Setter
public class Event {
    @Id
    public String id;

    @Indexed(unique = true)
    public String name;

    public Track track;
    public EventType eventType;
    public int preRaceWaitingTimeSeconds;
    public int sessionOverTimeSeconds;
    public int ambientTemp;
    public int trackTemp;
    public BigDecimal cloudLevel;
    public BigDecimal rain;
    public BigDecimal weatherRandomness;
    public List<Session> sessions;
    public int configVersion = 1;
}
