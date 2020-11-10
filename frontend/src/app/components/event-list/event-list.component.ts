import { AfterViewInit, Component, OnDestroy, ViewChild } from '@angular/core';
import { EventTableComponent } from '../event-table/event-table.component';
import { EventDto } from '../../models/dtos/EventDto';
import { EventSourcePolyfill } from 'ng-event-source';
import { EventApiService } from '../../services/event-api.service';
import { DialogService } from '../../services/dialog.service';

@Component({
  selector: 'app-event-list',
  templateUrl: './event-list.component.html',
  styleUrls: ['./event-list.component.scss']
})
export class EventListComponent implements AfterViewInit, OnDestroy {

  @ViewChild(EventTableComponent)
  private eventTableComponent: EventTableComponent;

  loading = true;
  events: EventDto[] = [];
  eventStream: EventSourcePolyfill;

  constructor(private eventService: EventApiService,
              private dialogService: DialogService) {
  }

  ngAfterViewInit() {
    this.eventService.getEvents().subscribe(
      (events: EventDto[]) => {
        this.events = events;
        this.loading = false;
      }
    );

    this.eventStream = this.eventService.getEventStream();
    this.addEventListeners();
  }

  ngOnDestroy() {
    this.eventStream.close();
  }

  openCreateEventDialog(): void {
    this.dialogService.showCreateEventDialog();
  }

  addEventListeners(): void {
    this.eventStream.addEventListener('create', (event: any) => {
      const newEvent: EventDto = JSON.parse(event.data);

      this.events.push(newEvent);

      this.eventTableComponent.refresh();
    });

    this.eventStream.addEventListener('update', (event: any) => {
      const updatedEvent: EventDto = JSON.parse(event.data);

      this.events.forEach((e: EventDto, index: number) => {
        if (e.id === updatedEvent.id) {
          this.events[index] = updatedEvent;
          return;
        }
      });

      this.eventTableComponent.refresh();
    });

    this.eventStream.addEventListener('delete', (event: any) => {
      const deletedEventId: string = event.data;

      this.events.forEach((e: EventDto, index: number) => {
        if (e.id === deletedEventId) {
          this.events.splice(index, 1);
          return;
        }
      });

      this.eventTableComponent.refresh();
    });
  }
}
