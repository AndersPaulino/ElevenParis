import { Component, EventEmitter, Input, Output, inject } from '@angular/core';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { Estoque } from 'src/app/models/estoque.model';
import { EstoqueService } from 'src/app/services/estoque.service';

@Component({
  selector: 'app-estoquemovimentacao',
  templateUrl: './estoquemovimentacao.component.html',
  styleUrls: ['./estoquemovimentacao.component.scss']
})
export class EstoquemovimentacaoComponent {

  @Input() estoque: Estoque = new Estoque();
  @Output() retorno = new EventEmitter<Estoque>();

  modalService = inject(NgbModal);
  modalRef!: NgbModalRef;
  estoqueService = inject(EstoqueService);

  constructor(){}

  salvar():void{
    this.retorno.emit(this.estoque);
  }

}
