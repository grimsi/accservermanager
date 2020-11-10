import { TestBed } from '@angular/core/testing';

import { EventApiService } from './event-api.service';

describe('EventApiService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: EventApiService = TestBed.get(EventApiService);
    expect(service).toBeTruthy();
  });
});
