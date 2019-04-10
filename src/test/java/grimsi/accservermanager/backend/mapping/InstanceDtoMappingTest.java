package grimsi.accservermanager.backend.mapping;

import grimsi.accservermanager.backend.dto.ConfigurationDto;
import grimsi.accservermanager.backend.dto.EventDto;
import grimsi.accservermanager.backend.dto.InstanceDto;
import grimsi.accservermanager.backend.dto.SettingsDto;
import grimsi.accservermanager.backend.entity.Configuration;
import grimsi.accservermanager.backend.entity.Event;
import grimsi.accservermanager.backend.entity.Instance;
import grimsi.accservermanager.backend.entity.Settings;
import grimsi.accservermanager.backend.enums.InstanceState;
import grimsi.accservermanager.backend.interfaces.MappingUnitTest;
import org.junit.Test;
import org.modelmapper.ModelMapper;

import static org.junit.Assert.assertEquals;

public class InstanceDtoMappingTest implements MappingUnitTest {

    private ModelMapper modelMapper = new ModelMapper();

    @Test
    public void convertEntityToDto() {
        Instance instance = new Instance();
        instance.setId("123");
        instance.setRestartRequired(false);
        instance.setName("testInstance");
        instance.setState(InstanceState.STOPPED);
        instance.setContainer("testContainer");
        instance.setConfiguration(new Configuration());
        instance.setSettings(new Settings());
        instance.setEvent(new Event());
        instance.setVersion("1.0.0");

        InstanceDto instanceDto = modelMapper.map(instance, InstanceDto.class);

        checkMapping(instance, instanceDto);
    }

    @Test
    public void convertDtoToEntity() {
        InstanceDto instanceDto = new InstanceDto();
        instanceDto.setId("123");
        instanceDto.setRestartRequired(false);
        instanceDto.setName("testInstance");
        instanceDto.setState(InstanceState.STOPPED);
        instanceDto.setContainer("testContainer");
        instanceDto.setConfiguration(new ConfigurationDto());
        instanceDto.setSettings(new SettingsDto());
        instanceDto.setEvent(new EventDto());
        instanceDto.setVersion("1.0.0");

        Instance instance = modelMapper.map(instanceDto, Instance.class);

        checkMapping(instance, instanceDto);
    }

    private void checkMapping(Instance instance, InstanceDto instanceDto) {
        assertEquals(instance.getId(), instanceDto.getId());
        assertEquals(instance.isRestartRequired(), instanceDto.isRestartRequired());
        assertEquals(instance.getName(), instanceDto.getName());
        assertEquals(instance.getState(), instanceDto.getState());
        assertEquals(instance.getContainer(), instanceDto.getContainer());
        assertEquals(instance.getVersion(), instanceDto.getVersion());
    }
}
