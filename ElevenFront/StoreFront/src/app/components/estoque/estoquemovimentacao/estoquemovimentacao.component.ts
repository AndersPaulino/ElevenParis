import { Component, EventEmitter, Input, OnInit, Output, inject } from '@angular/core';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { Estoque } from 'src/app/models/estoque.model';
import { Movimentacao } from 'src/app/models/movimentacao.model';
import { EstoqueService } from 'src/app/services/estoque.service';
import { MovimentacaodetailsComponent } from '../../movimentacao/movimentacaodetails/movimentacaodetails.component';

@Component({
  selector: 'app-estoquemovimentacao',
  templateUrl: './estoquemovimentacao.component.html',
  styleUrls: ['./estoquemovimentacao.component.scss']
})
export class EstoquemovimentacaoComponent implements OnInit {
  @Input() estoque: Estoque = new Estoque();
  @Output() retorno = new EventEmitter<Estoque>();

  modalService = inject(NgbModal);
  modalRef!: NgbModalRef;
  estoqueService = inject(EstoqueService);

  ngOnInit(): void {
      this.findById(this.estoque.id);
  }

  findById(idEstoque: number){
    console.log(idEstoque); 
  }

  constructor() {

  } 

  salvar(): void {
    console.log(this.estoque);
    this.retorno.emit(this.estoque);
  }

  lancar(modal: any): void {
    this.modalRef = this.modalService.open(modal, { size: 'lg' });
  }

  retornoMovimentacaoList(movimentacao: Movimentacao) {
    // Assuming 'estoque' has a property named 'movimentacoes' that is an array
    this.estoque.movimentacao.push(movimentacao);
    this.modalRef.dismiss();
  }

  abrirModalSelecaoMovimentancao(): void {
    this.modalRef = this.modalService.open(MovimentacaodetailsComponent, { size: 'lg' });
    this.modalRef.componentInstance.retorno.subscribe((movimentacao: Movimentacao) => {
      // Assuming 'estoque' has a property named 'movimentacoes' that is an array
      this.estoque.movimentacao.push(movimentacao);
      this.modalRef.close();
    });
  }
}
