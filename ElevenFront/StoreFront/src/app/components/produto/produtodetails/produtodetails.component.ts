import { Component, EventEmitter, Input, Output, inject } from '@angular/core';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { Produto } from 'src/app/models/produto';
import { Tipo } from 'src/app/models/tipo';
import { ProdutoService } from 'src/app/services/produto.service';
import { TipoListComponent } from '../../tipo/tipolist/tipolist.component';

@Component({
  selector: 'app-produtodetails',
  templateUrl: './produtodetails.component.html',
  styleUrls: ['./produtodetails.component.scss']
})
export class ProdutodetailsComponent {
  @Input() produto: Produto = new Produto();
  @Output() retorno = new EventEmitter<Produto>();
  
  modalService = inject(NgbModal);
  modalRef!: NgbModalRef;
  produtoService = inject(ProdutoService);

  constructor() {}

  salvar(): void {
    this.retorno.emit(this.produto);
  }

  retornoTipoList(tipo: Tipo): void {
    this.produto.tipo = tipo;
    this.modalRef.dismiss();
  }

  lancar(modal: any): void {
    this.modalRef = this.modalService.open(modal, { size: 'lg' });
  }
  
  abrirModalSelecaoTipo(): void {
    this.modalRef = this.modalService.open(TipoListComponent, { size: 'lg' });
    this.modalRef.componentInstance.tipoSelecionado.subscribe((tipo: Tipo) => {
      this.produto.tipo = tipo;
      this.modalRef.close();
    });
  }

  desativar() {
    this.produto.ativo = false;
  }

  ativar() {
    this.produto.ativo = true;
  }
}
