package grimsi.accservermanager.backend.entity;

import grimsi.accservermanager.backend.enums.InstanceState;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Getter
@Setter
public class Instance {
    @Id
    public String id;

    public String name;
    private String version;
    public InstanceState state;
    public String container;

    @DBRef
    public Config config;
}
