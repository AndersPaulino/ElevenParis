import { Movimentacao } from "./movimentacao.model";

export class Estoque {
    id!: number;
    ativo!: boolean;
    registro!: Date;
    atualizar!: Date;
    nomeEstoque!: string;
    movimentacao!: Movimentacao[];

    constructor(){
        this.movimentacao = [];
    }
    
} 