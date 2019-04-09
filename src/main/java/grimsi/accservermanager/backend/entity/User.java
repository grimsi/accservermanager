package grimsi.accservermanager.backend.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

@Document
@Getter
@Setter
public class User implements Serializable {

    private static final long serialVersionUID = 7650367368584885329L;

    @Id
    private String id;

    private String username;
    private String password;
}
