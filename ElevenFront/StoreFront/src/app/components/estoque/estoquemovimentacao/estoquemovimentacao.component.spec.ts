import { ComponentFixture, TestBed } from '@angular/core/testing';
import { EstoquemovimentacaoComponent } from './estoquemovimentacao.component';
import { HttpClientModule } from '@angular/common/http';
import { FormsModule } from '@angular/forms';

describe('EstoquemovimentacaoComponent', () => {
  let component: EstoquemovimentacaoComponent;
  let fixture: ComponentFixture<EstoquemovimentacaoComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [EstoquemovimentacaoComponent],
      imports: [FormsModule,HttpClientModule]
    });
    fixture = TestBed.createComponent(EstoquemovimentacaoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
