package grimsi.accservermanager.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ConfigDto {
    private ConfigurationJsonDto configurationJson;
    private SettingsJsonDto settingsJson;
    private EventJsonDto eventJson;
}
