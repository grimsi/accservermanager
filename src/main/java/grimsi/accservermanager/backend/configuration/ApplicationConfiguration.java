package grimsi.accservermanager.backend.configuration;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.util.UUID;

@Getter
@Setter
@Primary
@Configuration
@ConfigurationProperties(prefix = "accservermanager")
public class ApplicationConfiguration {
    private UUID secret;
    private long expirationTime;
    private String username;
    private String password;
    private String folderPath;
    private String serverExecutableName;
    private boolean containerNamePostfixEnabled;
    private String containerImage;
}
