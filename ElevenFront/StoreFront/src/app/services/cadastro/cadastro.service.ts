import { HttpClient } from "@angular/common/http";
import { Injectable, inject } from "@angular/core";
import { Observable } from "rxjs";
import { Cadastro } from "src/app/models/cadastro.model";

@Injectable({

    providedIn: 'root'

})
export class CadastroService {
    API: string = 'http://172.22.89.72:8080/api/users';
    http = inject(HttpClient);

    constructor(){}

    cadastrar(cadastro: Cadastro): Observable<Cadastro> {
        return this.http.post<Cadastro>(this.API, cadastro);
    }
}