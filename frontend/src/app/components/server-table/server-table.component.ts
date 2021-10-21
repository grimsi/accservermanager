import { Component, Input, OnInit, ViewChild } from '@angular/core';
import { MatPaginator, MatSort, MatTable } from '@angular/material';
import { ServerTableDataSource } from './server-table-datasource';
import { ServerDto } from '../../models/dtos/ServerDto';
import { InstanceState } from '../../models/enums/InstanceState';
import { Icon } from '../../models/enums/Icon';
import { ServerApiService } from '../../services/server-api.service';
import { DialogService } from '../../services/dialog.service';
import { UtilityService } from '../../services/utility.service';
import { InfoService } from '../../services/info.service';
import { EventApiService } from '../../services/event-api.service';
import { MatProgressButtonOptions } from 'mat-progress-buttons';

@Component({
  selector: 'app-server-table',
  templateUrl: './server-table.component.html',
  styleUrls: ['./server-table.component.css']
})
export class ServerTableComponent implements OnInit {
  @ViewChild(MatPaginator) paginator: MatPaginator;
  @ViewChild(MatSort) sort: MatSort;
  @ViewChild(MatTable) serverTable: MatTable<ServerDto>;

  dataSource: ServerTableDataSource;

  InstanceState = InstanceState;
  Icon = Icon;

  editServerButton: MatProgressButtonOptions = {
    active: false,
    text: 'Edit Server',
    spinnerSize: 10,
    raised: false,
    fab: true,
    stroked: false,
    buttonColor: 'primary',
    spinnerColor: 'accent',
    fullWidth: false,
    disabled: false,
    mode: 'indeterminate',
    buttonIcon: {
      fontSet: 'fa',
      fontIcon: 'fa-heart',
      inline: true
    }
  };

  @Input()
  data: ServerDto[];

  constructor(private serverApiService: ServerApiService,
              private infoApiService: InfoService,
              private eventApiService: EventApiService,
              private dialogService: DialogService,
              private utils: UtilityService) {

  }


  /** Columns displayed in the table. Columns IDs can be added, removed, or reordered. */
    // tslint:disable-next-line:max-line-length
  displayedColumns = ['name', 'serverName', 'event', 'track', 'version', 'state', 'ports', 'restartRequired', 'stateButtons', 'managementButtons'];

  ngOnInit(): void {
    this.dataSource = new ServerTableDataSource(this.paginator, this.sort, this.data);
  }

  refresh(): void {
    this.dataSource = new ServerTableDataSource(this.paginator, this.sort, this.data);
  }

  createServer(): void {
    this.dialogService.showCreateServerDialog();
  }

  editServer(server: ServerDto): void {
    this.dialogService.showEditServerDialog(server);
  }

  deleteServer(server: ServerDto): void {
    this.serverApiService.deleteServerById(server.id).subscribe();
  }

  startServer(server: ServerDto): void {
    this.serverApiService.startServerById(server.id).subscribe();
  }

  stopServer(server: ServerDto): void {
    this.serverApiService.stopServerById(server.id).subscribe();
  }

  pauseServer(server: ServerDto): void {
    this.serverApiService.pauseServerById(server.id).subscribe();
  }

  resumeServer(server: ServerDto): void {
    this.serverApiService.resumeServerById(server.id).subscribe();
  }

  restartServer(server: ServerDto): void {
    if (server.state !== InstanceState.STOPPED) {
      this.serverApiService.stopServerById(server.id).subscribe(() => {
        this.serverApiService.startServerById(server.id).subscribe();
      });
    } else {
      this.serverApiService.startServerById(server.id).subscribe();
    }
  }
}
