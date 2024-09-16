import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';


@Injectable({
  providedIn: 'root'
})
export class AuthService {

  API_USUARIO = environment.apiUrl+'auth';

  constructor(private http: HttpClient) { }

  // Método para registrar um novo usuário
  register(userData: any): Observable<any> {
    return this.http.post(`${this.API_USUARIO}/register`, userData);
  }

  // Método para logar o usuário
  login(credentials: any): Observable<any> {
    return this.http.post(`${this.API_USUARIO}/login`, credentials);
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
