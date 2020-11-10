import { Injectable } from '@angular/core';
import { MatDialog, MatDialogConfig } from '@angular/material';
import { ErrorDialogComponent } from '../components/error-dialog/error-dialog.component';
import { ServerCreateDialogComponent } from '../components/server-create-dialog/server-create-dialog.component';
import { ServerEditDialogComponent } from '../components/server-edit-dialog/server-edit-dialog.component';
import { ServerDto } from '../models/dtos/ServerDto';
import { EventDto } from '../models/dtos/EventDto';
import { EventCreateDialogComponent } from '../components/event-create-dialog/event-create-dialog.component';
import { EventEditDialogComponent } from '../components/event-edit-dialog/event-edit-dialog.component';

@Injectable({
  providedIn: 'root'
})
export class DialogService {

  private dialogWidth = '750px';

  constructor(private dialog: MatDialog) {
  }

  public showErrorDialog(message: string): void {
    const dialogConfig = new MatDialogConfig();

    dialogConfig.disableClose = true;
    dialogConfig.autoFocus = true;
    dialogConfig.closeOnNavigation = true;

    dialogConfig.data = {
      message
    };

    this.dialog.open(ErrorDialogComponent, dialogConfig);
  }

  public showCreateServerDialog(): void {
    const dialogConfig = new MatDialogConfig();

    dialogConfig.disableClose = false;
    dialogConfig.autoFocus = true;
    dialogConfig.closeOnNavigation = true;
    dialogConfig.width = this.dialogWidth;

    this.dialog.open(ServerCreateDialogComponent, dialogConfig);
  }

  public showEditServerDialog(server: ServerDto): void {
    const dialogConfig = new MatDialogConfig();

    dialogConfig.disableClose = false;
    dialogConfig.autoFocus = true;
    dialogConfig.closeOnNavigation = true;
    dialogConfig.width = this.dialogWidth;

    dialogConfig.data = {
      server
    };

    this.dialog.open(ServerEditDialogComponent, dialogConfig);
  }

  public showCreateEventDialog(): void {
    const dialogConfig = new MatDialogConfig();

    dialogConfig.disableClose = false;
    dialogConfig.autoFocus = true;
    dialogConfig.closeOnNavigation = true;
    dialogConfig.width = this.dialogWidth;

    this.dialog.open(EventCreateDialogComponent, dialogConfig);
  }

  public showEditEventDialog(event: EventDto): void {
    const dialogConfig = new MatDialogConfig();

    dialogConfig.disableClose = false;
    dialogConfig.autoFocus = true;
    dialogConfig.closeOnNavigation = true;
    dialogConfig.width = this.dialogWidth;

    dialogConfig.data = {
      event
    };

    this.dialog.open(EventEditDialogComponent, dialogConfig);
  }

}
