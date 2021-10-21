export class ConfigurationDto {
  readonly configVersion = 1;
  udpPort: number;
  tcpPort: number;
  maxConnections: number;
}
