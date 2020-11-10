import { SessionType } from '../enums/SessionType';

export class SessionDto {
  hourOfDay: number;
  dayOfWeekend: number;
  timeMultiplier: number;
  sessionType: SessionType;
  sessionDurationMinutes: number;
}
