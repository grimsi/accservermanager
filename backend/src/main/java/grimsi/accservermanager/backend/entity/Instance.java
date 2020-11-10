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
    private String id;

    private boolean restartRequired;

    @Indexed(unique = true)
    private String name;

    private InstanceState state;

    private String container;

    private Configuration configuration;

    private Settings settings;

    @DBRef
    private Event event;

    private String version;
}
