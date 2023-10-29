import { Component, EventEmitter, Input, Output, inject } from '@angular/core';
import { DatePipe } from '@angular/common';
import { NgbModal , NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { Produto } from 'src/app/models/produto';
import { ProdutoService } from 'src/app/services/produto.service';
@Component({
  selector: 'app-produtolist',
  templateUrl: './produtolist.component.html',
  styleUrls: ['./produtolist.component.scss']
})
export class ProdutolistComponent {

  lista: Produto[] = [];

  produtoSelecionadoParaEdicao: Produto = new Produto();
  indiceSelecionadoParaEdicao!: number;

  @Output() retorno = new EventEmitter<Produto>();
  @Input() modoLancamento: boolean = false;

  modalService = inject(NgbModal);
  modalRef!: NgbModalRef;

  produtoService = inject(ProdutoService);

  constructor(private datePipe: DatePipe) {
    this.listAll();
  }

  listAll() {
    this.produtoService.findAll().subscribe({
      next: lista => {
        this.lista = lista;
      },
      error: erro => {
        alert('Observe o erro no console!');
        console.error(erro);
      }
    });
  }

  adicionar(modal: any) {
    this.produtoSelecionadoParaEdicao = new Produto();
    this.modalRef = this.modalService.open(modal, { size: 'sm' });
  }

  editar(modal: any, produto: Produto, indice: number) {
    this.produtoSelecionadoParaEdicao = { ...produto };
    this.indiceSelecionadoParaEdicao = indice;
    this.modalRef = this.modalService.open(modal, { size: 'sm' });
  }

  addOuEditarProduto(produto: Produto) {
    const onComplete = () => {
      this.listAll();
      this.modalRef.dismiss();
    };

    if (produto.id) {
      console.log("Aqui foi atualizar");
      this.produtoService.atualizar(produto.id, produto).subscribe(onComplete);
    } else {
      console.log("Aqui foi cadastrar");
      this.produtoService.cadastrar(produto).subscribe(onComplete);
    }
  }

  deletar(id: number) {
    this.produtoService.deletar(id).subscribe(() => this.listAll());
  }

  lancamento(produto: Produto){
    this.retorno.emit(produto);
  }

  // Declarações de variáveis para a ordenação
  sortByIdDirection: 'asc' | 'desc' = 'asc';
  sortNameDirection: 'asc' | 'desc' = 'asc';
  sortNameTipoDirection: 'asc' | 'desc' = 'asc';
  sortStatusDirection: 'asc' | 'desc' = 'asc';
  sortRegistroDirection: 'asc' | 'desc' = 'asc';
  sortAtualizarDirection: 'asc' | 'desc' = 'asc';
  sortDescricaoDirection: 'asc' | 'desc' = 'asc';

  // Função de ordenação para a coluna "ID"
  sortById() {
    this.sortByIdDirection = this.sortByIdDirection === 'asc' ? 'desc' : 'asc';
    this.lista.sort((a, b) => (this.sortByIdDirection === 'asc' ? a.id - b.id : b.id - a.id));
  }

  // Função de ordenação para a coluna "Nome"
  sortName() {
    this.sortNameDirection = this.sortNameDirection === 'asc' ? 'desc' : 'asc';
    this.lista.sort((a, b) =>
      this.sortNameDirection === 'asc' ? a.nome.localeCompare(b.nome) : b.nome.localeCompare(a.nome)
    );
  }

  // Função de ordenação para a coluna "Nome do Tipo"
  sortNameTipo() {
    this.sortNameTipoDirection = this.sortNameTipoDirection === 'asc' ? 'desc' : 'asc';
    this.lista.sort((a, b) =>
      this.sortNameTipoDirection === 'asc' ? a.tipo.nameTipo.localeCompare(b.tipo.nameTipo) : b.tipo.nameTipo.localeCompare(a.tipo.nameTipo)
    );
  }

  // Função de ordenação para a coluna "Status"
  sortStatus() {
    this.sortStatusDirection = this.sortStatusDirection === 'asc' ? 'desc' : 'asc';
    this.lista.sort((a, b) => {
      if (this.sortStatusDirection === 'asc') {
        return a.ativo === b.ativo ? 0 : a.ativo ? -1 : 1;
      } else {
        return b.ativo === a.ativo ? 0 : b.ativo ? -1 : 1;
      }
    });
  }

  // Função de ordenação para a coluna "Registrado Em"
  sortRegistro() {
    this.sortRegistroDirection = this.sortRegistroDirection === 'asc' ? 'desc' : 'asc';
    this.lista.sort((a, b) =>
      this.sortRegistroDirection === 'asc'
        ? a.registro.localeCompare(b.registro)
        : b.registro.localeCompare(a.registro)
    );
  }

  // Função de ordenação para a coluna "Atualizado Em"
  sortAtualizar() {
    this.sortAtualizarDirection = this.sortAtualizarDirection === 'asc' ? 'desc' : 'asc';
    this.lista.sort((a, b) =>
      this.sortAtualizarDirection === 'asc'
        ? a.atualizar.localeCompare(b.atualizar)
        : b.atualizar.localeCompare(a.atualizar)
    );
  }

  // Função de ordenação para a coluna "Descrição"
  sortDescricao() {
    this.sortDescricaoDirection = this.sortDescricaoDirection === 'asc' ? 'desc' : 'asc';
    this.lista.sort((a, b) =>
      this.sortDescricaoDirection === 'asc' ? a.descricao.localeCompare(b.descricao) : b.descricao.localeCompare(a.descricao)
    );
  }

  formatRegistro(data: string): string {
  const formattedDate = this.datePipe.transform(data, 'dd/MM/yyyy HH:mm:ss');
  return formattedDate || data;
}

formatAtualizar(data: string): string {
  const formattedDate = this.datePipe.transform(data, 'dd/MM/yyyy HH:mm:ss');
  return formattedDate || data;
}

}
