import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';
import { FormsModule } from '@angular/forms';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { Movimentacao } from 'src/app/models/movimentacao.model';
import { MovimentacaoService } from 'src/app/services/movimentacao.service';
import { MovimentacaodetailsComponent } from './movimentacaodetails.component';
import { Produto } from 'src/app/models/produto';
import { HttpClientModule } from '@angular/common/http';

describe('MovimentacaodetailsComponent', () => {
  let component: MovimentacaodetailsComponent;
  let fixture: ComponentFixture<MovimentacaodetailsComponent>;

  beforeEach(
    waitForAsync(() => {
      TestBed.configureTestingModule({
        declarations: [MovimentacaodetailsComponent],
        imports: [FormsModule,HttpClientModule],
        providers: [NgbModal, MovimentacaoService],
      }).compileComponents();
    })
  );

  beforeEach(() => {
    fixture = TestBed.createComponent(MovimentacaodetailsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should open modal on lancar', () => {
    spyOn(component.modalService, 'open').and.callThrough();
    component.lancar('modal3');
    expect(component.modalService.open).toHaveBeenCalledWith('modal3', { size: 'lg' });
  });

  it('should emit movimentacao on salvar', () => {
    spyOn(component.retorno, 'emit');
    component.salvar();
    expect(component.retorno.emit).toHaveBeenCalledWith(component.movimentacao);
  });

  it('should set produtos on retornoProdutoList and dismiss modal', () => {
    const mockModalRef: NgbModalRef = jasmine.createSpyObj('NgbModalRef', ['dismiss']);
  
    spyOn(component, 'retornoProdutoList').and.callThrough();
    spyOn(component.modalService, 'open').and.returnValue(mockModalRef);
  
    const produtos: Produto = {
      id: 1,
      nome: 'Teste',
      tipo: { id: 1, ativo: true, registro: '2023-01-01T00:00:00', atualizar: '2023-01-01T00:00:00', nameTipo: 'Tipo1', filtro: '' },
      ativo: true,
      registro: '2023-01-01T00:00:00',
      atualizar: '2023-01-01T00:00:00',
      descricao: 'Descrição',
      filtro: 'Teste1'
    };
  
    component.lancar('modal3');
    component.retornoProdutoList(produtos);
  
    expect(component.movimentacao.produtos).toEqual(produtos);
    expect(mockModalRef.dismiss).toHaveBeenCalled();
    expect(component.retornoProdutoList).toHaveBeenCalled();
  });
  

});