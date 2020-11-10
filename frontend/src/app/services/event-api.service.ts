import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { AuthService } from './auth.service';
import { EventDto } from '../models/dtos/EventDto';
import { Observable } from 'rxjs';
import { ApiErrorResponse } from '../models/dtos/ApiErrorResponse';
import { EventSourcePolyfill } from 'ng-event-source';
import { Config } from '../config/Config';
import { EventApi } from '../api/EventApi';

@Injectable({
  providedIn: 'root'
})
export class EventApiService implements EventApi {

  private readonly apiPath = '/events';

  constructor(private http: HttpClient,
              private authService: AuthService) {
  }

  public createEvent(event: EventDto): Observable<EventDto> {
    return this.http.post<EventDto>(this.apiPath, event);
  }

  public getEvents(name?: string): Observable<EventDto[] | ApiErrorResponse> {
    return this.http.get<EventDto[] | ApiErrorResponse>(this.apiPath);
  }

  public getEventById(eventId: string): Observable<EventDto | ApiErrorResponse> {
    return this.http.get<EventDto | ApiErrorResponse>(`${this.apiPath}/${eventId}`);
  }

  public deleteEventById(eventId: string): Observable<Response | ApiErrorResponse> {
    return this.http.delete<Response>(`${this.apiPath}/${eventId}`);
  }

  public updateEventById(event: EventDto): Observable<EventDto | ApiErrorResponse> {
    return this.http.put<EventDto>(`${this.apiPath}/${event.id}`, event);
  }

  public getEventStream(): EventSourcePolyfill {
    return new EventSourcePolyfill(`${Config.apiBasePath}${this.apiPath}/stream`,
      {headers: {Authorization: `Bearer ${this.authService.getToken()}`}});
  }

  public getEventSchema(): Observable<object | ApiErrorResponse> {
    return this.http.get<object | ApiErrorResponse>(`${this.apiPath}/schema`);
  }
}
