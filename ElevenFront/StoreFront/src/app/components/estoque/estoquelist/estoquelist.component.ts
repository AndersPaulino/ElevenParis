import { DatePipe } from '@angular/common';
import { Component, EventEmitter, Input, Output, inject } from '@angular/core';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { Estoque } from 'src/app/models/estoque.model';
import { EstoqueService } from 'src/app/services/estoque.service';
@Component({
  selector: 'app-estoquelist',
  templateUrl: './estoquelist.component.html',
  styleUrls: ['./estoquelist.component.scss']
})
export class EstoquelistComponent {

  list: Estoque[] = [];

  estoqueSelecionadoParaEdicao: Estoque = new Estoque();
  indiceSelecionadoParaEdicao!: number;

  @Output() retorno = new EventEmitter<Estoque>();
  @Input() modoLancamento: boolean = false;

  modalService = inject(NgbModal);
  modalRef!: NgbModalRef;

  estoqueService = inject(EstoqueService);

  constructor(private datePipe: DatePipe){
    this.listAll();
  }
  listAll() {
    this.estoqueService.findAll().subscribe({
      next: list => {
        this.list = list;
      },
      error: erro => {
        alert('Observe o erro no console!');
        console.error(erro);
      }
    });
  }

  adicionar(modal: any) {
    this.estoqueSelecionadoParaEdicao = new Estoque();
      this.modalRef = this.modalService.open(modal, { size: 'sm'});
  }

  addOuEditarEstoque(estoque: Estoque) {
    const onComplete = () => {
      this.listAll();
      this.modalRef.dismiss();
    };

    if(estoque.id) {
      this.estoqueService.atualizar(estoque.id, estoque).subscribe(onComplete);
    } else {
      this.estoqueService.cadastrar(estoque).subscribe(onComplete);
    }
  }
  
  editar(modal: any,  estoque: Estoque, indice: number){
    this.estoqueSelecionadoParaEdicao = { ...estoque};
    this.indiceSelecionadoParaEdicao = indice;
    this.modalRef = this.modalService.open(modal, { size: 'sm' });
  }
}
