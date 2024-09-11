import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private apiUrl = 'http://localhost:8080/api/auth';  // URL do backend

  constructor(private http: HttpClient) { }

  // Método para registrar um novo usuário
  register(userData: any): Observable<any> {
    return this.http.post(`${this.apiUrl}/register`, userData);
  }

  // Método para logar o usuário
  login(credentials: any): Observable<any> {
    return this.http.post(`${this.apiUrl}/login`, credentials);
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
