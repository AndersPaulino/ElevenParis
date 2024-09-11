import { DatePipe } from '@angular/common';
import { Component, EventEmitter, Input, Output, inject } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { MovimentacaoService } from 'src/app/services/movimentacao.service';
import { Movimentacao } from 'src/app/models/movimentacao.model';
import { Produto } from 'src/app/models/produto';

@Component({
  selector: 'app-movimentacaolist',
  templateUrl: './movimentacaolist.component.html',
  styleUrls: ['./movimentacaolist.component.scss']
})
export class MovimentacaolistComponent {
  list: Movimentacao[] = [];
  list2: Produto[] = [];

  movimentacaoSelecionadoParaEdicao: Movimentacao = new Movimentacao();
  indiceSelecionadoParaEdicao!: number;

  @Input() movimentacao: Movimentacao = new Movimentacao();
  @Output() retorno = new EventEmitter<Movimentacao>();
  @Input() modoLancamento: boolean = false;

  modalService = inject(NgbModal);
  modalRef!: NgbModalRef;
  movimentacaoService = inject(MovimentacaoService);

  constructor(private datePipe: DatePipe, private router: Router) {
    this.listAll();
  }

  listAll() {
    this.movimentacaoService.findAll().subscribe({
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
    this.movimentacaoSelecionadoParaEdicao = new Movimentacao();
    this.modalRef = this.modalService.open(modal, { size: 'sm' });
  }

  addOuEditarMovimentacao(movimentacao: Movimentacao) {
    const onComplete = () => {
      this.listAll();
      this.modalRef.dismiss();
    };

    if (movimentacao.id) {
      this.movimentacaoService.atualizar(movimentacao.id, movimentacao).subscribe(onComplete);
    } else {
      this.movimentacaoService.cadastrar(movimentacao).subscribe(onComplete);
    }
  }

  editar(modal: any, movimentacao: Movimentacao, indice: number) {
    this.movimentacaoSelecionadoParaEdicao = { ...movimentacao };
    this.indiceSelecionadoParaEdicao = indice;
    this.modalRef = this.modalService.open(modal, { size: 'sm' });
  }

  voltar() {
    this.router.navigate(['/admin/estoque']);
  }

  lancamento(movimentacao: Movimentacao) {
    this.retorno.emit(movimentacao);
  }
}
