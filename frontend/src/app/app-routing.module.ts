import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule, Routes } from '@angular/router';
import { NotImplementedComponent } from './components/not-implemented/not-implemented.component';
import { AuthGuard } from './guards/auth.guard';
import { LoginPageComponent } from './components/login-page/login-page.component';
import { LogoutPageComponent } from './components/logout-page/logout-page.component';
import { FullpageLayoutComponent } from './layouts/fullpage-layout/fullpage-layout.component';
import { NavbarLayoutComponent } from './layouts/navbar-layout/navbar-layout.component';
import { ServerListComponent } from './components/server-list/server-list.component';
import { PageNotFoundComponent } from './components/page-not-found/page-not-found.component';
import { EventListComponent } from './components/event-list/event-list.component';
import { SystemInfoComponent } from './components/system-info/system-info.component';


const appRoutes: Routes = [
  {
    path: '',
    component: NavbarLayoutComponent,
    canActivate: [AuthGuard],
    children: [
      {
        path: 'servers',
        component: ServerListComponent
      },
      {
        path: 'events',
        component: EventListComponent
      },
      {
        path: 'results',
        component: NotImplementedComponent
      },
      {
        path: 'logs',
        component: NotImplementedComponent
      },
      {
        path: 'info',
        component: SystemInfoComponent
      },
      {
        path: '',
        redirectTo: '/servers',
        pathMatch: 'full'
      }
    ]
  },
  {
    path: '',
    component: FullpageLayoutComponent,
    children: [
      {
        path: 'login',
        component: LoginPageComponent
      },
      {
        path: 'logout',
        component: LogoutPageComponent
      },
      {
        path: '',
        redirectTo: '/login',
        pathMatch: 'full'
      }
    ]
  },
  {
    path: '**',
    component: PageNotFoundComponent
  }
];

@NgModule({
  declarations: [],
  imports: [
    RouterModule.forRoot(appRoutes),
    CommonModule
  ],
  exports: [
    RouterModule
  ]
})
export class AppRoutingModule {
}
