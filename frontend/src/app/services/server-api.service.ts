import { Injectable } from '@angular/core';
import { ServerDto } from '../models/dtos/ServerDto';
import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { InstanceState } from '../models/enums/InstanceState';
import { ApiErrorResponse } from '../models/dtos/ApiErrorResponse';
import { ServerApi } from '../api/ServerApi';
import { EventSourcePolyfill } from 'ng-event-source';
import { AuthService } from './auth.service';
import { Config } from '../config/Config';

@Injectable({
  providedIn: 'root'
})
export class ServerApiService implements ServerApi {

  private readonly apiPath = '/instances';

  constructor(private http: HttpClient,
              private authService: AuthService) {
  }

  public createServer(server: ServerDto): Observable<ServerDto> {
    return this.http.post<ServerDto>(this.apiPath, server);
  }

  public getServers(name?: string, state?: InstanceState): Observable<ServerDto[] | ApiErrorResponse> {
    return this.http.get<ServerDto[] | ApiErrorResponse>(this.apiPath);
  }

  public getServerById(serverId: string): Observable<ServerDto | ApiErrorResponse> {
    return this.http.get<ServerDto | ApiErrorResponse>(`${this.apiPath}/${serverId}`);
  }

  public deleteServerById(serverId: string): Observable<Response | ApiErrorResponse> {
    return this.http.delete<Response>(`${this.apiPath}/${serverId}`);
  }

  public updateServerById(server: ServerDto): Observable<ServerDto | ApiErrorResponse> {
    return this.http.put<ServerDto>(`${this.apiPath}/${server.id}`, server);
  }

  public startServerById(serverId: string): Observable<any> {
    return this.http.get<any>(`${this.apiPath}/${serverId}/start`);
  }

  public stopServerById(serverId: string): Observable<any> {
    return this.http.get<any>(`${this.apiPath}/${serverId}/stop`);
  }

  public pauseServerById(serverId: string): Observable<any> {
    return this.http.get<any>(`${this.apiPath}/${serverId}/pause`);
  }

  public resumeServerById(serverId: string): Observable<any> {
    return this.http.get<any>(`${this.apiPath}/${serverId}/resume`);
  }

  public getServerStream(): EventSourcePolyfill {
    return new EventSourcePolyfill(`${Config.apiBasePath}${this.apiPath}/stream`,
      {headers: {Authorization: `Bearer ${this.authService.getToken()}`}});
  }

  public getServerSchema(): Observable<object | ApiErrorResponse> {
    return this.http.get<object | ApiErrorResponse>(`${this.apiPath}/schema`);
  }
}
