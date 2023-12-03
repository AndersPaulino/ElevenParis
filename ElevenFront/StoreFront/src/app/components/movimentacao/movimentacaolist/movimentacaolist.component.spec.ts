import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpClientModule } from '@angular/common/http';
import { FormsModule } from '@angular/forms';
import { DatePipe } from '@angular/common';

import { MovimentacaolistComponent } from './movimentacaolist.component';

describe('MovimentacaolistComponent', () => {
  let component: MovimentacaolistComponent;
  let fixture: ComponentFixture<MovimentacaolistComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [MovimentacaolistComponent],
      imports: [FormsModule, HttpClientModule],
      providers: [DatePipe],
    });

    fixture = TestBed.createComponent(MovimentacaolistComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
