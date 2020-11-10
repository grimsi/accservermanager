import { EventDto } from '../models/dtos/EventDto';
import { InstanceState } from '../models/enums/InstanceState';
import { Observable } from 'rxjs';
import { ApiErrorResponse } from '../models/dtos/ApiErrorResponse';
import { EventSourcePolyfill } from 'ng-event-source';

export interface EventApi {
  createEvent(event: EventDto): Observable<EventDto | ApiErrorResponse>;

  getEvents(name?: string, state?: InstanceState): Observable<EventDto[] | ApiErrorResponse>;

  getEventById(eventId: string): Observable<EventDto | ApiErrorResponse>;

  deleteEventById(eventId: string): Observable<Response | ApiErrorResponse>;

  updateEventById(event: EventDto): Observable<EventDto | ApiErrorResponse>;

  getEventStream(): EventSourcePolyfill;

  getEventSchema(): Observable<object | ApiErrorResponse>;
}
