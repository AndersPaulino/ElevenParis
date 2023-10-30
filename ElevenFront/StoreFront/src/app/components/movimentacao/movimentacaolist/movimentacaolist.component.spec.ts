import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MovimentacaolistComponent } from './movimentacaolist.component';

describe('MovimentacaolistComponent', () => {
  let component: MovimentacaolistComponent;
  let fixture: ComponentFixture<MovimentacaolistComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [MovimentacaolistComponent]
    });
    fixture = TestBed.createComponent(MovimentacaolistComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
