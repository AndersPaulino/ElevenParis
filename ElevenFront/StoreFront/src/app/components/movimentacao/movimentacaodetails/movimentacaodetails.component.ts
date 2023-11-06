import { Component, EventEmitter, Input, Output, inject } from '@angular/core';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { Movimentacao } from 'src/app/models/movimentacao.spec';
import { MovimentacaoService } from 'src/app/services/movimentacao.service';

@Component({
  selector: 'app-movimentacaodetails',
  templateUrl: './movimentacaodetails.component.html',
  styleUrls: ['./movimentacaodetails.component.scss']
})
export class MovimentacaodetailsComponent {

  @Input() movimentacao: Movimentacao = new Movimentacao();
  @Output() retorno = new EventEmitter<Movimentacao>();

  modalService = inject(NgbModal);
  modalRef!: NgbModalRef;
  movimentacaoService = inject(MovimentacaoService);

  constructor(){}

  salvar():void{
    this.retorno.emit(this.movimentacao);
  }

  lancar(modal: any): void{
    this,this.modalRef = this.modalService.open(modal, {size: 'lg'});
  }
}
