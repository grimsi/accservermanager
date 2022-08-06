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

    @NotNull(message = "maxConnections is required.")
    @Min(value = 1, message = "maxConnections has to be between 1 and 24.")
    @Max(value = 85, message = "maxConnections has to be between 1 and 24.")
    private int maxConnections;

    @NotNull(message = "lanDiscovery Defines if the server will listen to LAN discovery requests.")
    @Min(value = 0, message = "lanDiscovery has to be between 0 and 1.")
    @Max(value = 1, message = "lanDiscovery has to be between 0 and 1.")
    private int lanDiscovery;

    @NotNull(message = "When 0, this server won’t register to the backend")
    @Min(value = 0, message = "registerToLobby has to be between 0 and 1.")
    @Max(value = 1, message = "registerToLobby has to be between 0 and 1.")
    private int registerToLobby;

    @NotNull(message = "publicIP is Explicitly defines the public IP address this server is listening to.")
    private int publicIP;
}
