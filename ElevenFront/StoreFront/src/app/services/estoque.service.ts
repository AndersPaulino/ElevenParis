import { HttpClient, HttpHeaders } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { Estoque } from "../models/estoque.model";

@Injectable({
    providedIn: 'root'
})
export class EstoqueService {
    private API: string = 'http://localhost:8080/api/estoque';

    constructor(private http: HttpClient) { }

    private getAuthHeaders(): HttpHeaders {
        const token = localStorage.getItem('authToken');
        return new HttpHeaders({
            'Authorization': `Bearer ${token}`
        });
    }

    findAll(): Observable<Estoque[]> {
        return this.http.get<Estoque[]>(this.API, { headers: this.getAuthHeaders() });
    }

    findById(id: number): Observable<Estoque> {
        const url = `${this.API}/${id}`;
        return this.http.get<Estoque>(url, { headers: this.getAuthHeaders() });
    }

    cadastrar(estoque: Estoque): Observable<string> {
        return this.http.post(this.API, estoque, {
            headers: this.getAuthHeaders(),
            responseType: 'text'
        });
    }

    atualizar(id: number, estoque: Estoque): Observable<string> {
        const url = `${this.API}/nome/${id}`;
        return this.http.put(url, estoque, {
            headers: this.getAuthHeaders(),
            responseType: 'text'
        });
    }

    /*
    getMovimentacoesDoEstoque(idEstoque: number): Observable<Movimentacao[]> {
        // Assuming your API endpoint for fetching movimentacoes based on Estoque ID is /api/estoque/:idEstoque/movimentacoes
        const url = `${this.API}/${idEstoque}/movimentacoes`;
    
        // Assuming your API returns an array of Movimentacao objects
        return this.http.get<Movimentacao[]>(url);
    }
    */
}
