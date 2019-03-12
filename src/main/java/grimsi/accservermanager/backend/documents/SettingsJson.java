package grimsi.accservermanager.backend.documents;

import org.springframework.data.mongodb.core.mapping.Document;

@Document("setting")
public class SettingsJson {
    public String serverName;
    public String password;
    public String adminPassword;
    public int configVersion = 1;
}
