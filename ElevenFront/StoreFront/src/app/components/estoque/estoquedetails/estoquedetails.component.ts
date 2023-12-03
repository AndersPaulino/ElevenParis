import { Component, EventEmitter, Input, Output, inject } from '@angular/core';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { Estoque } from 'src/app/models/estoque.model';
import { Movimentacao } from 'src/app/models/movimentacao.model';
import { EstoqueService } from 'src/app/services/estoque.service';
import { MovimentacaolistComponent } from '../../movimentacao/movimentacaolist/movimentacaolist.component';

@Component({
  selector: 'app-estoquedetails',
  templateUrl: './estoquedetails.component.html',
  styleUrls: ['./estoquedetails.component.scss']
})
export class EstoquedetailsComponent {
  
  @Input() estoque: Estoque = new Estoque();
  @Output() retorno = new EventEmitter<Estoque>();

  modalService = inject(NgbModal);
  modalRef!: NgbModalRef;
  estoqueService = inject(EstoqueService);

  constructor(){}

  salvar():void {
    this.retorno.emit(this.estoque);
  }

  lancar(modal: any): void{
    this,this.modalRef = this.modalService.open(modal, { size:'lg'});
  }

  retornoMovimentacaoList(movimentacao: Movimentacao): void {
    this.estoque.movimentacao = [];
    this.modalRef.dismiss();
  }

  abrirModalSelecaoMovimentacao(): void{
    this.modalRef = this.modalService.open(MovimentacaolistComponent, {size: 'lg'});
    this.estoque.movimentacao = [];
    this.modalRef.close();
  }
}
