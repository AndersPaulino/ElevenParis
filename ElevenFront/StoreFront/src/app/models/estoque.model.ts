import { Movimentacao } from "./movimentacao.spec";

export class Estoque {
    id!: number;
    ativo!: boolean;
    registro!: string;
    atualizar!: string;
    nomeEstoque!: string;
    movimentacao!: Movimentacao[];

    constructor(){
        this.movimentacao = [];
    }
} 