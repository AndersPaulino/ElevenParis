import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse, HttpHeaders } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { environment } from 'src/environments/environment';
import { catchError, tap } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  API_USUARIO = environment.apiUrl+'auth';

  constructor(private http: HttpClient) { }

  login(credentials: any): Observable<any> {
    return this.http.post(`${this.API_USUARIO}/login`, {
      clientId: 'store_client',
      grantType: 'password',
      username: credentials.username,
      password: credentials.password
    }).pipe(
      tap((response: any) => {
        console.log('Resposta do servidor:', response);
        if (response.access_token) {
          console.log('Token recebido:', response.access_token);
          this.saveToken(response.access_token);
        } else {
          console.error('Token não encontrado na resposta.');
        }
      }),
      catchError((error) => {
        console.error('Erro ao fazer login:', error);
        return throwError(() => new Error('Falha ao fazer login, tente novamente.'));
      })
    );
  }
  

// Método para armazenar o token JWT no localStorage
saveToken(access_token: string): void {
  if (access_token) {
    localStorage.setItem('authToken', access_token);
    console.log('Token Salvo:', access_token); // Log para confirmação
  } else {
    console.error('Tentativa de salvar um token indefinido');
  }
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
