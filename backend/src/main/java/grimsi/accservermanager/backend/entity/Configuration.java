package grimsi.accservermanager.backend.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Configuration {
    private int udpPort;
    private int tcpPort;
    private int maxClients;
    private int configVersion = 1;
}
