import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { TestBed } from '@angular/core/testing';
import { LoginService } from './login.service';
import { Login } from 'src/app/models/login.model';
import { Usuario } from 'src/app/models/usuario.model';

describe('LoginService', () => {
  let service: LoginService;
  let httpMock: HttpTestingController;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [LoginService]
    });
    service = TestBed.inject(LoginService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  afterEach(() => {
    httpMock.verify();
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('should log in a user', () => {
    const mockLogin: Login = { username: 'admin', password: 'admin' };
    const mockUser: Usuario = { id: 1, username: 'admin', role: 'admin', token: 'eyJhbGciOiJIUzI1NiJ9.eyJyb2xlIjoiYWRtaW4iLCJpZCI6IjEiLCJ1c2VybmFtZSI6ImFkbWluIiwic3ViIjoiYWRtaW4iLCJpYXQiOjE3MDE1NTE2NDYsImV4cCI6MTcwMTU1NTI0Nn0.iphyCq8Qk1gqHwMnt0_qxN8MtXoIX-ggAuinHq2nUdQ' };
  
    service.logar(mockLogin).subscribe((usuario: Usuario) => {
      expect(usuario).toEqual(mockUser);
    });
  
    const req = httpMock.expectOne('http://192.168.4.157:8080/api/login');
    expect(req.request.method).toBe('POST');
    req.flush(mockUser); // Simula a resposta do servidor
  });
  

  it('should decode a JWT token', () => {
    // Defina um token JWT válido para o teste
    const token = 'eyJhbGciOiJIUzI1NiJ9.eyJyb2xlIjoiYWRtaW4iLCJpZCI6IjEiLCJ1c2VybmFtZSI6ImFkbWluIiwic3ViIjoiYWRtaW4iLCJpYXQiOjE3MDE1NTE2NDYsImV4cCI6MTcwMTU1NTI0Nn0.iphyCq8Qk1gqHwMnt0_qxN8MtXoIX-ggAuinHq2nUdQ';

    localStorage.setItem('token', token);

    const decoded = service.jwtDecode();
    expect(decoded).toBeDefined();
    // Adicione asserções adicionais conforme necessário para o resultado da decodificação
  });

  // Escreva mais testes para outras funcionalidades do serviço, como hasPermission, addToken, etc.
});
