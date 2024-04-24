import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Tipo } from '../models/tipo';

@Injectable({
  providedIn: 'root'
})
export class TipoService {
  private API: string = 'http://172.22.89.72:8080/api/tipo';

  constructor(private http: HttpClient) {}

  listAll(): Observable<Tipo[]> {
    return this.http.get<Tipo[]>(this.API);
  }

  findById(id: number): Observable<Tipo> {
    const url = `${this.API}/${id}`;
    return this.http.get<Tipo>(url);
  }

  findByAtivo(ativo: boolean): Observable<Tipo[]> {
    const url = `${this.API}/ativo/${ativo}`;
    return this.http.get<Tipo[]>(url);
  }

  cadastrar(tipo: Tipo): Observable<string> {
    return this.http.post(this.API, tipo, { responseType: 'text' });
  }

  atualizar(id: number, tipo: Tipo): Observable<string> {
    const url = `${this.API}/nome/${id}`;
    return this.http.put(url, tipo, { responseType: 'text' });
  }

  desativar(id: number): Observable<string> {
    const url = `${this.API}/desativar/${id}`;
    return this.http.delete(url, { responseType: 'text' });
  }
}
