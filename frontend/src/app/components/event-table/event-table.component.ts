import { Component, Input, OnInit, ViewChild } from '@angular/core';
import { MatPaginator, MatSort } from '@angular/material';
import { EventTableDataSource } from './event-table-datasource';
import { EventDto } from '../../models/dtos/EventDto';
import { DialogService } from '../../services/dialog.service';
import { EventApiService } from '../../services/event-api.service';
import { Icon } from 'src/app/models/enums/Icon';
import { UtilityService } from '../../services/utility.service';

@Component({
  selector: 'app-event-table',
  templateUrl: './event-table.component.html',
  styleUrls: ['./event-table.component.css']
})
export class EventTableComponent implements OnInit {
  @ViewChild(MatPaginator) paginator: MatPaginator;
  @ViewChild(MatSort) sort: MatSort;
  dataSource: EventTableDataSource;

  @Input()
  data: EventDto[];

  Icon = Icon;

  constructor(private dialogService: DialogService,
              private eventApiService: EventApiService,
              private utils: UtilityService) {
  }


  /** Columns displayed in the table. Columns IDs can be added, removed, or reordered. */
  displayedColumns = ['name', 'track', 'managementButtons'];

  ngOnInit() {
    this.dataSource = new EventTableDataSource(this.paginator, this.sort, this.data);
  }

  refresh(): void {
    this.dataSource = new EventTableDataSource(this.paginator, this.sort, this.data);
  }

  createEvent(): void {
    this.dialogService.showCreateEventDialog();
  }

  editEvent(event: EventDto): void {
    this.dialogService.showEditEventDialog(event);
  }

  deleteEvent(event: EventDto): void {
    this.eventApiService.deleteEventById(event.id).subscribe();
  }
}
