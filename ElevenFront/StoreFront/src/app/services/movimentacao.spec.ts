import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { MovimentacaoService } from './movimentacao.service';
import { Movimentacao } from '../models/movimentacao.model';
import { Produto } from '../models/produto';

describe('MovimentacaoService', () => {
  let service: MovimentacaoService;
  let httpMock: HttpTestingController;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [MovimentacaoService]
    });

    service = TestBed.inject(MovimentacaoService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  afterEach(() => {
    httpMock.verify();
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('should retrieve all movimentacoes', () => {
    const mockMovimentacoes: Movimentacao[] = [
      {
        id: 1,
        ativo: true,
        registro: '2023-12-01',
        atualizar: '2023-12-01',
        produtos: {
          id: 1,
          ativo: true,
          registro: '2023-12-01',
          atualizar: '2023-12-01',
          nome: 'Produto 1',
          tipo: { id: 1, nameTipo: 'Tipo 1', ativo: true, registro: '', atualizar: '', filtro: '' },
          descricao: 'Descrição do Produto 1',
          filtro: 'Filtro 1'
        },
        entrada: 10,
        saida: 5,
        totalProduto: 5,
        valorCompra: 100.0,
        valorVenda: 150.0,
        valorTotal: 750.0
      }
    ];

    service.findAll().subscribe((movimentacoes: Movimentacao[]) => {
      expect(movimentacoes).toEqual(mockMovimentacoes);
    });

    const req = httpMock.expectOne('http://192.168.56.102:8080/api/movimentacao');
    expect(req.request.method).toBe('GET');
    req.flush(mockMovimentacoes);
  });

  it('should add new movimentacao', () => {
    const mockMovimentacao: Movimentacao = {
      id: 1,
      ativo: true,
      registro: '2023-12-01',
      atualizar: '2023-12-01',
      produtos: {
        id: 1,
        ativo: true,
        registro: '2023-12-01',
        atualizar: '2023-12-01',
        nome: 'Produto 1',
        tipo: { id: 1, nameTipo: 'Tipo 1', ativo: true, registro: '', atualizar: '', filtro: '' },
        descricao: 'Descrição do Produto 1',
        filtro: 'Filtro 1'
      },
      entrada: 10,
      saida: 5,
      totalProduto: 5,
      valorCompra: 100.0,
      valorVenda: 150.0,
      valorTotal: 750.0
    };

    service.cadastar(mockMovimentacao).subscribe((response: String) => {
        expect(response).toBe('Registro cadastrado com sucesso!');
      });

    const req = httpMock.expectOne('http://192.168.56.102:8080/api/movimentacao');
    expect(req.request.method).toBe('POST');
    req.flush('Registro cadastrado com sucesso!');
  });

  it('should update movimentacao by id', () => {
    const mockMovimentacao: Movimentacao = {
      id: 1,
      ativo: true,
      registro: '2023-12-01',
      atualizar: '2023-12-01',
      produtos: {
        id: 1,
        ativo: true,
        registro: '2023-12-01',
        atualizar: '2023-12-01',
        nome: 'Produto 1',
        tipo: { id: 1, nameTipo: 'Tipo 1', ativo: true, registro: '', atualizar: '', filtro: '' },
        descricao: 'Descrição do Produto 1',
        filtro: 'Filtro 1'
      },
      entrada: 10,
      saida: 5,
      totalProduto: 5,
      valorCompra: 100.0,
      valorVenda: 150.0,
      valorTotal: 750.0
    };

    service.atualizar(1, mockMovimentacao).subscribe((response: string) => {
      expect(response).toBe('Registro atualizado com sucesso!');
    });

    const req = httpMock.expectOne('http://192.168.56.102:8080/api/movimentacao/atualizar/1');
    expect(req.request.method).toBe('PUT');
    req.flush('Registro atualizado com sucesso!');
  });

  
});
