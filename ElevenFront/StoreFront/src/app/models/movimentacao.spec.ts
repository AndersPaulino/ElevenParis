import { Movimentacao } from "./movimentacao.model";
import { Produto } from "./produto";
import { Tipo } from "./tipo";

describe('Movimentacao', () => {
  let movimentacao: Movimentacao;

  beforeEach(() => {
    movimentacao = new Movimentacao();
  });

  it('should create an instance', () => {
    expect(movimentacao).toBeTruthy();
  });

  it('should have default values', () => {
    expect(movimentacao.id).toBeUndefined();
    expect(movimentacao.ativo).toBeUndefined();
    expect(movimentacao.registro).toBeUndefined();
    expect(movimentacao.atualizar).toBeUndefined();
    expect(movimentacao.produtos).toBeUndefined();
    expect(movimentacao.entrada).toBeUndefined();
    expect(movimentacao.saida).toBeUndefined();
    expect(movimentacao.totalProduto).toBeUndefined();
    expect(movimentacao.valorCompra).toBeUndefined();
    expect(movimentacao.valorVenda).toBeUndefined();
    expect(movimentacao.valorTotal).toBeUndefined();
  });

  it('should update properties correctly', () => {
    const produto = new Produto();
    const tipo = new Tipo();

    movimentacao.id = 1;
    movimentacao.ativo = true;
    movimentacao.registro = '2023-12-03';
    movimentacao.atualizar = '2023-12-03';
    movimentacao.produtos = produto;
    movimentacao.entrada = 10;
    movimentacao.saida = 5;
    movimentacao.totalProduto = 5;
    movimentacao.valorCompra = 50.00;
    movimentacao.valorVenda = 75.00;
    movimentacao.valorTotal = 375.00;

    expect(movimentacao.id).toEqual(1);
    expect(movimentacao.ativo).toEqual(true);
    expect(movimentacao.registro).toEqual('2023-12-03');
    expect(movimentacao.atualizar).toEqual('2023-12-03');
    expect(movimentacao.produtos).toBe(produto);
    expect(movimentacao.entrada).toEqual(10);
    expect(movimentacao.saida).toEqual(5);
    expect(movimentacao.totalProduto).toEqual(5);
    expect(movimentacao.valorCompra).toEqual(50.00);
    expect(movimentacao.valorVenda).toEqual(75.00);
    expect(movimentacao.valorTotal).toEqual(375.00);
  });

});
