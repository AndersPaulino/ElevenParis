import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Movimentacao } from '../models/movimentacao.model';

@Injectable({
  providedIn: 'root'
})
export class MovimentacaoService {
  private API: string = 'http://localhost:8080/api/movimentacao';

  constructor(private http: HttpClient) { }

  private getAuthHeaders(): HttpHeaders {
    const token = localStorage.getItem('authToken');
    return new HttpHeaders({
      'Authorization': `Bearer ${token}`
    });
  }

  findAll(): Observable<Movimentacao[]> {
    return this.http.get<Movimentacao[]>(this.API, { headers: this.getAuthHeaders() });
  }

  cadastrar(movimentacao: Movimentacao): Observable<string> {
    return this.http.post(this.API, movimentacao, {
      headers: this.getAuthHeaders(),
      responseType: 'text'
    });
  }

  atualizar(id: number, movimentacao: Movimentacao): Observable<string> {
    const url = `${this.API}/atualizar/${id}`;
    return this.http.put(url, movimentacao, {
      headers: this.getAuthHeaders(),
      responseType: 'text'
    });
  }
}
