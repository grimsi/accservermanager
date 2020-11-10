package grimsi.accservermanager.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ConfigurationDto {

    private final int configVersion = 1;

    @NotNull(message = "udpPort is required.")
    @Min(value = 1024, message = "udpPort has to be between 1024 and 49151.")
    @Max(value = 49151, message = "udpPort has to be between 1024 and 49151.")
    private int udpPort;

    @NotNull(message = "tcpPort is required.")
    @Min(value = 1024, message = "tcpPort has to be between 1024 and 49151.")
    @Max(value = 49151, message = "tcpPort has to be between 1024 and 49151.")
    private int tcpPort;

    @NotNull(message = "maxClients is required.")
    @Min(value = 1, message = "maxClients has to be between 1 and 24.")
    @Max(value = 24, message = "maxClients has to be between 1 and 24.")
    private int maxClients;
}
