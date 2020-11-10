import { EventDto } from './EventDto';
import { InstanceState } from '../enums/InstanceState';
import { ConfigurationDto } from './ConfigurationDto';
import { SettingsDto } from './SettingsDto';

export class ServerDto {
  readonly id: string;
  name: string;
  restartRequired: boolean;
  version: string;
  state: InstanceState;
  readonly container: string;
  configuration: ConfigurationDto;
  settings: SettingsDto;
  event: EventDto;
}
