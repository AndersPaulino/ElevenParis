import { Component, EventEmitter, Input, Output, inject } from '@angular/core';
import { DatePipe } from '@angular/common';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { Tipo } from 'src/app/models/tipo';
import { TipoService } from 'src/app/services/tipo.service';
@Component({
  selector: 'app-tipolist',
  templateUrl: './tipolist.component.html',
  styleUrls: ['./tipolist.component.scss']
})
export class TipoListComponent {

  lista: Tipo[] = [];

  @Output() retorno = new EventEmitter<Tipo>();
  @Input() modoLancamento: boolean = false;

  tipoSelecionadoParaEdicao: Tipo = new Tipo();
  indiceSelecionadoParaEdicao!: number;

  modalService = inject(NgbModal);
  modalRef!: NgbModalRef;
  tipoService = inject(TipoService);

  constructor(private datePipe: DatePipe) {
    this.listAll();
  }

  listAll() {
    this.tipoService.listAll().subscribe({
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
    this.tipoSelecionadoParaEdicao = new Tipo();
    this.modalRef = this.modalService.open(modal, { size: 'sm' });
  }

  editar(modal: any, tipo: Tipo, indice: number) {
    this.tipoSelecionadoParaEdicao = { ...tipo };
    this.indiceSelecionadoParaEdicao = indice;
    this.modalRef = this.modalService.open(modal, { size: 'sm' });
  }

  addOuEditarTipo(tipo: Tipo) {
    const onComplete = () => {
      this.listAll();
      this.modalRef.dismiss();
    };

    if (tipo.id) {
      this.tipoService.atualizar(tipo.id, tipo).subscribe(onComplete);
    } else {
      this.tipoService.cadastrar(tipo).subscribe(onComplete);
    }
  }
  
  lancamento(tipo: Tipo) {
    this.retorno.emit(tipo);
  }

  sortByIdDirection: 'asc' | 'desc' = 'asc';
  sortByNameTipoDirection: 'asc' | 'desc' = 'asc';
  sortStatusDirection: 'asc' | 'desc' = 'asc';
  sortRegistroDirection: 'asc' | 'desc' = 'asc';
  sortAtualizarDirection: 'asc' | 'desc' = 'asc';

  sortById() {
    this.lista.sort((a, b) => {
      if (this.sortByIdDirection === 'asc') {
        return a.id - b.id;
      } else {
        return b.id - a.id;
      }
    });
    this.sortByIdDirection = this.sortByIdDirection === 'asc' ? 'desc' : 'asc';
  }

  sortByNameTipo() {
    this.lista.sort((a, b) => {
      if (this.sortByNameTipoDirection === 'asc') {
        return a.nameTipo.localeCompare(b.nameTipo);
      } else {
        return b.nameTipo.localeCompare(a.nameTipo);
      }
    });
    this.sortByNameTipoDirection = this.sortByNameTipoDirection === 'asc' ? 'desc' : 'asc';
  }
  
  sortStatus() {
    this.lista.sort((a, b) => {
      if (this.sortStatusDirection === 'asc') {
        return a.ativo === b.ativo ? 0 : a.ativo ? -1 : 1;
      } else {
        return a.ativo === b.ativo ? 0 : a.ativo ? 1 : -1;
      }
    });
    this.sortStatusDirection = this.sortStatusDirection === 'asc' ? 'desc' : 'asc';
  }

  formatData(data: string): string {
    const formattedDate = this.datePipe.transform(data, 'dd/MM/yyyy HH:mm:ss');
    return formattedDate || data;
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

  // Declaração da variável para o filtro
  filtro: string = '';

  // Função para aplicar o filtro
  applyFilter() {
    this.lista = this.lista.filter((tipo) => {
      // Personalize aqui como deseja aplicar o filtro (neste exemplo, o filtro é aplicado ao nome do produto)
      return tipo.nameTipo.toLowerCase().includes(this.filtro.toLowerCase());
    });
  }

  // Função para limpar o filtro
  clearFilter() {
    this.filtro = '';
    this.listAll();
  }

  applyOrClearFilter() {
    if (this.filtro) {
      this.applyFilter();
    } else {
      this.clearFilter();
    }
  }

}