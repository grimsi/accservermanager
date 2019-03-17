package grimsi.accservermanager.backend.dto;

import grimsi.accservermanager.backend.annotation.UniqueInstanceName;
import grimsi.accservermanager.backend.annotation.ValidAccVersion;
import grimsi.accservermanager.backend.annotation.ValidConfigId;
import grimsi.accservermanager.backend.enums.InstanceState;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class InstanceDto {

    private String id;

    @NotBlank(message = "name is required.")
    @Pattern(regexp = "[a-zA-Z0-9_-]*", message = "invalid name. Allowed characters: a-z, A-Z, 0-9, '_', '-'")
    @UniqueInstanceName
    private String name;

    @NotBlank(message = "version is required.")
    @ValidAccVersion
    private String version;

    private InstanceState state;

    private String container;

    @NotNull(message = "config is required.")
    @ValidConfigId
    private ConfigDto config;
}
