import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Produto } from '../models/produto';

@Injectable({
  providedIn: 'root'
})
export class ProdutoService {
  private API: string = 'http://localhost:8080/api/produto';

  constructor(private http: HttpClient) { }

  private getAuthHeaders(): HttpHeaders {
    const token = localStorage.getItem('authToken');
    return new HttpHeaders({
      'Authorization': `Bearer ${token}`
    });
  }

  findById(id: number): Observable<Produto> {
    return this.http.get<Produto>(`${this.API}/${id}`, { headers: this.getAuthHeaders() });
  }

  findAll(): Observable<Produto[]> {
    return this.http.get<Produto[]>(this.API, { headers: this.getAuthHeaders() });
  }

  findByNomeProduto(nome: string): Observable<Produto> {
    return this.http.get<Produto>(`${this.API}/nome/${nome}`, { headers: this.getAuthHeaders() });
  }

  findByAtivo(ativo: boolean): Observable<Produto[]> {
    return this.http.get<Produto[]>(`${this.API}/ativo/${ativo}`, { headers: this.getAuthHeaders() });
  }

  findByDiaRegistro(registro: string): Observable<Produto[]> {
    return this.http.get<Produto[]>(`${this.API}/registro/dia/${registro}`, { headers: this.getAuthHeaders() });
  }

  findByDiaAtualizar(atualizar: string): Observable<Produto[]> {
    return this.http.get<Produto[]>(`${this.API}/atualizar/dia/${atualizar}`, { headers: this.getAuthHeaders() });
  }

  cadastrar(produto: Produto): Observable<string> {
    return this.http.post(this.API, produto, {
      headers: this.getAuthHeaders(),
      responseType: 'text'
    });
  }

  atualizar(id: number, produto: Produto): Observable<string> {
    const url = `${this.API}/nome/${id}`;
    return this.http.put(url, produto, {
      headers: this.getAuthHeaders(),
      responseType: 'text'
    });
  }

  deletar(id: number): Observable<string> {
    const url = `${this.API}/desativar/${id}`;
    return this.http.delete(url, {
      headers: this.getAuthHeaders(),
      responseType: 'text'
    });
  }
}
