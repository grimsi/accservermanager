import { Observable } from 'rxjs';
import { HttpResponse } from '@angular/common/http';

export interface AuthApi {
  login(username: string, password: string): Observable<HttpResponse<Response>>;
}
