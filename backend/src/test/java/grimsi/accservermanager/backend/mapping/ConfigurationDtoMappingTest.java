package grimsi.accservermanager.backend.mapping;

import grimsi.accservermanager.backend.dto.ConfigurationDto;
import grimsi.accservermanager.backend.entity.Configuration;
import grimsi.accservermanager.backend.interfaces.MappingUnitTest;
import org.junit.Test;
import org.modelmapper.ModelMapper;

import static org.junit.Assert.assertEquals;

public class ConfigurationDtoMappingTest implements MappingUnitTest {

    private ModelMapper modelMapper = new ModelMapper();

    @Test
    public void convertEntityToDto() {
        Configuration configuration = new Configuration();
        configuration.setMaxConnections(24);
        configuration.setTcpPort(9600);
        configuration.setUdpPort(9600);

        ConfigurationDto configurationDto = modelMapper.map(configuration, ConfigurationDto.class);

        checkMapping(configuration, configurationDto);
    }

    @Test
    public void convertDtoToEntity() {
        ConfigurationDto configurationDto = new ConfigurationDto();
        configurationDto.setMaxConnections(24);
        configurationDto.setTcpPort(9600);
        configurationDto.setUdpPort(9600);

        Configuration configuration = modelMapper.map(configurationDto, Configuration.class);

        checkMapping(configuration, configurationDto);
    }

    private void checkMapping(Configuration configuration, ConfigurationDto configurationDto) {
        assertEquals(configuration.getConfigVersion(), configurationDto.getConfigVersion());
        assertEquals(configuration.getMaxConnections(), configurationDto.getMaxConnections());
        assertEquals(configuration.getTcpPort(), configurationDto.getUdpPort());
        assertEquals(configuration.getUdpPort(), configurationDto.getUdpPort());
    }
}
