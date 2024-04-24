import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Produto } from '../models/produto';

@Injectable({
  providedIn: 'root'
})
export class ProdutoService {
  private API: string = 'http://172.22.89.72:8080/api/produto';

  constructor(private http: HttpClient) { }

  findById(id: number): Observable<Produto> {
    return this.http.get<Produto>(`${this.API}/${id}`);
  }

  findAll(): Observable<Produto[]> {
    return this.http.get<Produto[]>(this.API);
  }

  findByNomeProduto(nome: string): Observable<Produto> {
    return this.http.get<Produto>(`${this.API}/nome/${nome}`);
  }

  findByAtivo(ativo: boolean): Observable<Produto[]> {
    return this.http.get<Produto[]>(`${this.API}/ativo/${ativo}`);
  }

  findByDiaRegistro(registro: string): Observable<Produto[]> {
    return this.http.get<Produto[]>(`${this.API}/registro/dia/${registro}`);
  }

  findByDiaAtualizar(atualizar: string): Observable<Produto[]> {
    return this.http.get<Produto[]>(`${this.API}/atualizar/dia/${atualizar}`);
  }

  cadastrar(produto: Produto): Observable<string> {
    return this.http.post(this.API, produto, { responseType: 'text' });
  }

  atualizar(id: number, produto: Produto): Observable<string> {
    const url = `${this.API}/nome/${id}`;
    return this.http.put(url, produto, { responseType: 'text' });
  }

  deletar(id: number): Observable<string> {
    const url = `${this.API}/desativar/${id}`;
    return this.http.delete(url, { responseType: 'text' });
  }
}
