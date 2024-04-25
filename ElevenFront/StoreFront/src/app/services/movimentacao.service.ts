import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { Movimentacao } from "../models/movimentacao.model";

@Injectable({
    providedIn: 'root'
})
export class MovimentacaoService{
    private API: string = 'http://192.168.92.18:8080/api/movimentacao';

    constructor(private http: HttpClient){}

    findAll(): Observable<Movimentacao[]>{
        return this.http.get<Movimentacao[]>(this.API);
    }

    cadastar(movimentacao: Movimentacao): Observable<String>{
        return this.http.post(this.API, movimentacao, { responseType: 'text'});
    }

    atualizar(id:number, movimentacao: Movimentacao): Observable<string> {
        const url = `${this.API}/atualizar/${id}`;
        return this.http.put(url, movimentacao, {responseType: 'text'});
    }
}