import { Component, inject } from '@angular/core';
import { Router } from '@angular/router';
import { Login } from 'src/app/models/login.model';
import { AuthService } from 'src/app/services/auth/authservice';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent {
  credentials = { login: '', password: '' };

  constructor(private authService: AuthService, private router: Router) {}

  logar() {
    this.authService.login(this.credentials).subscribe({
      next: (response) => {
        const token = response.token;  // Supondo que o token JWT esteja no campo 'token' da resposta
        this.authService.saveToken(token);
        this.router.navigate(['/admin/tipo']);  // Redirecionar para a página desejada após o login
      },
      error: (err) => {
        console.error('Erro no login', err);
      }
    });
  }
  cadastro(){
    this.router.navigate(['/cadastro']);
  }

}
