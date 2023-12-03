import { ComponentFixture, TestBed } from '@angular/core/testing';
import { TipoListComponent } from './tipolist.component';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { TipoService } from 'src/app/services/tipo.service';
import { of } from 'rxjs';
import { DatePipe } from '@angular/common';
import { NO_ERRORS_SCHEMA } from '@angular/core';

describe('TipoListComponent', () => {
  let component: TipoListComponent;
  let fixture: ComponentFixture<TipoListComponent>;
  let mockTipoService: jasmine.SpyObj<TipoService>;
  let mockNgbModal: jasmine.SpyObj<NgbModal>;

  beforeEach(() => {
    mockTipoService = jasmine.createSpyObj('TipoService', ['listAll', 'cadastrar', 'atualizar']);
    mockTipoService.listAll.and.returnValue(of([]));
    mockNgbModal = jasmine.createSpyObj('NgbModal', ['open']);
    
    TestBed.configureTestingModule({
      declarations: [TipoListComponent],
      providers: [
        { provide: NgbModal, useValue: mockNgbModal },
        { provide: TipoService, useValue: mockTipoService },
        DatePipe
      ],
      schemas: [NO_ERRORS_SCHEMA]
    });
    
    fixture = TestBed.createComponent(TipoListComponent);
    component = fixture.componentInstance;
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should call listAll on initialization', () => {
    fixture.detectChanges();
    expect(mockTipoService.listAll).toHaveBeenCalled();
  });

  it('should call listAll on applyFilter', () => {
    spyOn(component, 'applyFilter').and.callThrough();
    fixture.detectChanges();

    component.filtro = 'Test';
    component.applyFilter();

    expect(component.applyFilter).toHaveBeenCalled();
    expect(mockTipoService.listAll).toHaveBeenCalled();
  });

  it('should call listAll on clearFilter', () => {
    spyOn(component, 'clearFilter').and.callThrough();
    fixture.detectChanges();

    component.filtro = 'Test';
    component.clearFilter();

    expect(component.clearFilter).toHaveBeenCalled();
    expect(mockTipoService.listAll).toHaveBeenCalled();
  });

  it('should call applyFilter on applyOrClearFilter when filtro is not empty', () => {
    spyOn(component, 'applyFilter').and.callThrough();
    fixture.detectChanges();

    component.filtro = 'Test';
    component.applyOrClearFilter();

    expect(component.applyFilter).toHaveBeenCalled();
    expect(mockTipoService.listAll).toHaveBeenCalled();
  });

  it('should call clearFilter on applyOrClearFilter when filtro is empty', () => {
    spyOn(component, 'clearFilter').and.callThrough();
    fixture.detectChanges();

    component.filtro = '';
    component.applyOrClearFilter();

    expect(component.clearFilter).toHaveBeenCalled();
    expect(mockTipoService.listAll).toHaveBeenCalled();
  });

});
