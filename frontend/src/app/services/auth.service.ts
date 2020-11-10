import { Injectable } from '@angular/core';
import { AuthApi } from '../api/AuthApi';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { AuthBody } from '../models/objects/AuthBody';
import { Observable } from 'rxjs';
import { JwtHelperService } from '@auth0/angular-jwt';
import { Router } from '@angular/router';
import { UserService } from './user.service';

@Injectable({
  providedIn: 'root'
})
export class AuthService implements AuthApi {

  private readonly apiPath = '/login';
  private readonly tokenStorageKey = 'token';
  private readonly jwt = new JwtHelperService();

  constructor(private http: HttpClient,
              private router: Router,
              private userService: UserService) {
  }

  public getToken(): string {
    const token: string = localStorage.getItem(this.tokenStorageKey);
    return token;
  }

  public setToken(tokenHeader: string): void {
    const token = tokenHeader.substring(7);
    localStorage.setItem(this.tokenStorageKey, token);
  }

  public isAuthenticated(): boolean {
    return !this.jwt.isTokenExpired(this.getToken());
  }

  public login(username: string, password: string): Observable<HttpResponse<Response>> {
    return this.http.post<Response>(this.apiPath, new AuthBody(username, password), {observe: 'response'});
  }

  public logout(redirect: boolean = true): void {
    if (redirect) {
      this.router.navigate(['/logout']);
    }
    localStorage.removeItem(this.tokenStorageKey);
    this.userService.removeKey();
  }
}
