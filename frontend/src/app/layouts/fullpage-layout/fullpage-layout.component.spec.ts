import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { FullpageLayoutComponent } from './fullpage-layout.component';

describe('FullpageLayoutComponent', () => {
  let component: FullpageLayoutComponent;
  let fixture: ComponentFixture<FullpageLayoutComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ FullpageLayoutComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(FullpageLayoutComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
