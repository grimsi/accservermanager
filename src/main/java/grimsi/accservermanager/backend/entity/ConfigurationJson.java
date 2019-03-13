package grimsi.accservermanager.backend.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("configuration")
public class ConfigurationJson {
    @Id
    public String id;

    public String name;
    public int udpPort;
    public int tcpPort;
    public int maxClients;
    public int configVersion = 1;
}
