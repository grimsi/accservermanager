package grimsi.accservermanager.backend.dto;

import grimsi.accservermanager.backend.enums.InstanceState;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class InstanceDto {

    private String id;

    @NotBlank(message = "name is required.")
    private String name;

    private InstanceState state;

    private String container;

    @NotNull(message = "config is required.")
    private ConfigDto config;
}
