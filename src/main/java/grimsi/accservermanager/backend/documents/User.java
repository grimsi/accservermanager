package grimsi.accservermanager.backend.documents;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class User {
    @Id
    public String id;

    public String username;
    public String password;
}
