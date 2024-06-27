import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { TestBed } from '@angular/core/testing';
import { CadastroService } from './cadastro.service';
import { Cadastro } from 'src/app/models/cadastro.model';

describe('CadastroService', () => {
  let service: CadastroService;
  let httpTestingController: HttpTestingController;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [CadastroService],
    });
    service = TestBed.inject(CadastroService);
    httpTestingController = TestBed.inject(HttpTestingController);
  });

  afterEach(() => {
    httpTestingController.verify(); // Verifica se todas as solicitações foram tratadas
  });

  it('should create a user', () => {
    const mockUser: Cadastro = { id: 1,username: 'teste', password:"teste", role:"admin"};

    service.cadastrar(mockUser).subscribe((user) => {
      expect(user).toEqual(mockUser); // Verifica se o usuário retornado é o mesmo que foi enviado
    });

    const req = httpTestingController.expectOne('http://192.168.56.102:8080/api/users');
    expect(req.request.method).toEqual('POST'); // Verifica se a solicitação é um POST

    req.flush(mockUser); // Simula a resposta da API com os dados do usuário mockado
  });
});
