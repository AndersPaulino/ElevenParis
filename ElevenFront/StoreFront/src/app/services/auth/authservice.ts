import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { tap } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  API_USUARIO = environment.apiUrl+'auth';

  constructor(private http: HttpClient) { }

  // Método para registrar um novo usuário
  register(userData: any): Observable<any> {
    const headers = new HttpHeaders().set('Authorization', `Bearer eyJhbGciOiJSUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICIzbF95NnJHcV9KRXE5V2d5VTNDTldDMVgzVnJCd0c2X0Y2UUpZejVESXdvIn0.eyJleHAiOjE3MzAyNDQ2NjEsImlhdCI6MTczMDI0NDM2MSwianRpIjoiYmZlMmYxNjMtZTg1My00ZDQyLWIzM2QtMzljZWI5NjhmOGM5IiwiaXNzIjoiaHR0cDovLzE5Mi4xNjguNTYuMTA2OjgwODAvcmVhbG1zL2VsZXZlbiIsImF1ZCI6ImFjY291bnQiLCJzdWIiOiIyMDUwODhiYi05Y2M3LTQ3MDktOTA1ZS00OWFmOTkxNDYzNDQiLCJ0eXAiOiJCZWFyZXIiLCJhenAiOiJzdG9yZV9jbGllbnQiLCJzaWQiOiJkY2Y1YWU5ZS05NDQwLTRhODQtOWM0Zi0zMDJmMmMxNzRiYWEiLCJhY3IiOiIxIiwiYWxsb3dlZC1vcmlnaW5zIjpbIioiXSwicmVhbG1fYWNjZXNzIjp7InJvbGVzIjpbImRlZmF1bHQtcm9sZXMtZWxldmVuIiwib2ZmbGluZV9hY2Nlc3MiLCJ1bWFfYXV0aG9yaXphdGlvbiJdfSwicmVzb3VyY2VfYWNjZXNzIjp7ImFjY291bnQiOnsicm9sZXMiOlsibWFuYWdlLWFjY291bnQiLCJtYW5hZ2UtYWNjb3VudC1saW5rcyIsInZpZXctcHJvZmlsZSJdfX0sInNjb3BlIjoicHJvZmlsZSBlbWFpbCIsImVtYWlsX3ZlcmlmaWVkIjpmYWxzZSwibmFtZSI6IkFkbWluIFN0b3JlIiwicHJlZmVycmVkX3VzZXJuYW1lIjoiYWRtaW5fc3RvcmUiLCJnaXZlbl9uYW1lIjoiQWRtaW4iLCJmYW1pbHlfbmFtZSI6IlN0b3JlIiwiZW1haWwiOiJtYXRoZXVzLXAtYWd1aWFyQGhvdG1haWwuY29tIn0.X77bZopRnCzFjLh_APWBqJuEEzXWGaFYep0IcBnIOZaoSGqVLDj8B4StqrLQHZODGyXP3Ebnx1z6qDIsqeSpz-F8gkw66P4xDKQmdG9IQ1REd8owp9Of2oJDyK3DhDFL51Pk4JAaOE7SeD-0PkrNn4oX8VdNbc_YBJwT1Cy5_WnnhbUYWIqtTbKZiviu_bDUSTV8LQF4HFCwd9zF0P8Hl8sBC9RTxuJmx05N4yhfOyq3PMBEtUob22TYUHnRRQ3Gghq_i6Zk7EArf-AATneXlUfByI2lfg8YiXWkzUgGHkrsZYPB2SP3JxZoF3LZsCWpT_kHGKAMbzFv0T4yulG51A`);
    return this.http.post(`${this.API_USUARIO}/register`, {
      clientId: 'store_client',
      grantType: 'password',
      username: userData.username,
      password: userData.password,
      role: userData.role
    }, { headers });
  }
  

  // Método para logar o usuário
  login(credentials: any): Observable<any> {
    return this.http.post(`${this.API_USUARIO}/login`, {
      clientId: 'store_client',
      grantType: 'password',
      username: credentials.username,
      password: credentials.password
    }).pipe(
      tap((response: any) => {
        this.saveToken(response.access_token); // Armazena o token no localStorage
      })
    );
  }
  

  // Método para armazenar o token JWT no localStorage
  saveToken(token: string): void {
    localStorage.setItem('authToken', token);
  }

  // Método para obter o token JWT armazenado
  getToken(): string | null {
    return localStorage.getItem('authToken');
  }

  // Método para sair e limpar o token
  logout(): void {
    localStorage.removeItem('authToken');
  }
}
