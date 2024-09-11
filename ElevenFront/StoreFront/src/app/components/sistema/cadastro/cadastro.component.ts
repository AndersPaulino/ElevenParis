import { Component, EventEmitter, Input, Output, inject } from '@angular/core';
import { Router } from '@angular/router';
import { Cadastro } from 'src/app/models/cadastro.model';
import { AuthService } from 'src/app/services/auth/authservice';

@Component({
  selector: 'app-cadastro',
  templateUrl: './cadastro.component.html',
  styleUrls: ['./cadastro.component.scss']
})
export class CadastroComponent {
  userData = { login: '', password: '', role: 'ADMIN' };  // Adicione outros campos conforme necessário

  constructor(private authService: AuthService, private router: Router) {}

  register() {
    this.authService.register(this.userData).subscribe({
      next: (response) => {
        this.router.navigate(['/login']);  // Redirecionar para a página de login após o registro
      },
      error: (err) => {
        console.error('Erro no registro', err);
      }
    });
  }

  logar(){
    this.router.navigate(["/login"]);
  }
  
}