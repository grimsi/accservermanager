package grimsi.accservermanager.backend.documents;

import org.springframework.data.mongodb.core.mapping.Document;

@Document("configuration")
public class ConfigurationJson {
    public int udpPort;
    public int tcpPort;
    public int maxClients;
    public int configVersion = 1;
}
