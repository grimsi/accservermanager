package grimsi.accservermanager.backend.dto;

import grimsi.accservermanager.backend.annotation.ValidAccVersion;
import grimsi.accservermanager.backend.annotation.ValidEventId;
import grimsi.accservermanager.backend.enums.InstanceState;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class InstanceDto {

    @Id
    private String id;

    private boolean restartRequired;

    @NotBlank(message = "name is required.")
    @Pattern(regexp = "[a-zA-Z0-9_-]*", message = "invalid name. Allowed characters: a-z, A-Z, 0-9, '_', '-'")
    private String name;

    @NotBlank(message = "version is required.")
    @ValidAccVersion
    private String version;

    private InstanceState state;

    private String container;

    @NotNull(message = "configuration is required.")
    @Valid
    private ConfigurationDto configuration;

    @NotNull(message = "settings is required.")
    @Valid
    private SettingsDto settings;

    @NotNull(message = "event is required.")
    @ValidEventId
    private EventDto event;
}
