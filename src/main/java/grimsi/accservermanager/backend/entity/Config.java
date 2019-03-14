package grimsi.accservermanager.backend.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Getter
@Setter
public class Config {
    @Id
    public String id;

    public String name;

    @DBRef
    public ConfigurationJson configurationJson;
    @DBRef
    public SettingsJson settingsJson;
    @DBRef
    public EventJson eventJson;
}