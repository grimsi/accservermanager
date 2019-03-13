package grimsi.accservermanager.backend.documents;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("setting")
public class SettingsJson {
    @Id
    public String id;

    public String name;
    public String serverName;
    public String password;
    public String adminPassword;
    public int configVersion = 1;
}
