import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Tipo } from '../models/tipo';
import { HttpHeaders } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class TipoService {
  private API: string = 'http://localhost:8080/api/tipo';

  constructor(private http: HttpClient) {}

  private getAuthHeaders() {
    const token = localStorage.getItem('authToken');
    return new HttpHeaders({
      'Authorization': `Bearer ${token}`
    });
  }

  listAll(): Observable<Tipo[]> {
    return this.http.get<Tipo[]>(this.API, { headers: this.getAuthHeaders() });
  }

  findById(id: number): Observable<Tipo> {
    const url = `${this.API}/${id}`;
    return this.http.get<Tipo>(url, { headers: this.getAuthHeaders() });
  }

  findByAtivo(ativo: boolean): Observable<Tipo[]> {
    const url = `${this.API}/ativo/${ativo}`;
    return this.http.get<Tipo[]>(url, { headers: this.getAuthHeaders() });
  }

  cadastrar(tipo: Tipo): Observable<string> {
    return this.http.post(this.API, tipo, {
      headers: this.getAuthHeaders(),
      responseType: 'text'
    });
  }

  atualizar(id: number, tipo: Tipo): Observable<string> {
    const url = `${this.API}/nome/${id}`;
    return this.http.put(url, tipo, {
      headers: this.getAuthHeaders(),
      responseType: 'text'
    });
  }

  desativar(id: number): Observable<string> {
    const url = `${this.API}/desativar/${id}`;
    return this.http.delete(url, {
      headers: this.getAuthHeaders(),
      responseType: 'text'
    });
  }
}
