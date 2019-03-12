package grimsi.accservermanager.backend.dto;

import grimsi.accservermanager.backend.enums.InstanceState;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class InstanceDto {
    private String id;
    private String name;
    private InstanceState state;
    private String container;
    private ConfigDto config;
}
