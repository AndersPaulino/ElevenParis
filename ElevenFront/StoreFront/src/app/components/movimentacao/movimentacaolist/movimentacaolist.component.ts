import { DatePipe } from '@angular/common';
import { Component, EventEmitter, Inject, Input, Output, inject } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { MovimentacaoService } from 'src/app/services/movimentacao.service';
import { Movimentacao } from 'src/app/models/movimentacao.spec';
import { NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { Produto } from 'src/app/models/produto';

@Component({
  selector: 'app-movimentacaolist',
  templateUrl: './movimentacaolist.component.html',
  styleUrls: ['./movimentacaolist.component.scss']
})
export class MovimentacaolistComponent {
  list: Movimentacao[] = [];
  list2: Produto[] = [];

  movimentacaoSelecionadoParaEdicao: Movimentacao = new Movimentacao ();
  indiceSelecionadoParaEdicao!: number;

  @Input() movimentacao: Movimentacao = new Movimentacao();
  @Output() retorno = new EventEmitter<Movimentacao>();
  @Input() modoLancamento: boolean = false;

  modalService = inject(NgbModal);
  modalRef!: NgbModalRef;
  movimentacaoService = inject(MovimentacaoService);
  router: any;

  constructor(private datePipe: DatePipe, router: Router){
    this.listAll();
  }

  listAll(){
    this.movimentacaoService.findAll().subscribe({
      next: list => {
        this.list = list;
      },
      error: erro =>{
        alert('Observe o erro no console!');
        console.error(erro);
      }
    });
  }
  adicionar(modal: any) {
    this.movimentacaoSelecionadoParaEdicao = new Movimentacao();
    this.modalRef = this.modalService.open(modal, { size: 'sm'});
  }

  addOuEditarMovimentacao(movimentacao: Movimentacao) {
    const onComplete = () => {
      this.listAll();
      this.modalRef.dismiss();
    };
  
    if (movimentacao.id) {
      // Atualiza a movimentação existente
      this.movimentacaoService.atualizar(movimentacao.id, movimentacao).subscribe(onComplete);
      
      // Calcula o valor total da Movimentação
      const valorTotal = this.calcularValorTotal(movimentacao);
      
      // Calcula o total de produtos da Movimentação
      const totalProduto = this.calcularTotalProduto(movimentacao);
      
      // Faça o que precisar com valorTotal e totalProduto aqui
      console.log('Valor Total:', valorTotal);
      console.log('Total de Produtos:', totalProduto);
    } else {
      // Se você precisar adicionar alguma lógica adicional para inserir uma nova movimentação,
      // faça isso aqui
    }
  }
  
  // Função para calcular o valor total da Movimentação
  calcularValorTotal(movimentacao: Movimentacao): number {
    // Calcula o valor total da Movimentação com base em valorVenda e valorCompra
    const valorTotal = movimentacao.valorVenda - movimentacao.valorCompra;
  
    return valorTotal;
  }
  
  // Função para calcular o total de produtos da Movimentação
  calcularTotalProduto(movimentacao: Movimentacao): number {
    // Calcula o total de produtos da Movimentação com base em entrada e saída
    const totalProduto = movimentacao.entrada - movimentacao.saida;
  
    return totalProduto;
  }
  

  editar(modal: any, movimentacao: Movimentacao, indice: number){
    this.movimentacaoSelecionadoParaEdicao = { ...movimentacao};
    this.indiceSelecionadoParaEdicao = indice;
    this.modalRef = this.modalService.open(modal, { size: 'sm'});
  }

  voltar() {
    this.router.navigate(['/admin/estoque']);
  }
}
