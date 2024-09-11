import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { EstoqueService } from './estoque.service';
import { Estoque } from '../models/estoque.model';
import { Movimentacao } from '../models/movimentacao.model';

describe('EstoqueService', () => {
  let service: EstoqueService;
  let httpMock: HttpTestingController;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [EstoqueService]
    });

    service = TestBed.inject(EstoqueService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  afterEach(() => {
    httpMock.verify();
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('should retrieve all estoques', () => {
    const mockEstoques: Estoque[] = [
      { id: 1, ativo: true, registro: new Date(), atualizar: new Date(), nomeEstoque: 'Estoque1', movimentacao: [] },
      { id: 2, ativo: true, registro: new Date(), atualizar: new Date(), nomeEstoque: 'Estoque2', movimentacao: [] }
    ];

    service.findAll().subscribe((estoques: Estoque[]) => {
      expect(estoques).toEqual(mockEstoques);
    });

    const req = httpMock.expectOne('http://localhost:8080/api/estoque');
    expect(req.request.method).toBe('GET');
    req.flush(mockEstoques);
  });

  it('should retrieve estoque by id', () => {
    const mockEstoque: Estoque = { id: 1, ativo: true, registro: new Date(), atualizar: new Date(), nomeEstoque: 'Estoque1', movimentacao: [] };

    service.findById(1).subscribe((estoque: Estoque) => {
      expect(estoque).toEqual(mockEstoque);
    });

    const req = httpMock.expectOne('http://localhost:8080/api/estoque/1');
    expect(req.request.method).toBe('GET');
    req.flush(mockEstoque);
  });

  it('should add new estoque', () => {
    const mockEstoque: Estoque = { id: 1, ativo: true, registro: new Date(), atualizar: new Date(), nomeEstoque: 'Estoque1', movimentacao: [] };
  
    service.cadastrar(mockEstoque).subscribe((response: String) => {
      expect(response).toBe('Registro cadastrado com sucesso!');
    });
  
    const req = httpMock.expectOne('http://localhost:8080/api/estoque');
    expect(req.request.method).toBe('POST');
    req.flush('Registro cadastrado com sucesso!');
  });
  
  

  it('should update estoque by id', () => {
    const mockEstoque: Estoque = { id: 1, ativo: true, registro: new Date(), atualizar: new Date(), nomeEstoque: 'Estoque1', movimentacao: [] };

    service.atualizar(1, mockEstoque).subscribe((response: string) => {
      expect(response).toBe('Registro atualizado com sucesso!');
    });

    const req = httpMock.expectOne('http://localhost:8080/api/estoque/nome/1');
    expect(req.request.method).toBe('PUT');
    req.flush('Registro atualizado com sucesso!');
  });

});
