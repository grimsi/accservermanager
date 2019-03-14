package grimsi.accservermanager.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ConfigDto {

    private String id;

    @NotBlank(message = "name is required.")
    private String name;

    @NotNull(message = "configurationJson is required.")
    @Valid
    private ConfigurationJsonDto configurationJson;

    @NotNull(message = "settingsJson is required.")
    @Valid
    private SettingsJsonDto settingsJson;

    @NotNull(message = "eventJson is required.")
    @Valid
    private EventJsonDto eventJson;
}
