package grimsi.accservermanager.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ConfigurationJsonDto {
    private int udpPort;
    private int tcpPort;
    private int maxClients;
    private int configVersion = 1;
}
