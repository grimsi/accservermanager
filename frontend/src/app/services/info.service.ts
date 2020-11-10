import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { InfoDto } from '../models/dtos/InfoDto';
import { map } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class InfoService {

  private readonly apiPath = '/info';

  constructor(private http: HttpClient) {
  }

  public getInfo(): Observable<InfoDto> {
    return this.http.get<InfoDto>(this.apiPath);
  }

  public getSupportedVersions(): Observable<string[]> {
    return this.getInfo().pipe(map((info: InfoDto) => {
      return info.supportedAccVersions;
    }));
  }
}
