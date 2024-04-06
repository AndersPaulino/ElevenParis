import { Produto } from "./produto";

export class Movimentacao {
    id! : number;
    ativo!: boolean;
    registro!: string;
    atualizar!: string;
    produtos!: Produto;
    entrada!: number;
    saida!: number;
    totalProduto!: number;
    valorCompra!: number;
    valorVenda!: number;
    valorTotal!: number;
}
