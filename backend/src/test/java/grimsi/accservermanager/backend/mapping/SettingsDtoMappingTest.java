package grimsi.accservermanager.backend.mapping;

import grimsi.accservermanager.backend.dto.SettingsDto;
import grimsi.accservermanager.backend.entity.Settings;
import grimsi.accservermanager.backend.interfaces.MappingUnitTest;
import org.junit.Test;
import org.modelmapper.ModelMapper;

import static org.junit.Assert.assertEquals;

public class SettingsDtoMappingTest implements MappingUnitTest {

    private ModelMapper modelMapper = new ModelMapper();


    @Test
    public void convertEntityToDto() {
        Settings settings = new Settings();
        settings.setServerName("testServer");
        settings.setPassword("testPassword");
        settings.setAdminPassword("testAdminPassword");
        settings.setTrackMedalsRequirement(0);
        settings.setSafetyRatingRequirement(0);
        settings.setRacecraftRatingRequirement(0);
        settings.setAllowAutoDQ(0);
        settings.setIsRaceLocked(0);
        settings.setShortFormationLap(0);
        settings.setMaxCarSlots(0);
        settings.setIgnorePrematureDisconnects(0);

        SettingsDto settingsDto = modelMapper.map(settings, SettingsDto.class);

        checkMapping(settings, settingsDto);
    }

    @Test
    public void convertDtoToEntity() {
        SettingsDto settingsDto = new SettingsDto();
        settingsDto.setServerName("testServer");
        settingsDto.setPassword("testPassword");
        settingsDto.setAdminPassword("testAdminPassword");
        settingsDto.setTrackMedalsRequirement(0);
        settingsDto.setSafetyRatingRequirement(0);
        settingsDto.setRacecraftRatingRequirement(0);
        settingsDto.setAllowAutoDQ(0);
        settingsDto.setIsRaceLocked(0);
        settingsDto.setShortFormationLap(0);
        settingsDto.setMaxCarSlots(0);
        settingsDto.setIgnorePrematureDisconnects(0);

        Settings settings = modelMapper.map(settingsDto, Settings.class);

        checkMapping(settings, settingsDto);
    }

    private void checkMapping(Settings settings, SettingsDto settingsDto) {
        assertEquals(settings.getConfigVersion(), settingsDto.getConfigVersion());
        assertEquals(settings.getServerName(), settingsDto.getServerName());
        assertEquals(settings.getPassword(), settingsDto.getPassword());
        assertEquals(settings.getAdminPassword(), settingsDto.getAdminPassword());
        assertEquals(settings.getTrackMedalsRequirement(), settingsDto.getTrackMedalsRequirement());
        assertEquals(settings.getSafetyRatingRequirement(), settingsDto.getSafetyRatingRequirement());
        assertEquals(settings.getRacecraftRatingRequirement(), settingsDto.getRacecraftRatingRequirement());
        assertEquals(settings.getAllowAutoDQ(), settingsDto.getAllowAutoDQ());
        assertEquals(settings.getIsRaceLocked(), settingsDto.getIsRaceLocked());
        assertEquals(settings.getFormationLapType(), settingsDto.getFormationLapType());
        assertEquals(settings.getMaxCarSlots(), settingsDto.getMaxCarSlots());
        assertEquals(settings.getIgnorePrematureDisconnects(),settingsDto.getIgnorePrematureDisconnects());
    }
}
