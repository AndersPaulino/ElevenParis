import { Component, EventEmitter, Input, Output, inject } from '@angular/core';
import { Tipo } from 'src/app/models/tipo';
import { TipoService } from 'src/app/services/tipo.service';
@Component({
  selector: 'app-tipodetails',
  templateUrl: './tipodetails.component.html',
  styleUrls: ['./tipodetails.component.scss']
})
export class TipodetailsComponent {
  @Input() tipo: Tipo = new Tipo();
  @Output() retorno = new EventEmitter<Tipo>();

  tipoService = inject(TipoService);

  constructor(){}

  salvar(){
    this.retorno.emit(this.tipo);
    console.log(this.tipo);
  }

  desativar() {
    this.tipo.ativo = false;
  }

  ativar() {
    this.tipo.ativo = true;
  }
}
