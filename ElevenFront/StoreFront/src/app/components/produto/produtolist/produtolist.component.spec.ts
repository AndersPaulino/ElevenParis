import { ComponentFixture, TestBed } from '@angular/core/testing';
import { FormsModule } from '@angular/forms';
import { DatePipe } from '@angular/common';
import { ProdutolistComponent } from './produtolist.component';
import { of } from 'rxjs';
import { Produto } from 'src/app/models/produto';
import { HttpClientTestingModule } from '@angular/common/http/testing';

describe('ProdutolistComponent', () => {
  let component: ProdutolistComponent;
  let fixture: ComponentFixture<ProdutolistComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ProdutolistComponent],
      imports: [FormsModule, HttpClientTestingModule],
      providers: [DatePipe]
    });
    fixture = TestBed.createComponent(ProdutolistComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should initialize with an empty product list', () => {
    expect(component.lista).toEqual([]);
  });

  it('should call listAll', () => {
    spyOn(component, 'listAll');
    component.listAll();
    expect(component.listAll).toHaveBeenCalled();
  });

  it('should open modal on adicionar', () => {
    spyOn(component.modalService, 'open').and.callThrough();
    component.adicionar('modal');
    expect(component.modalService.open).toHaveBeenCalledWith('modal', { size: 'sm' });
  });

  it('should open modal on editar', () => {
    spyOn(component.modalService, 'open').and.callThrough();
    const produto = {
      id: 1,
      nome: 'Teste',
      tipo: { id: 1, ativo: true, registro: '2023-01-01T00:00:00', atualizar: '2023-01-01T00:00:00', nameTipo: 'Tipo1', filtro: '' },
      ativo: true,
      registro: '2023-01-01T00:00:00',
      atualizar: '2023-01-01T00:00:00',
      descricao: 'Descrição',
      filtro: ''
    };
    component.editar('modal', produto, 0);
    expect(component.modalService.open).toHaveBeenCalledWith('modal', { size: 'sm' });
    expect(component.produtoSelecionadoParaEdicao).toEqual({
      id: 1,
      nome: 'Teste',
      tipo: { id: 1, ativo: true, registro: '2023-01-01T00:00:00', atualizar: '2023-01-01T00:00:00', nameTipo: 'Tipo1', filtro: '' },
      ativo: true,
      registro: '2023-01-01T00:00:00',
      atualizar: '2023-01-01T00:00:00',
      descricao: 'Descrição',
      filtro: ''
    });
    expect(component.indiceSelecionadoParaEdicao).toBe(0);
  });

  it('should call delete service method when deleting product', () => {
    const deleteMessage = 'Registro desativado com sucesso!';
    spyOn(component.produtoService, 'deletar').and.returnValue(of(deleteMessage));

    const produto = {
      id: 1,
      nome: 'Teste',
      tipo: { id: 1, ativo: true, registro: '2023-01-01T00:00:00', atualizar: '2023-01-01T00:00:00', nameTipo: 'Tipo1', filtro: '' },
      ativo: true,
      registro: '2023-01-01T00:00:00',
      atualizar: '2023-01-01T00:00:00',
      descricao: 'Descrição',
      filtro: ''
    };

    component.deletar(produto.id);
    expect(component.produtoService.deletar).toHaveBeenCalledWith(produto.id);
  });

  it('should emit product on lancamento', () => {
    spyOn(component.retorno, 'emit');
    const produto = {
      id: 1,
      nome: 'Teste',
      tipo: { id: 1, ativo: true, registro: '2023-01-01T00:00:00', atualizar: '2023-01-01T00:00:00', nameTipo: 'Tipo1', filtro: '' },
      ativo: true,
      registro: '2023-01-01T00:00:00',
      atualizar: '2023-01-01T00:00:00',
      descricao: 'Descrição',
      filtro: ''
    };
    component.lancamento(produto);
    expect(component.retorno.emit).toHaveBeenCalledWith(produto);
  });

  it('should sort by ID', () => {
    const produtos: Produto[] = [
      {
        id: 1,
        nome: 'Teste',
        tipo: { id: 1, ativo: true, registro: '2023-01-01T00:00:00', atualizar: '2023-01-01T00:00:00', nameTipo: 'Tipo1', filtro: '' },
        ativo: true,
        registro: '2023-01-01T00:00:00',
        atualizar: '2023-01-01T00:00:00',
        descricao: 'Descrição',
        filtro: ''
      }
    ];

    component.lista = produtos;
    component.sortById();
    expect(component.lista).toEqual([
      {
        id: 1,
        nome: 'Teste',
        tipo: { id: 1, ativo: true, registro: '2023-01-01T00:00:00', atualizar: '2023-01-01T00:00:00', nameTipo: 'Tipo1', filtro: '' },
        ativo: true,
        registro: '2023-01-01T00:00:00',
        atualizar: '2023-01-01T00:00:00',
        descricao: 'Descrição',
        filtro: ''
      }
    ]);
  });

  it('should sort by Name', () => {
    const produtos: Produto[] = [{
      id: 1,
      nome: 'Teste',
      tipo: { id: 1, ativo: true, registro: '2023-01-01T00:00:00', atualizar: '2023-01-01T00:00:00', nameTipo: 'Tipo1', filtro: '' },
      ativo: true,
      registro: '2023-01-01T00:00:00',
      atualizar: '2023-01-01T00:00:00',
      descricao: 'Descrição',
      filtro: ''
    }];
    component.lista = produtos;
    component.sortName();
    expect(component.lista).toEqual([{
      id: 1,
      nome: 'Teste',
      tipo: { id: 1, ativo: true, registro: '2023-01-01T00:00:00', atualizar: '2023-01-01T00:00:00', nameTipo: 'Tipo1', filtro: '' },
      ativo: true,
      registro: '2023-01-01T00:00:00',
      atualizar: '2023-01-01T00:00:00',
      descricao: 'Descrição',
      filtro: ''
    }]);
  });

  it('should sort by NameTipo', () => {
    const produtos: Produto[] = [
      {
        id: 1,
        nome: 'Teste',
        tipo: { id: 1, ativo: true, registro: '2023-01-01T00:00:00', atualizar: '2023-01-01T00:00:00', nameTipo: 'Tipo1', filtro: '' },
        ativo: true,
        registro: '2023-01-01T00:00:00',
        atualizar: '2023-01-01T00:00:00',
        descricao: 'Descrição',
        filtro: ''
      }
    ];
    component.lista = produtos;
    component.sortNameTipo();
    expect(component.lista).toEqual([
      {
        id: 1,
        nome: 'Teste',
        tipo: { id: 1, ativo: true, registro: '2023-01-01T00:00:00', atualizar: '2023-01-01T00:00:00', nameTipo: 'Tipo1', filtro: '' },
        ativo: true,
        registro: '2023-01-01T00:00:00',
        atualizar: '2023-01-01T00:00:00',
        descricao: 'Descrição',
        filtro: ''
      }
    ]);
  });

  it('should sort by Status', () => {
    const produtos: Produto[] = [{
      id: 1,
      nome: 'Teste',
      tipo: { id: 1, ativo: true, registro: '2023-01-01T00:00:00', atualizar: '2023-01-01T00:00:00', nameTipo: 'Tipo1', filtro: '' },
      ativo: true,
      registro: '2023-01-01T00:00:00',
      atualizar: '2023-01-01T00:00:00',
      descricao: 'Descrição',
      filtro: ''
    }];
    component.lista = produtos;
    component.sortStatus();
    expect(component.lista).toEqual([{
      id: 1,
      nome: 'Teste',
      tipo: { id: 1, ativo: true, registro: '2023-01-01T00:00:00', atualizar: '2023-01-01T00:00:00', nameTipo: 'Tipo1', filtro: '' },
      ativo: true,
      registro: '2023-01-01T00:00:00',
      atualizar: '2023-01-01T00:00:00',
      descricao: 'Descrição',
      filtro: ''
    }]);
  });

  it('should sort by Registro', () => {
    const produtos: Produto[] = [
      {
        id: 1,
        nome: 'Teste',
        tipo: { id: 1, ativo: true, registro: '2023-01-01T00:00:00', atualizar: '2023-01-01T00:00:00', nameTipo: 'Tipo1', filtro: '' },
        ativo: true,
        registro: '2023-01-01T00:00:00',
        atualizar: '2023-01-01T00:00:00',
        descricao: 'Descrição',
        filtro: ''
      },
      {
        id: 2,
        nome: 'Teste2',
        tipo: { id: 2, ativo: true, registro: '2023-01-01T00:00:00', atualizar: '2023-01-01T00:00:00', nameTipo: 'Tipo2', filtro: '' },
        ativo: true,
        registro: '2023-01-01T00:00:00',
        atualizar: '2023-01-01T00:00:00',
        descricao: 'Descrição2',
        filtro: ''
      }
    ];
    component.lista = produtos;
    component.sortRegistro();
    expect(component.lista).toEqual([
      {
        id: 1,
        nome: 'Teste',
        tipo: { id: 1, ativo: true, registro: '2023-01-01T00:00:00', atualizar: '2023-01-01T00:00:00', nameTipo: 'Tipo1', filtro: '' },
        ativo: true,
        registro: '2023-01-01T00:00:00',
        atualizar: '2023-01-01T00:00:00',
        descricao: 'Descrição',
        filtro: ''
      },
      {
        id: 2,
        nome: 'Teste2',
        tipo: { id: 2, ativo: true, registro: '2023-01-01T00:00:00', atualizar: '2023-01-01T00:00:00', nameTipo: 'Tipo2', filtro: '' },
        ativo: true,
        registro: '2023-01-01T00:00:00',
        atualizar: '2023-01-01T00:00:00',
        descricao: 'Descrição2',
        filtro: ''
      }
    ]);
  });

  it('should sort by Atualizar', () => {
    const produtos: Produto[] = [
      {
        id: 1,
        nome: 'Teste',
        tipo: { id: 1, ativo: true, registro: '2023-01-01T00:00:00', atualizar: '2023-01-01T00:00:00', nameTipo: 'Tipo1', filtro: '' },
        ativo: true,
        registro: '2023-01-01T00:00:00',
        atualizar: '2023-01-01T00:00:00',
        descricao: 'Descrição',
        filtro: ''
      },
      {
        id: 2,
        nome: 'Teste2',
        tipo: { id: 2, ativo: true, registro: '2023-01-01T00:00:00', atualizar: '2023-01-01T00:00:00', nameTipo: 'Tipo2', filtro: '' },
        ativo: true,
        registro: '2023-01-01T00:00:00',
        atualizar: '2023-01-01T00:00:00',
        descricao: 'Descrição2',
        filtro: ''
      }
    ];
    component.lista = produtos;
    component.sortAtualizar();
    expect(component.lista).toEqual([
      {
        id: 1,
        nome: 'Teste',
        tipo: { id: 1, ativo: true, registro: '2023-01-01T00:00:00', atualizar: '2023-01-01T00:00:00', nameTipo: 'Tipo1', filtro: '' },
        ativo: true,
        registro: '2023-01-01T00:00:00',
        atualizar: '2023-01-01T00:00:00',
        descricao: 'Descrição',
        filtro: ''
      },
      {
        id: 2,
        nome: 'Teste2',
        tipo: { id: 2, ativo: true, registro: '2023-01-01T00:00:00', atualizar: '2023-01-01T00:00:00', nameTipo: 'Tipo2', filtro: '' },
        ativo: true,
        registro: '2023-01-01T00:00:00',
        atualizar: '2023-01-01T00:00:00',
        descricao: 'Descrição2',
        filtro: ''
      }
    ]);
  });

  it('should sort by Descricao', () => {
    const produtos: Produto[] = [{
      id: 1,
      nome: 'Teste',
      tipo: { id: 1, ativo: true, registro: '2023-01-01T00:00:00', atualizar: '2023-01-01T00:00:00', nameTipo: 'Tipo1', filtro: '' },
      ativo: true,
      registro: '2023-01-01T00:00:00',
      atualizar: '2023-01-01T00:00:00',
      descricao: 'Descrição',
      filtro: ''
    }
    ];
    component.lista = produtos;
    component.sortDescricao();
    expect(component.lista).toEqual([{
      id: 1,
      nome: 'Teste',
      tipo: { id: 1, ativo: true, registro: '2023-01-01T00:00:00', atualizar: '2023-01-01T00:00:00', nameTipo: 'Tipo1', filtro: '' },
      ativo: true,
      registro: '2023-01-01T00:00:00',
      atualizar: '2023-01-01T00:00:00',
      descricao: 'Descrição',
      filtro: ''
    }]);
  });

  it('should apply filter', () => {
    const produtos: Produto[] = [{
      id: 1,
      nome: 'Teste',
      tipo: { id: 1, ativo: true, registro: '2023-01-01T00:00:00', atualizar: '2023-01-01T00:00:00', nameTipo: 'Tipo1', filtro: '' },
      ativo: true,
      registro: '2023-01-01T00:00:00',
      atualizar: '2023-01-01T00:00:00',
      descricao: 'Descrição',
      filtro: 'Teste1'
    }];
    component.lista = produtos;
    component.filtro = 'Teste1';
    
    fixture.detectChanges();

    setTimeout(() => {
      expect(component.lista).toEqual([{
        id: 1,
        nome: 'Teste',
        tipo: { id: 1, ativo: true, registro: '2023-01-01T00:00:00', atualizar: '2023-01-01T00:00:00', nameTipo: 'Tipo1', filtro: '' },
        ativo: true,
        registro: '2023-01-01T00:00:00',
        atualizar: '2023-01-01T00:00:00',
        descricao: 'Descrição',
        filtro: 'Teste1'
      }]);
    }, 1000);
  });
  

  it('should clear filter', () => {
    const produtos: Produto[] = [{
      id: 1,
      nome: 'Teste',
      tipo: { id: 1, ativo: true, registro: '2023-01-01T00:00:00', atualizar: '2023-01-01T00:00:00', nameTipo: 'Tipo1', filtro: '' },
      ativo: true,
      registro: '2023-01-01T00:00:00',
      atualizar: '2023-01-01T00:00:00',
      descricao: 'Descrição',
      filtro: ''
    },
    {
      id: 2,
      nome: 'Teste2',
      tipo: { id: 2, ativo: true, registro: '2023-01-01T00:00:00', atualizar: '2023-01-01T00:00:00', nameTipo: 'Tipo2', filtro: '' },
      ativo: true,
      registro: '2023-01-01T00:00:00',
      atualizar: '2023-01-01T00:00:00',
      descricao: 'Descrição2',
      filtro: ''
    }];
    component.lista = produtos;
    component.filtro = 'Teste1';
    component.clearFilter();
    expect(component.filtro).toEqual('');
    expect(component.lista).toEqual([{
      id: 1,
      nome: 'Teste',
      tipo: { id: 1, ativo: true, registro: '2023-01-01T00:00:00', atualizar: '2023-01-01T00:00:00', nameTipo: 'Tipo1', filtro: '' },
      ativo: true,
      registro: '2023-01-01T00:00:00',
      atualizar: '2023-01-01T00:00:00',
      descricao: 'Descrição',
      filtro: ''
    },
    {
      id: 2,
      nome: 'Teste2',
      tipo: { id: 2, ativo: true, registro: '2023-01-01T00:00:00', atualizar: '2023-01-01T00:00:00', nameTipo: 'Tipo2', filtro: '' },
      ativo: true,
      registro: '2023-01-01T00:00:00',
      atualizar: '2023-01-01T00:00:00',
      descricao: 'Descrição2',
      filtro: ''
    }]);
  });

  it('should apply or clear filter', () => {
    spyOn(component, 'applyFilter');
    spyOn(component, 'clearFilter');
    component.filtro = 'Teste';
    component.applyOrClearFilter();
    expect(component.applyFilter).toHaveBeenCalled();
    component.filtro = '';
    component.applyOrClearFilter();
    expect(component.clearFilter).toHaveBeenCalled();
  });

});
