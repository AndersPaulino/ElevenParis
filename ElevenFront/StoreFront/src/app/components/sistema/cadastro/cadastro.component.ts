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
        console.log(response.message);
        alert(response.message);
        this.limparFormulario();
        this.router.navigate(['/cadastro']);
      
      },
    );
  }


  limparFormulario() {
    this.cadastro = new Cadastro();
  }
}
