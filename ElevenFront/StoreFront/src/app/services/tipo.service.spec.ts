import { TestBed, inject } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { TipoService } from './tipo.service';
import { Tipo } from '../models/tipo';

describe('TipoService', () => {
  let service: TipoService;
  let httpMock: HttpTestingController;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [TipoService]
    });

    service = TestBed.inject(TipoService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  afterEach(() => {
    httpMock.verify();
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('should retrieve all tipos', () => {
    const mockTipos: Tipo[] = [
      { id: 1, nameTipo: 'Tipo1', ativo: true, registro: '', atualizar: '', filtro: '' },
      { id: 2, nameTipo: 'Tipo2', ativo: false, registro: '', atualizar: '', filtro: '' }
    ];    

    service.listAll().subscribe((tipos: Tipo[]) => {
      expect(tipos).toEqual(mockTipos);
    });

    const req = httpMock.expectOne('http://192.168.56.103:8080/api/tipo');
    expect(req.request.method).toBe('GET');
    req.flush(mockTipos);
  });

  it('should retrieve tipo by id', () => {
    const mockTipo: Tipo = { id: 1, nameTipo: 'Tipo1', ativo: true, registro: '', atualizar: '', filtro: ''};

    service.findById(1).subscribe((tipo: Tipo) => {
      expect(tipo).toEqual(mockTipo);
    });

    const req = httpMock.expectOne('http://192.168.56.103:8080/api/tipo/1');
    expect(req.request.method).toBe('GET');
    req.flush(mockTipo);
  });

  it('should retrieve tipos by ativo', () => {
    const mockTipos: Tipo[] = [{id: 1, nameTipo: 'Tipo1', ativo: true, registro: '', atualizar: '', filtro: '' }];

    service.findByAtivo(true).subscribe((tipos: Tipo[]) => {
      expect(tipos).toEqual(mockTipos);
    });

    const req = httpMock.expectOne('http://192.168.56.103:8080/api/tipo/ativo/true');
    expect(req.request.method).toBe('GET');
    req.flush(mockTipos);
  });

  it('should add new tipo', () => {
    const mockTipo: Tipo = {id: 1, nameTipo: 'Tipo1', ativo: true, registro: '', atualizar: '', filtro: ''};

    service.cadastrar(mockTipo).subscribe((response: string) => {
      expect(response).toBe('Registro cadastrado com sucesso!');
    });

    const req = httpMock.expectOne('http://192.168.56.103:8080/api/tipo');
    expect(req.request.method).toBe('POST');
    req.flush('Registro cadastrado com sucesso!');
  });

  it('should update tipo by id', () => {
    const mockTipo: Tipo = {id: 1, nameTipo: 'Tipo1', ativo: true, registro: '', atualizar: '', filtro: ''};

    service.atualizar(1, mockTipo).subscribe((response: string) => {
      expect(response).toBe('Registro atualizado com sucesso!');
    });

    const req = httpMock.expectOne('http://192.168.56.103:8080/api/tipo/nome/1');
    expect(req.request.method).toBe('PUT');
    req.flush('Registro atualizado com sucesso!');
  });

  it('should delete tipo by id', () => {
    service.desativar(1).subscribe((response: string) => {
      expect(response).toBe('Registro desativado com sucesso!');
    });

    const req = httpMock.expectOne('http://192.168.56.103:8080/api/tipo/desativar/1');
    expect(req.request.method).toBe('DELETE');
    req.flush('Registro desativado com sucesso!');
  });

  it('should handle error on listAll', () => {
    service.listAll().subscribe(
      () => fail('Expected an error'),
      (error) => {
        expect(error.status).toBe(500);
        expect(error.statusText).toBe('Internal Server Error');
      }
    );

    const req = httpMock.expectOne('http://192.168.56.103:8080/api/tipo');
    req.flush('Internal Server Error', { status: 500, statusText: 'Internal Server Error' });
  });

});
