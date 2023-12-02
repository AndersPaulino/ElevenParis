import { Produto } from './produto';
import { Tipo } from './tipo';

describe('Produto', () => {
  it('should create an instance', () => {
    const produto = new Produto();
    expect(produto).toBeTruthy();
  });

  it('should have default values for properties', () => {
    const produto = new Produto();
    expect(produto.id).toBeUndefined();
    expect(produto.ativo).toBeUndefined();
    expect(produto.registro).toBeUndefined();
    expect(produto.atualizar).toBeUndefined();
    expect(produto.nome).toBeUndefined();
    expect(produto.tipo).toBeUndefined();
    expect(produto.descricao).toBeUndefined();
    expect(produto.filtro).toBe('');
  });

  it('should update properties using setter methods', () => {
    const produto = new Produto();

    produto.id = 1;
    produto.ativo = true;
    produto.registro = '2023-12-01';
    produto.atualizar = '2023-12-01';
    produto.nome = 'Produto1';
    produto.tipo = new Tipo();
    produto.descricao = 'Descrição do Produto1';
    produto.filtro = 'Filtro1';

    expect(produto.id).toBe(1);
    expect(produto.ativo).toBe(true);
    expect(produto.registro).toBe('2023-12-01');
    expect(produto.atualizar).toBe('2023-12-01');
    expect(produto.nome).toBe('Produto1');
    expect(produto.tipo instanceof Tipo).toBe(true);
    expect(produto.descricao).toBe('Descrição do Produto1');
    expect(produto.filtro).toBe('Filtro1');
  });
});
