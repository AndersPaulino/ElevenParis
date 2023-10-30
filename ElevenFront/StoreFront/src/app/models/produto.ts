import { Tipo } from "./tipo";

export class Produto {
    id!: number;
    ativo!: boolean;
    registro!: string;
    atualizar!: string;
    nome!: string;
    tipo!: Tipo;
    descricao!: string;
    filtro: string = '';
}
