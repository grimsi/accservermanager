import { Component, Inject, OnInit } from '@angular/core';
import { EventDto } from '../../models/dtos/EventDto';
import { FormBuilder, Validators } from '@angular/forms';
import { EventApiService } from '../../services/event-api.service';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material';
import { InfoService } from '../../services/info.service';
import { ServerApiService } from '../../services/server-api.service';
import { ServerDto } from '../../models/dtos/ServerDto';

@Component({
  selector: 'app-server-edit-dialog',
  templateUrl: './server-edit-dialog.component.html',
  styleUrls: ['./server-edit-dialog.component.scss']
})
export class ServerEditDialogComponent implements OnInit {

  server: ServerDto;
  supportedAccVersions: string[];
  events: EventDto[];

  versionsLoaded = false;
  eventsLoaded = false;

  editServerForm = this.fb.group({
    id: [{value: null, disabled: true}, [Validators.required]],
    container: [{value: null, disabled: true}, [Validators.required]],
    name: [null, Validators.required],
    version: [null, Validators.required],
    event: [null, Validators.required],
    configuration: this.fb.group({
      udpPort: [null, [Validators.required, Validators.min(1024), Validators.max(49151)]],
      tcpPort: [null, [Validators.required, Validators.min(1024), Validators.max(49151)]],
      maxConnections: [null, [Validators.required, Validators.min(1), Validators.max(24)]]
    }),
    settings: this.fb.group({
      serverName: [null, Validators.required],
      password: [''],
      adminPassword: [null, Validators.required],
      trackMedalsRequirement: [0, [Validators.required, Validators.min(0), Validators.max(3)]],
      safetyRatingRequirement: [-1, [Validators.required, Validators.min(-1), Validators.max(99)]],
      ignorePrematureDisconnects: [0, [Validators.required, Validators.min(0), Validators.max(1)]],
    })
  });

  constructor(private fb: FormBuilder,
              private infoService: InfoService,
              private eventService: EventApiService,
              private serverService: ServerApiService,
              public dialogRef: MatDialogRef<ServerEditDialogComponent>,
              @Inject(MAT_DIALOG_DATA) data) {
    this.server = data.server;
  }

  ngOnInit() {
    this.setSupportedAccVersions();
    this.setEvents();
    this.setDialogContent();
  }

  close() {
    this.dialogRef.close();
  }

  private setSupportedAccVersions(): void {
    this.infoService.getSupportedVersions()
      .subscribe((versions: string[]) => {
        this.supportedAccVersions = versions;
        this.versionsLoaded = true;
      }, error => {
        this.close();
      });
  }

  private setEvents(): void {
    this.eventService.getEvents().subscribe(
      (events: EventDto[]) => {
        this.events = events;
        this.eventsLoaded = true;
        this.editServerForm.patchValue({
          event: this.events[this.getEventIndex(this.server.event)]
        });
      }, error => {
        this.close();
      });
  }

  private setDialogContent(): void {
    this.editServerForm.patchValue({
      id: this.server.id,
      container: this.server.container,
      name: this.server.name,
      version: this.server.version,
      event: this.server.event,
      configuration: {
        udpPort: this.server.configuration.udpPort,
        tcpPort: this.server.configuration.tcpPort,
        maxConnections: this.server.configuration.maxConnections
      },
      settings: {
        serverName: this.server.settings.serverName,
        password: this.server.settings.password || '',
        adminPassword: this.server.settings.adminPassword,
        trackMedalsRequirement: this.server.settings.trackMedalsRequirement,
        safetyRatingRequirement: this.server.settings.safetyRatingRequirement
      }
    });
  }

  private getEventIndex(event: EventDto): number {
    let eventIndex = 0;
    this.events.forEach((e: EventDto, index: number) => {
      if (e.id === event.id) {
        eventIndex = index;
      }
    });
    return eventIndex;
  }

  submit(): void {
    this.updateServer(this.editServerForm.getRawValue());
  }

  updateServer(server: ServerDto): void {
    this.serverService.updateServerById(server).subscribe(() => {
      this.dialogRef.close();
    });
  }


}
