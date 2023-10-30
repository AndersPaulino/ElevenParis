import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";

@Injectable({
    providedIn: 'root'
})
export class EstoqueService{
    private API: string = 'http://localhost:8080/api/estoque';

    constructor(private htto: HttpClient){}

    
}