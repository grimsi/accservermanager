import { Injectable } from '@angular/core';
import { Track } from '../models/enums/Track';
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
      case Track.barcelona:
        return 'Barcelona';
      case Track.brands_hatch:
        return 'Brands hatch';
        case Track.cota:
          return 'Circuit of the Americas';        
      case Track.donington:
        return 'Donington';
      case Track.imola:
        return 'Imola';
        case Track.indianapolis:
          return 'Indianapolis Motor Speedway';        
      case Track.kyalami:
        return 'kyalami';
      case Track.laguna_seca:
        return 'Laguna seca';
      case Track.mount_panorama:
        return 'Mount panorama';
      case Track.oulton_park:
        return 'Oulton Parck';
      case Track.silverstone:
        return 'Silverstone';
      case Track.suzuka:
        return 'Suzuka';
        case Track.watkins_glen:
          return 'Watkins Glen';        
      case Track.snetterton:
        return 'Snetterton';
      case Track.spa:
        return 'Spa';
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
      case Track.zandvoort:
        return 'Zandvoort';
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
      case 1:
        return 'Friday';
      case 2:
        return 'Saturday';
      case 3:
        return 'Sunday';
      default:
        return 'Unknown';
    }
  }
}
