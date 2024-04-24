import { HttpClient } from '@angular/common/http';
import { Injectable, inject } from '@angular/core';
import { Login } from 'src/app/models/login.model'; 
import { Usuario } from 'src/app/models/usuario.model';
import { Observable } from 'rxjs';
import { jwtDecode,JwtPayload } from 'jwt-decode';



@Injectable({
  providedIn: 'root'
})
export class LoginService {

  API: string = 'http://172.22.89.72:8080/api/login';
  http = inject(HttpClient);

  constructor() { }


  logar(login: Login): Observable<Usuario> {
    return this.http.post<Usuario>(this.API, login);
  }

  deslogar(): Observable<any> {
    return this.http.get<any>(this.API + '/deslogar');
  }



  addToken(token: string) {
    localStorage.setItem('token', token);
  }

  removerToken() {
    localStorage.removeItem('token');
  }

  getToken() {
    return localStorage.getItem('token');
  }


  jwtDecode() {
    let token = this.getToken();
    if (token) {
      return jwtDecode<JwtPayload>(token);
    }
    return "";
  }

  hasPermission(role: string) {
    let usuario = this.jwtDecode() as Usuario;
    if (usuario.role == role)
      return true;
    else
      return false;
  }
}