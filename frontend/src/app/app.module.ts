import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppComponent } from './app.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import {
  MatButtonModule,
  MatCardModule,
  MatDialogModule,
  MatDividerModule,
  MatFormFieldModule,
  MatIconModule,
  MatInputModule,
  MatMenuModule,
  MatOptionModule,
  MatPaginatorModule,
  MatProgressSpinnerModule,
  MatSelectModule,
  MatSliderModule,
  MatSortModule,
  MatTableModule,
  MatTabsModule,
  MatToolbarModule,
  MatTooltipModule
} from '@angular/material';
import { HeaderComponent } from './components/header/header.component';
import { NotImplementedComponent } from './components/not-implemented/not-implemented.component';
import { PageNotFoundComponent } from './components/page-not-found/page-not-found.component';
import { AppRoutingModule } from './app-routing.module';
import { FlexLayoutModule } from '@angular/flex-layout';
import { HTTP_INTERCEPTORS, HttpClientModule } from '@angular/common/http';
import { AuthInterceptor } from './interceptor/auth.interceptor';
import { ErrorInterceptor } from './interceptor/error.interceptor';
import { LoginPageComponent } from './components/login-page/login-page.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { LogoutPageComponent } from './components/logout-page/logout-page.component';
import { NavbarLayoutComponent } from './layouts/navbar-layout/navbar-layout.component';
import { FullpageLayoutComponent } from './layouts/fullpage-layout/fullpage-layout.component';
import { ServerListComponent } from './components/server-list/server-list.component';
import { MatProgressButtonsModule } from 'mat-progress-buttons';
import { ApiUrlInterceptor } from './interceptor/api-url.interceptor';
import { ErrorDialogComponent } from './components/error-dialog/error-dialog.component';
import { ServerTableComponent } from './components/server-table/server-table.component';
import { ServerCreateDialogComponent } from './components/server-create-dialog/server-create-dialog.component';
import { ServerEditDialogComponent } from './components/server-edit-dialog/server-edit-dialog.component';
import { EventListComponent } from './components/event-list/event-list.component';
import { EventTableComponent } from './components/event-table/event-table.component';
import { EventCreateDialogComponent } from './components/event-create-dialog/event-create-dialog.component';
import { EventEditDialogComponent } from './components/event-edit-dialog/event-edit-dialog.component';
import { SystemInfoComponent } from './components/system-info/system-info.component';

@NgModule({
  declarations: [
    AppComponent,
    HeaderComponent,
    NotImplementedComponent,
    PageNotFoundComponent,
    LoginPageComponent,
    LogoutPageComponent,
    NavbarLayoutComponent,
    FullpageLayoutComponent,
    ServerListComponent,
    ErrorDialogComponent,
    ServerTableComponent,
    ServerCreateDialogComponent,
    ServerEditDialogComponent,
    EventListComponent,
    EventTableComponent,
    EventCreateDialogComponent,
    EventEditDialogComponent,
    SystemInfoComponent
  ],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    FlexLayoutModule,
    MatTabsModule,
    MatIconModule,
    MatToolbarModule,
    MatMenuModule,
    MatButtonModule,
    MatDividerModule,
    MatCardModule,
    MatFormFieldModule,
    MatInputModule,
    MatProgressSpinnerModule,
    MatProgressButtonsModule,
    MatDialogModule,
    MatSelectModule,
    MatOptionModule,
    MatSliderModule,
    FormsModule,
    AppRoutingModule,
    HttpClientModule,
    ReactiveFormsModule,
    MatTableModule,
    MatPaginatorModule,
    MatSortModule,
    MatTooltipModule
  ],
  providers: [
    {
      provide: HTTP_INTERCEPTORS,
      useClass: ApiUrlInterceptor,
      multi: true
    },
    {
      provide: HTTP_INTERCEPTORS,
      useClass: AuthInterceptor,
      multi: true
    },
    {
      provide: HTTP_INTERCEPTORS,
      useClass: ErrorInterceptor,
      multi: true
    }
  ],
  entryComponents: [
    ErrorDialogComponent,
    ServerCreateDialogComponent,
    ServerEditDialogComponent,
    EventCreateDialogComponent,
    EventEditDialogComponent
  ],
  bootstrap: [AppComponent]
})
export class AppModule {
}
