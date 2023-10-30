import { Component, inject } from '@angular/core';
import { Router } from '@angular/router';
import { Usuario } from 'src/app/models/usuario.model';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent {
  router = inject(Router);

  usuario: Usuario = new Usuario();

  logar(){
    if(this.usuario.login != "admin"){
      alert("Usuário Incorreto!");
    }if(this.usuario.senha != "admin"){
      alert("Senha Incorreto!");
    }if(this.usuario.login != "admin" && this.usuario.senha != "admin"){
      alert("Usuário e senha incorretos!")
    }if(this.usuario.login == "admin" && this.usuario.senha == "admin"){
      this.router.navigate(["/admin"]);
    }
  }
  cadastro(){
    this.router.navigate(["/cadastro"]);
  }

}
