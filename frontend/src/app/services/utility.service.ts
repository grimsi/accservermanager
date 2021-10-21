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
      case Track.barcelona:
        return 'barcelona';
      case Track.barcelona_2019:
        return 'barcelona 2019';
      case Track.barcelona_2020:
        return 'barcelona 2020';
      case Track.brands_hatch:
        return 'brands hatch';
      case Track.brands_hatch_2019:
        return 'brands hatch 2019';
      case Track.brands_hatch_2020:
        return 'brands hatch 2020';
      case Track.donington_2019:
        return 'donington 2019';
      case Track.donington_2020:
        return 'donington 2020';
      case Track.imola_2020:
        return 'imola 2020';
      case Track.kyalami_2019:
        return 'kyalami 2019';
      case Track.kyalami_2020:
        return 'kyalami 2020';
      case Track.laguna_seca_2019:
        return 'Laguna seca 2019';
      case Track.laguna_seca_2020:
        return 'Laguna seca 2020';
      case Track.mount_panorama_2019:
        return 'Mount panorama 2019';
      case Track.mount_panorama_2020:
        return 'Mount panorama  2020';
      case Track.oulton_park_2019:
        return 'Oulton Parck 2019';
      case Track.oulton_park_2020:
        return 'Oulton Parck 2020';
      case Track.silverstone:
        return 'silverstone';
      case Track.silverstone_2019:
        return 'silverstone 2019';
      case Track.silverstone_2020:
        return 'silverstone 2020';
      case Track.suzuka_2019:
        return 'silverstone 2019';
      case Track.suzuka_2020:
        return 'silverstone 2020';
      case Track.snetterton_2019:
        return 'snetterton 2019';
      case Track.snetterton_2020:
        return 'snetterton 2020';
      case Track.spa:
        return 'spa';
      case Track.spa_2019:
        return 'spa 2019';
      case Track.spa_2020:
        return 'spa 2020';
      case Track.nurburgring:
        return 'Nürburgring';
      case Track.nurburgring_2019:
        return 'Nürburgring 2019';
      case Track.nurburgring_2020:
        return 'Nürburgring 2020';
      case Track.hungaroring:
        return 'Hungaroring';
      case Track.hungaroring_2019:
        return 'Hungaroring 2019';
      case Track.hungaroring_2020:
        return 'Hungaroring_2020';
      case Track.misano:
        return 'Misano';
      case Track.misano_2019:
        return 'Misano 2019';
      case Track.misano_2020:
        return 'Misano 2020';
      case Track.monza:
        return 'Monza';
      case Track.monza_2019:
        return 'Monza 2019';
      case Track.monza_2020:
        return 'Monza 2020';
      case Track.paul_ricard:
        return 'Paul Ricard';
      case Track.paul_ricard_2019:
        return 'Paul Ricard 2019';
      case Track.paul_ricard_2020:
        return 'Paul Ricard 2020';
      case Track.zolder:
        return 'Zolder';
      case Track.zolder_2019:
        return 'Zolder 2019';
      case Track.zolder_2020:
        return 'Zolder 2020';
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
