import { Component, EventEmitter, Input, Output, inject } from '@angular/core';
import { Router } from '@angular/router';
import { Cadastro } from 'src/app/models/cadastro.model';
import { CadastroService } from 'src/app/services/cadastro/cadastro.service';

@Component({
  selector: 'app-cadastro',
  templateUrl: './cadastro.component.html',
  styleUrls: ['./cadastro.component.scss']
})
export class CadastroComponent {
  router = inject(Router);
  cadastroService = inject(CadastroService);

  cadastro: Cadastro = new Cadastro();

  constructor(){}

  logar(){
    this.router.navigate(["/login"]);
  }
  
  cadastrar() {
    this.cadastroService.cadastrar(this.cadastro).subscribe(
      (response: any) => {
        if(response.status == 200 || response.status == 201){
        console.log('Usuário cadastrado com sucesso!', response);
        alert('Usuário cadastrado com sucesso!');
        this.limparFormulario();
        this.router.navigate(['/cadastro']);
        } else {
          alert('Erro ao cadastrar Usuário!');
        }
      },
    );
  }


  limparFormulario() {
    this.cadastro = new Cadastro();
  }
}
