import { Estoque } from "./estoque.model";
import { Movimentacao } from "./movimentacao.model";

// Importe as classes necessárias e o que mais for necessário para seus testes

describe('Estoque', () => {
    let estoque: Estoque;
  
    beforeEach(() => {
      estoque = new Estoque();
    });
  
    it('should create an instance of Estoque', () => {
        expect(estoque).toBeTruthy();
    });

    it('should initialize movimentacao as an empty array', () => {
        expect(estoque.movimentacao).toEqual([]);
    });

    it('should have properties initialized', () => {
        expect(estoque.id).toBeUndefined();
        expect(estoque.ativo).toBeUndefined();
        expect(estoque.registro).toBeUndefined();
        expect(estoque.atualizar).toBeUndefined();
        expect(estoque.nomeEstoque).toBeUndefined();
    });

    it('should add a Movimentacao to movimentacao array', () => {
        const movimentacao = new Movimentacao();
        estoque.movimentacao.push(movimentacao);

        expect(estoque.movimentacao.length).toBe(1);
        expect(estoque.movimentacao[0]).toBe(movimentacao);
    });

    it('should remove a Movimentacao from movimentacao array', () => {
        const movimentacao = new Movimentacao();
        estoque.movimentacao.push(movimentacao);

        estoque.movimentacao.pop();

        expect(estoque.movimentacao.length).toBe(0);
    });
  
  });
