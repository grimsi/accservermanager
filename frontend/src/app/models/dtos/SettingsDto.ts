export class SettingsDto {
  readonly configVersion = 1;
  serverName: string;
  password: string;
  adminPassword: string;
  trackMedalsRequirement: number;
  safetyRatingRequirement: number;
  ignorePrematureDisconnects: number;
}
