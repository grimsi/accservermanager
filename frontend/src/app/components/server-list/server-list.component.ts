import { AfterViewInit, Component, OnDestroy, ViewChild } from '@angular/core';
import { ServerDto } from '../../models/dtos/ServerDto';
import { ServerApiService } from '../../services/server-api.service';
import { EventSourcePolyfill } from 'ng-event-source';
import { InstanceState } from '../../models/enums/InstanceState';
import { ServerTableComponent } from '../server-table/server-table.component';
import { DialogService } from '../../services/dialog.service';

@Component({
  selector: 'app-server-list',
  templateUrl: './server-list.component.html',
  styleUrls: ['./server-list.component.scss']
})
export class ServerListComponent implements AfterViewInit, OnDestroy {

  @ViewChild(ServerTableComponent)
  private serverTableComponent: ServerTableComponent;

  loading = true;
  servers: ServerDto[] = [];
  eventStream: EventSourcePolyfill;

  constructor(private serverService: ServerApiService,
              private dialogService: DialogService) {
  }

  ngAfterViewInit() {
    this.serverService.getServers().subscribe(
      (servers: ServerDto[]) => {
        this.servers = servers;
        this.loading = false;
      }
    );

    this.eventStream = this.serverService.getServerStream();
    this.addEventListeners();
  }

  ngOnDestroy() {
    this.eventStream.close();
  }

  openCreateServerDialog(): void {
    this.dialogService.showCreateServerDialog();
  }

  addEventListeners(): void {
    this.eventStream.addEventListener('create', (event: any) => {
      const newServer: ServerDto = JSON.parse(event.data);

      this.servers.push(newServer);

      this.serverTableComponent.refresh();
    });

    this.eventStream.addEventListener('update', (event: any) => {
      const updatedServer: ServerDto = JSON.parse(event.data);

      this.servers.forEach((server: ServerDto, index: number) => {
        if (server.id === updatedServer.id) {
          this.servers[index] = updatedServer;
          return;
        }
      });

      this.serverTableComponent.refresh();
    });

    this.eventStream.addEventListener('delete', (event: any) => {
      const deletedServerId: string = event.data;

      this.servers.forEach((server: ServerDto, index: number) => {
        if (server.id === deletedServerId) {
          this.servers.splice(index, 1);
          return;
        }
      });

      this.serverTableComponent.refresh();
    });

    this.eventStream.addEventListener('start', (event: any) => {
      const startedServerId: string = event.data;

      this.servers.forEach((server: ServerDto, index: number) => {
        if (server.id === startedServerId) {
          server.state = InstanceState.RUNNING;
          this.servers[index] = server;
          return;
        }
      });

      this.serverTableComponent.refresh();
    });

    this.eventStream.addEventListener('stop', (event: any) => {
      const stoppedServerId: string = event.data;

      this.servers.forEach((server: ServerDto, index: number) => {
        if (server.id === stoppedServerId) {
          server.state = InstanceState.STOPPED;
          this.servers[index] = server;
          return;
        }
      });

      this.serverTableComponent.refresh();
    });

    this.eventStream.addEventListener('pause', (event: any) => {
      const pausedServerId: string = event.data;

      this.servers.forEach((server: ServerDto, index: number) => {
        if (server.id === pausedServerId) {
          server.state = InstanceState.PAUSED;
          this.servers[index] = server;
          return;
        }
      });

      this.serverTableComponent.refresh();
    });

    this.eventStream.addEventListener('resume', (event: any) => {
      const resumedServerId: string = event.data;

      this.servers.forEach((server: ServerDto, index: number) => {
        if (server.id === resumedServerId) {
          server.state = InstanceState.RUNNING;
          this.servers[index] = server;
          return;
        }
      });

      this.serverTableComponent.refresh();
    });
  }
}
