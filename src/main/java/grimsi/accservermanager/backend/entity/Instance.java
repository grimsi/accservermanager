package grimsi.accservermanager.backend.entity;

import grimsi.accservermanager.backend.enums.InstanceState;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Instance {
    @Id
    public String id;

    public String name;
    public InstanceState state;
    public String container;

    @DBRef
    public Config config;
}
