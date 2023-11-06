import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { Estoque } from "../models/estoque.model";

@Injectable({
    providedIn: 'root'
})
export class EstoqueService{
    private API: string = 'http://localhost:8080/api/estoque';

    constructor(private http: HttpClient){}

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
}