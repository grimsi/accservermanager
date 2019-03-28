package grimsi.accservermanager.backend.entity;

import grimsi.accservermanager.backend.enums.InstanceState;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Getter
@Setter
public class Instance {
    @Id
    public String id;

    public boolean restartRequired;

    @Indexed(unique = true)
    public String name;

    public InstanceState state;

    public String container;

    public Configuration configuration;

    public Settings settings;

    @DBRef
    public Event event;

    private String version;
}
