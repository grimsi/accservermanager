export class SettingsDto {
  readonly configVersion = 1;
  serverName: string;
  password: string;
  adminPassword: string;
  carGroup: string;
  trackMedalsRequirement: number;
  safetyRatingRequirement: number;
  racecraftRatingRequirement: number;
  maxCarSlots: number;
  isRaceLocked: number;
  allowAutoDQ: number;
  shortFormationLap: number;
  formationLapType: number;
  ignorePrematureDisconnects: number;
}
