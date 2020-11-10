import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ServerEditDialogComponent } from './server-edit-dialog.component';

describe('ServerEditDialogComponent', () => {
  let component: ServerEditDialogComponent;
  let fixture: ComponentFixture<ServerEditDialogComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ServerEditDialogComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ServerEditDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
