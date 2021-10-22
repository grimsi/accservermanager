import { Component, OnInit } from '@angular/core';
import { FormArray, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { EventApiService } from '../../services/event-api.service';
import { MatDialogRef } from '@angular/material';
import { EventDto } from '../../models/dtos/EventDto';
import { Track } from 'src/app/models/enums/Track';
import { UtilityService } from '../../services/utility.service';
import { Icon } from 'src/app/models/enums/Icon';
import { SessionType } from '../../models/enums/SessionType';

@Component({
  selector: 'app-event-create-dialog',
  templateUrl: './event-create-dialog.component.html',
  styleUrls: ['./event-create-dialog.component.scss']
})
export class EventCreateDialogComponent implements OnInit {

  createEventForm = this.fb.group({
    name: [null, Validators.required],
    track: [null, Validators.required],
    preRaceWaitingTimeSeconds: [80, [Validators.required, Validators.min(0)]],
    postQualySeconds: [120, [Validators.required, Validators.min(0)]],
    postRaceSeconds: [120, [Validators.required, Validators.min(0)]],
    sessionOverTimeSeconds: [120, [Validators.required, Validators.min(0)]],
    ambientTemp: [null, [Validators.required, Validators.min(-100), Validators.max(100)]],
    trackTemp: [null, [Validators.required, Validators.min(-100), Validators.max(100)]],
    cloudLevel: [null, [Validators.required, Validators.min(0), Validators.max(1)]],
    rain: [null, [Validators.required, Validators.min(0), Validators.max(1)]],
    weatherRandomness: [null, [Validators.required, Validators.min(0), Validators.max(10)]],
    simracerWeatherConditions: [null, [Validators.required, Validators.min(0)]],
    isFixedConditionQualification: [null, [Validators.required, Validators.min(0)]],
    sessions: this.fb.array([], [Validators.required, Validators.minLength(1)])
  });

  Icon = Icon;
  trackKeys;
  sessionTypeKeys;

  constructor(private fb: FormBuilder,
              private eventService: EventApiService,
              private utils: UtilityService,
              public dialogRef: MatDialogRef<EventCreateDialogComponent>) {
    this.trackKeys = Object.keys(Track);
    this.sessionTypeKeys = Object.keys(SessionType);
  }

  ngOnInit() {
    this.addSession();
  }

  get sessions() {
    return this.createEventForm.get('sessions') as FormArray;
  }

  addSession() {
    const sessionForm: FormGroup = this.fb.group({
      hourOfDay: [null, [Validators.required, Validators.min(0), Validators.max(23)]],
      dayOfWeekend: [null, [Validators.required, Validators.min(1), Validators.max(3)]],
      timeMultiplier: [null, [Validators.required, Validators.min(0.1)]],
      sessionType: [null, [Validators.required]],
      sessionDurationMinutes: [null, [Validators.required, Validators.min(1)]]
    });

    this.sessions.push(sessionForm);
  }

  removeLastSession() {
    if (this.sessions.length > 1) {
      this.sessions.removeAt(this.sessions.length - 1);
    }
  }

  close() {
    this.dialogRef.close();
  }

  submit(): void {
    this.createEvent(this.createEventForm.value);
  }

  createEvent(event: EventDto): void {
    this.eventService.createEvent(event).subscribe(() => {
      this.close();
    });
  }

}
