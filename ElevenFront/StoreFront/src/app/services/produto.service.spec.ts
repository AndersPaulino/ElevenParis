import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { ProdutoService } from './produto.service';
import { Produto } from '../models/produto';

describe('ProdutoService', () => {
  let service: ProdutoService;
  let httpMock: HttpTestingController;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [ProdutoService]
    });

    service = TestBed.inject(ProdutoService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  afterEach(() => {
    httpMock.verify();
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('should retrieve produto by id', () => {
    const mockProduto: Produto =   {
      id: 1,
      ativo: true,
      registro: '2023-12-01',
      atualizar: '2023-12-01',
      nome: 'Produto 1',
      tipo: { id: 1, nameTipo: 'Tipo 1', ativo: true, registro: '', atualizar: '', filtro: '' },
      descricao: 'Descrição do Produto 1',
      filtro: 'Filtro 1'
    };

    service.findById(1).subscribe((produto: Produto) => {
      expect(produto).toEqual(mockProduto);
    });

    const req = httpMock.expectOne('http://192.168.56.103:8080/api/produto/1');
    expect(req.request.method).toBe('GET');
    req.flush(mockProduto);
  });

  it('should retrieve all produtos', () => {
    const mockProdutos: Produto[] = [
      {
        id: 1,
        ativo: true,
        registro: '2023-12-01',
        atualizar: '2023-12-01',
        nome: 'Produto 1',
        tipo: { id: 1, nameTipo: 'Tipo 1', ativo: true, registro: '', atualizar: '', filtro: '' },
        descricao: 'Descrição do Produto 1',
        filtro: 'Filtro 1'
      },
      {
        id: 2,
        ativo: false,
        registro: '2023-12-02',
        atualizar: '2023-12-02',
        nome: 'Produto 2',
        tipo: { id: 2, nameTipo: 'Tipo 2', ativo: false, registro: '', atualizar: '', filtro: '' },
        descricao: 'Descrição do Produto 2',
        filtro: 'Filtro 2'
      },
    ];

    service.findAll().subscribe((produtos: Produto[]) => {
      expect(produtos).toEqual(mockProdutos);
    });

    const req = httpMock.expectOne('http://192.168.56.103:8080/api/produto');
    expect(req.request.method).toBe('GET');
    req.flush(mockProdutos);
  });

  it('should retrieve produto by nome', () => {
    const mockProduto: Produto =   {
      id: 1,
      ativo: true,
      registro: '2023-12-01',
      atualizar: '2023-12-01',
      nome: 'Produto 1',
      tipo: { id: 1, nameTipo: 'Tipo 1', ativo: true, registro: '', atualizar: '', filtro: '' },
      descricao: 'Descrição do Produto 1',
      filtro: 'Filtro 1'
    };

    service.findByNomeProduto('Produto1').subscribe((produto: Produto) => {
      expect(produto).toEqual(mockProduto);
    });

    const req = httpMock.expectOne('http://192.168.56.103:8080/api/produto/nome/Produto1');
    expect(req.request.method).toBe('GET');
    req.flush(mockProduto);
  });

  it('should retrieve produtos by ativo', () => {
    const mockProdutos: Produto[] = [
      {
        id: 1,
        ativo: true,
        registro: '2023-12-01',
        atualizar: '2023-12-01',
        nome: 'Produto 1',
        tipo: { id: 1, nameTipo: 'Tipo 1', ativo: true, registro: '', atualizar: '', filtro: '' },
        descricao: 'Descrição do Produto 1',
        filtro: 'Filtro 1'
      }
    ];

    service.findByAtivo(true).subscribe((produtos: Produto[]) => {
      expect(produtos).toEqual(mockProdutos);
    });

    const req = httpMock.expectOne('http://192.168.56.103:8080/api/produto/ativo/true');
    expect(req.request.method).toBe('GET');
    req.flush(mockProdutos);
  });

  it('should retrieve produtos by dia de registro', () => {
    const mockProdutos: Produto[] = [
      {
        id: 1,
        ativo: true,
        registro: '2023-12-01',
        atualizar: '2023-12-01',
        nome: 'Produto 1',
        tipo: { id: 1, nameTipo: 'Tipo 1', ativo: true, registro: '', atualizar: '', filtro: '' },
        descricao: 'Descrição do Produto 1',
        filtro: 'Filtro 1'
      }
    ];

    service.findByDiaRegistro('2023-12-01').subscribe((produtos: Produto[]) => {
      expect(produtos).toEqual(mockProdutos);
    });

    const req = httpMock.expectOne('http://192.168.56.103:8080/api/produto/registro/dia/2023-12-01');
    expect(req.request.method).toBe('GET');
    req.flush(mockProdutos);
  });

  it('should retrieve produtos by dia de atualizar', () => {
    const mockProdutos: Produto[] = [
      {
        id: 1,
        ativo: true,
        registro: '2023-12-01',
        atualizar: '2023-12-01',
        nome: 'Produto 1',
        tipo: { id: 1, nameTipo: 'Tipo 1', ativo: true, registro: '', atualizar: '', filtro: '' },
        descricao: 'Descrição do Produto 1',
        filtro: 'Filtro 1'
      }
    ];

    service.findByDiaAtualizar('2023-12-01').subscribe((produtos: Produto[]) => {
      expect(produtos).toEqual(mockProdutos);
    });

    const req = httpMock.expectOne('http://192.168.56.103:8080/api/produto/atualizar/dia/2023-12-01');
    expect(req.request.method).toBe('GET');
    req.flush(mockProdutos);
  });

  it('should add new produto', () => {
    const mockProduto: Produto =  {
      id: 1,
      ativo: true,
      registro: '2023-12-01',
      atualizar: '2023-12-01',
      nome: 'Produto 1',
      tipo: { id: 1, nameTipo: 'Tipo 1', ativo: true, registro: '', atualizar: '', filtro: '' },
      descricao: 'Descrição do Produto 1',
      filtro: 'Filtro 1'
    };

    service.cadastrar(mockProduto).subscribe((response: string) => {
      expect(response).toBe('Registro cadastrado com sucesso!');
    });

    const req = httpMock.expectOne('http://192.168.56.103:8080/api/produto');
    expect(req.request.method).toBe('POST');
    req.flush('Registro cadastrado com sucesso!');
  });

  it('should update produto by id', () => {
    const mockProduto: Produto =   {
      id: 1,
      ativo: true,
      registro: '2023-12-01',
      atualizar: '2023-12-01',
      nome: 'Produto 1',
      tipo: { id: 1, nameTipo: 'Tipo 1', ativo: true, registro: '', atualizar: '', filtro: '' },
      descricao: 'Descrição do Produto 1',
      filtro: 'Filtro 1'
    };

    service.atualizar(1, mockProduto).subscribe((response: string) => {
      expect(response).toBe('Registro atualizado com sucesso!');
    });

    const req = httpMock.expectOne('http://192.168.56.103:8080/api/produto/nome/1');
    expect(req.request.method).toBe('PUT');
    req.flush('Registro atualizado com sucesso!');
  });

  it('should delete produto by id', () => {
    service.deletar(1).subscribe((response: string) => {
      expect(response).toBe('Registro desativado com sucesso!');
    });

    const req = httpMock.expectOne('http://192.168.56.103:8080/api/produto/desativar/1');
    expect(req.request.method).toBe('DELETE');
    req.flush('Registro desativado com sucesso!');
  });

  it('should handle error on findById', () => {
    service.findById(1).subscribe(
      () => fail('Expected an error'),
      (error) => {
        expect(error.status).toBe(500);
        expect(error.statusText).toBe('Internal Server Error');
      }
    );

    const req = httpMock.expectOne('http://192.168.56.103:8080/api/produto/1');
    req.flush('Internal Server Error', { status: 500, statusText: 'Internal Server Error' });
  });

  // Add more tests for other error scenarios
});
