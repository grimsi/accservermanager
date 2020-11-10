import { Injectable } from '@angular/core';
import { Track } from '../models/enums/Track';
import { EventType } from '../models/enums/EventType';
import { InstanceState } from '../models/enums/InstanceState';
import { Icon } from '../models/enums/Icon';
import { SessionType } from '../models/enums/SessionType';

@Injectable({
  providedIn: 'root'
})
export class UtilityService {

  constructor() {
  }

  public trackEnumToString(track: Track): string {
    switch (track) {
      case Track.nurburgring:
        return 'Nürburgring';
      case Track.hungaroring:
        return 'Hungaroring';
      case Track.misano:
        return 'Misano';
      case Track.monza:
        return 'Monza';
      case Track.paul_ricard:
        return 'Paul Ricard';
      case Track.zolder:
        return 'Zolder';
      default:
        return 'Unknown';
    }
  }

  public eventTypeToString(eventType: EventType): string {
    switch (eventType) {
      case EventType.E_3h:
        return 'Endurance 3h';
      case EventType.E_6h:
        return 'Endurance 6h';
      case EventType.E_24h:
        return 'Endurance 24h';
      default:
        return 'Unknown';
    }
  }

  public mapServerStateToIcon(state: InstanceState): Icon {
    switch (state) {
      case InstanceState.RUNNING:
        return Icon.play_arrow;
      case InstanceState.STOPPED:
        return Icon.stop;
      case InstanceState.PAUSED:
        return Icon.pause;
      case InstanceState.CRASHED:
        return Icon.error;
      default:
        return Icon.help;
    }
  }

  public mapSessionTypeToString(sessionType: SessionType): string {
    switch (sessionType) {
      case SessionType.P:
        return 'Practice';
      case SessionType.Q:
        return 'Qualifying';
      case SessionType.R:
        return 'Race';
      default:
        return 'Unknown';
    }
  }


  public mapNumberToDay(dayOfWeekend: number): string {
    switch (dayOfWeekend) {
      case 0:
        return 'Friday';
      case 1:
        return 'Saturday';
      case 2:
        return 'Sunday';
      default:
        return 'Unknown';
    }
  }
}
