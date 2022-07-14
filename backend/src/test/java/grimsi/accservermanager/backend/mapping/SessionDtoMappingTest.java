package grimsi.accservermanager.backend.mapping;

import grimsi.accservermanager.backend.dto.SessionDto;
import grimsi.accservermanager.backend.entity.Session;
import grimsi.accservermanager.backend.enums.SessionType;
import grimsi.accservermanager.backend.interfaces.MappingUnitTest;
import org.junit.Test;
import org.modelmapper.ModelMapper;


import static org.junit.Assert.assertEquals;

public class SessionDtoMappingTest implements MappingUnitTest {

    private ModelMapper modelMapper = new ModelMapper();

    @Test
    public void convertEntityToDto() {
        Session session = new Session();
        session.setHourOfDay(12);
        session.setDayOfWeekend(1);
        session.setTimeMultiplier(0);
        session.setSessionType(SessionType.P);
        session.setSessionDurationMinutes(20);

        SessionDto sessionDto = modelMapper.map(session, SessionDto.class);

        checkMapping(session, sessionDto);
    }

    @Test
    public void convertDtoToEntity() {
        SessionDto sessionDto = new SessionDto();
        sessionDto.setHourOfDay(12);
        sessionDto.setDayOfWeekend(1);
        sessionDto.setTimeMultiplier(0);
        sessionDto.setSessionType(SessionType.P);
        sessionDto.setSessionDurationMinutes(20);

        Session session = modelMapper.map(sessionDto, Session.class);

        checkMapping(session, sessionDto);
    }

    private void checkMapping(Session session, SessionDto sessionDto) {
        assertEquals(session.getHourOfDay(), sessionDto.getHourOfDay());
        assertEquals(session.getDayOfWeekend(), sessionDto.getDayOfWeekend());
        assertEquals(session.getTimeMultiplier(), sessionDto.getTimeMultiplier());
        assertEquals(session.getSessionType(), sessionDto.getSessionType());
        assertEquals(session.getSessionDurationMinutes(), sessionDto.getSessionDurationMinutes());
    }
}
