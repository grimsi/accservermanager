package grimsi.accservermanager.backend.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Configuration {
    public int udpPort;
    public int tcpPort;
    public int maxClients;
    public int configVersion = 1;
}
