import { HttpClient } from "@angular/common/http";
import { Injectable, inject } from "@angular/core";
import { Observable } from "rxjs";
import { Estoque } from "../models/estoque.model";

@Injectable({
    providedIn: 'root'
})
export class EstoqueService{
    private API: string = 'http://172.21.132.206:8080/api/estoque';
    http = inject(HttpClient);
    constructor(){}

    findAll(): Observable<Estoque[]>{
        return this.http.get<Estoque[]>(this.API);
    }

    findById(id: number): Observable<Estoque> {
        const url = `${this.API}/${id}`;
        return this.http.get<Estoque>(url);
    }

    cadastrar(estoque: Estoque): Observable<String> {
        return this.http.post(this.API, estoque, { responseType: 'text'});
    }

    atualizar(id:number, estoque: Estoque): Observable<string> {
        const url = `${this.API}/nome/${id}`;
        return this.http.put(url, estoque, { responseType: 'text'});
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