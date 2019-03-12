package grimsi.accservermanager.backend.dto;

import grimsi.accservermanager.backend.enums.EventType;
import grimsi.accservermanager.backend.enums.Track;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class EventJsonDto {
    private Track track;
    private EventType eventType;
    private int preRaceWaitingTimeSeconds;
    private int sessionOverTimeSeconds;
    private int ambientTemp;
    private int trackTemp;
    private float cloudLevel;
    private float rain;
    private List<SessionDto> sessions;
    private int configVersion = 1;
}
