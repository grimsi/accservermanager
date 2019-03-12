package grimsi.accservermanager.backend.documents;

import grimsi.accservermanager.backend.enums.InstanceState;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Instance {
    @Id
    public String id;

    private String name;
    private InstanceState state;
    private String container;
    
    @DBRef
    private Config config;
}
