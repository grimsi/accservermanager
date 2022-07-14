import { Component, Inject, OnInit } from '@angular/core';
import { FormArray, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Icon } from '../../models/enums/Icon';
import { EventApiService } from '../../services/event-api.service';
import { UtilityService } from '../../services/utility.service';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material';
import { Track } from '../../models/enums/Track';
import { SessionType } from '../../models/enums/SessionType';
import { EventDto } from '../../models/dtos/EventDto';
import { SessionDto } from '../../models/dtos/SessionDto';

@Component({
  selector: 'app-event-edit-dialog',
  templateUrl: './event-edit-dialog.component.html',
  styleUrls: ['./event-edit-dialog.component.scss']
})
export class EventEditDialogComponent implements OnInit {

  editEventForm = this.fb.group({
    id: [{value: null, disabled: true}, Validators.required],
    name: [null, Validators.required],
    track: [null, Validators.required],
    preRaceWaitingTimeSeconds: [null, [Validators.required, Validators.min(0)]],
    sessionOverTimeSeconds: [null, [Validators.required, Validators.min(0)]],
    postQualySeconds: [null, [Validators.required, Validators.min(0)]],
    postRaceSeconds: [null, [Validators.required, Validators.min(0)]],
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
  event;

  constructor(private fb: FormBuilder,
              private eventService: EventApiService,
              private utils: UtilityService,
              public dialogRef: MatDialogRef<EventEditDialogComponent>,
              @Inject(MAT_DIALOG_DATA) data) {
    this.trackKeys = Object.keys(Track);
    this.sessionTypeKeys = Object.keys(SessionType);
    this.event = data.event;
  }

  ngOnInit() {
    this.setDialogContent();
  }


  private setDialogContent(): void {
    this.editEventForm.patchValue({
      id: this.event.id,
      name: this.event.name,
      track: this.event.track,
      preRaceWaitingTimeSeconds: this.event.preRaceWaitingTimeSeconds,
      postQualySeconds: this.event.postQualySeconds,
      postRaceSeconds: this.event.postRaceSeconds,
      sessionOverTimeSeconds: this.event.sessionOverTimeSeconds,
      ambientTemp: this.event.ambientTemp,
      trackTemp: this.event.trackTemp,
      cloudLevel: this.event.cloudLevel,
      rain: this.event.rain,
      weatherRandomness: this.event.weatherRandomness,
      simracerWeatherConditions: this.event.simracerWeatherConditions,
      isFixedConditionQualification : this.event.isFixedConditionQualification
    });

    this.event.sessions.forEach((session: SessionDto) => {
      this.sessions.push(this.convertSessionToFormGroup(session));
    });
  }


  get sessions() {
    return this.editEventForm.get('sessions') as FormArray;
  }

  private convertSessionToFormGroup(session: SessionDto): FormGroup {
    return this.fb.group(
      {
        hourOfDay: [session.hourOfDay, [Validators.required, Validators.min(0), Validators.max(23)]],
        dayOfWeekend: [session.dayOfWeekend, [Validators.required, Validators.min(1), Validators.max(3)]],
        timeMultiplier: [session.timeMultiplier, [Validators.required, Validators.min(0), Validators.max(24)]],
        sessionType: [session.sessionType, [Validators.required]],
        sessionDurationMinutes: [session.sessionDurationMinutes, [Validators.required, Validators.min(1)]]
      }
    );
  }

  addSession() {
    const sessionForm: FormGroup = this.fb.group({
      hourOfDay: [null, [Validators.required, Validators.min(0), Validators.max(23)]],
      dayOfWeekend: [null, [Validators.required, Validators.min(1), Validators.max(3)]],
      timeMultiplier: [null, [Validators.required, Validators.min(0), Validators.max(24)]],
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
    this.updateEvent(this.editEventForm.getRawValue());
  }

  updateEvent(event: EventDto): void {
    this.eventService.updateEventById(event).subscribe(() => {
      this.close();
    });
  }

}
