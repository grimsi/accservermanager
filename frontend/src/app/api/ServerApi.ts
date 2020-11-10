import { ServerDto } from '../models/dtos/ServerDto';
import { InstanceState } from '../models/enums/InstanceState';
import { Observable } from 'rxjs';
import { ApiErrorResponse } from '../models/dtos/ApiErrorResponse';
import { EventSourcePolyfill } from 'ng-event-source';

export interface ServerApi {
  createServer(server: ServerDto): Observable<ServerDto | ApiErrorResponse>;

  getServers(name?: string, state?: InstanceState): Observable<ServerDto[] | ApiErrorResponse>;

  getServerById(serverId: string): Observable<ServerDto | ApiErrorResponse>;

  deleteServerById(serverId: string): Observable<Response | ApiErrorResponse>;

  updateServerById(server: ServerDto): Observable<ServerDto | ApiErrorResponse>;

  startServerById(serverId: string): Observable<Response | ApiErrorResponse>;

  stopServerById(serverId: string): Observable<Response | ApiErrorResponse>;

  pauseServerById(serverId: string): Observable<Response | ApiErrorResponse>;

  resumeServerById(serverId: string): Observable<Response | ApiErrorResponse>;

  getServerStream(): EventSourcePolyfill;

  getServerSchema(): Observable<object | ApiErrorResponse>;
}
