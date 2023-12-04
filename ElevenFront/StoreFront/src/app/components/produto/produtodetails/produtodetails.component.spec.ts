import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';
import { ProdutodetailsComponent } from './produtodetails.component';
import { NgbModal, NgbModalRef, NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { FormsModule } from '@angular/forms';
import { ProdutoService } from 'src/app/services/produto.service';

describe('ProdutodetailsComponent', () => {
  let component: ProdutodetailsComponent;
  let fixture: ComponentFixture<ProdutodetailsComponent>;
  let mockModalService: jasmine.SpyObj<NgbModal>;
  let mockProdutoService: jasmine.SpyObj<ProdutoService>;
  let mockModalRef: jasmine.SpyObj<NgbModalRef>;

  beforeEach(() => {
    mockModalService = jasmine.createSpyObj('NgbModal', ['open']);
    mockProdutoService = jasmine.createSpyObj('ProdutoService', ['']);
    mockModalRef = jasmine.createSpyObj('NgbModalRef', ['dismiss', 'close']);
    
    TestBed.configureTestingModule({
      declarations: [ProdutodetailsComponent],
      imports: [NgbModule, FormsModule],
      providers: [
        { provide: NgbModal, useValue: mockModalService },
        { provide: ProdutoService, useValue: mockProdutoService }
      ]
    }).compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ProdutodetailsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should emit produto on salvar', () => {
    spyOn(component.retorno, 'emit');
    component.salvar();
    expect(component.retorno.emit).toHaveBeenCalledWith(component.produto);
  });

  it('should set produto ativo to false on desativar', () => {
    component.produto.ativo = true;
    component.desativar();
    expect(component.produto.ativo).toBeFalse();
  });

  it('should set produto ativo to true on ativar', () => {
    component.produto.ativo = false;
    component.ativar();
    expect(component.produto.ativo).toBeTrue();
  });
});
