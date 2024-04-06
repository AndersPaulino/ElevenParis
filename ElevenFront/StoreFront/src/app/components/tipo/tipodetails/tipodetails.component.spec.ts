import { ComponentFixture, TestBed } from '@angular/core/testing';
import { TipodetailsComponent } from './tipodetails.component';
import { Tipo } from 'src/app/models/tipo';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { NO_ERRORS_SCHEMA } from '@angular/core';

describe('TipodetailsComponent', () => {
  let component: TipodetailsComponent;
  let fixture: ComponentFixture<TipodetailsComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [TipodetailsComponent],
      imports: [HttpClientTestingModule],
      schemas: [NO_ERRORS_SCHEMA]
    });
    fixture = TestBed.createComponent(TipodetailsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should emit tipo on salvar()', () => {
    const tipo = new Tipo();
    spyOn(component.retorno, 'emit');
    
    component.tipo = tipo;
    component.salvar();
    
    expect(component.retorno.emit).toHaveBeenCalledWith(tipo);
  });

  it('should log tipo on salvar()', () => {
    const tipo = new Tipo();
    spyOn(console, 'log');
    
    component.tipo = tipo;
    component.salvar();
    
    expect(console.log).toHaveBeenCalledWith(tipo);
  });

  it('should set tipo.ativo to false on desativar()', () => {
    const tipo = new Tipo();
    component.tipo = tipo;
    
    component.desativar();
    
    expect(component.tipo.ativo).toBeFalse();
  });

  it('should set tipo.ativo to true on ativar()', () => {
    const tipo = new Tipo();
    component.tipo = tipo;
    
    component.ativar();
    
    expect(component.tipo.ativo).toBeTrue();
  });

  it('should have tipo as input', () => {
    const tipo = new Tipo();
    component.tipo = tipo;
    
    expect(component.tipo).toEqual(tipo);
  });
});
