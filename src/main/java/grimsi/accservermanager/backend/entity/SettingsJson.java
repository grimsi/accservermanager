package grimsi.accservermanager.backend.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("setting")
@Getter
@Setter
public class SettingsJson {
    @Id
    public String id;

    public String serverName;
    public String password;
    public String adminPassword;
    public int configVersion = 1;
}
