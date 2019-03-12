package grimsi.accservermanager.backend.documents;

import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Config {
    @DBRef
    public ConfigurationJson configurationJson;
    @DBRef
    public SettingsJson settingsJson;
    @DBRef
    public EventJson eventJson;
}
