package grimsi.accservermanager.backend.mapping;

import grimsi.accservermanager.backend.dto.EventDto;
import grimsi.accservermanager.backend.entity.Event;
import grimsi.accservermanager.backend.enums.Track;
import grimsi.accservermanager.backend.interfaces.MappingUnitTest;
import org.junit.Test;
import org.modelmapper.ModelMapper;

import java.math.BigDecimal;
import java.util.Collections;

import static org.junit.Assert.assertEquals;

public class EventDtoMappingTest implements MappingUnitTest {

    private ModelMapper modelMapper = new ModelMapper();

    @Test
    public void convertEntityToDto() {
        Event event = new Event();
        event.setId("123");
        event.setName("testEvent");
        event.setTrack(Track.hungaroring);
        event.setPreRaceWaitingTimeSeconds(30);
        event.setSessionOverTimeSeconds(30);
        event.setAmbientTemp(25);
        event.setTrackTemp(25);
        event.setCloudLevel(new BigDecimal("0.5"));
        event.setRain(new BigDecimal("0.5"));
        event.setWeatherRandomness(new BigDecimal("2"));
        event.setSessions(Collections.emptyList());

        EventDto eventDto = modelMapper.map(event, EventDto.class);

        checkMapping(event, eventDto);
    }

    @Test
    public void convertDtoToEntity() {
        EventDto eventDto = new EventDto();
        eventDto.setId("123");
        eventDto.setName("testEvent");
        eventDto.setTrack(Track.hungaroring);
        eventDto.setPreRaceWaitingTimeSeconds(30);
        eventDto.setSessionOverTimeSeconds(30);
        eventDto.setAmbientTemp(25);
        eventDto.setTrackTemp(25);
        eventDto.setCloudLevel(new BigDecimal("0.5"));
        eventDto.setRain(new BigDecimal("0.5"));
        eventDto.setWeatherRandomness(new BigDecimal("2"));
        eventDto.setSessions(Collections.emptyList());

        Event event = modelMapper.map(eventDto, Event.class);

        checkMapping(event, eventDto);
    }

    private void checkMapping(Event event, EventDto eventDto) {
        assertEquals(event.getConfigVersion(), eventDto.getConfigVersion());
        assertEquals(event.getId(), eventDto.getId());
        assertEquals(event.getName(), eventDto.getName());
        assertEquals(event.getTrack(), eventDto.getTrack());
        assertEquals(event.getPreRaceWaitingTimeSeconds(), eventDto.getPreRaceWaitingTimeSeconds());
        assertEquals(event.getSessionOverTimeSeconds(), eventDto.getSessionOverTimeSeconds());
        assertEquals(event.getAmbientTemp(), eventDto.getAmbientTemp());
        assertEquals(event.getTrackTemp(), eventDto.getTrackTemp());
        assertEquals(event.getCloudLevel(), eventDto.getCloudLevel());
        assertEquals(event.getRain(), eventDto.getRain());
        assertEquals(event.getWeatherRandomness(), eventDto.getWeatherRandomness());
        assertEquals(event.getSessions(), eventDto.getSessions());
    }
}
