import { Component, OnInit } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { InfoService } from '../../services/info.service';
import { EventApiService } from '../../services/event-api.service';
import { EventDto } from '../../models/dtos/EventDto';
import { ServerDto } from '../../models/dtos/ServerDto';
import { ServerApiService } from '../../services/server-api.service';
import { MatDialogRef } from '@angular/material';

@Component({
  selector: 'app-create-server',
  templateUrl: './server-create-dialog.component.html',
  styleUrls: ['./server-create-dialog.component.scss']
})
export class ServerCreateDialogComponent implements OnInit {

  supportedAccVersions: string[];
  events: EventDto[];

  versionsLoaded = false;
  eventsLoaded = false;

  createServerForm = this.fb.group({
    name: [null, Validators.required],
    version: [this.supportedAccVersions, Validators.required],
    event: [this.events, Validators.required],
    configuration: this.fb.group({
      udpPort: [9600, [Validators.required, Validators.min(1024), Validators.max(49151)]],
      tcpPort: [9600, [Validators.required, Validators.min(1024), Validators.max(49151)]],
      maxClients: [24, [Validators.required, Validators.min(1), Validators.max(24)]]
    }),
    settings: this.fb.group({
      serverName: [null, Validators.required],
      password: [null],
      adminPassword: [null, Validators.required],
      trackMedalsRequirement: [0, [Validators.required, Validators.min(0), Validators.max(3)]],
      safetyRatingRequirement: [-1, [Validators.required, Validators.min(-1), Validators.max(99)]],
    })
  });

  constructor(private fb: FormBuilder,
              private infoService: InfoService,
              private eventService: EventApiService,
              private serverService: ServerApiService,
              public dialogRef: MatDialogRef<ServerCreateDialogComponent>) {
  }

  ngOnInit() {
    this.setSupportedAccVersions();
    this.setEvents();
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
      }, error => {
        this.close();
      });
  }

  submit(): void {
    this.createServer(this.createServerForm.value);
  }

  createServer(server: ServerDto): void {
    this.serverService.createServer(server).subscribe(() => {
      this.close();
    });
  }

}
