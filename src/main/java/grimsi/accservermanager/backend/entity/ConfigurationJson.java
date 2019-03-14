package grimsi.accservermanager.backend.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("configuration")
@Getter
@Setter
public class ConfigurationJson {
    @Id
    public String id;

    public int udpPort;
    public int tcpPort;
    public int maxClients;
    public int configVersion = 1;
}
