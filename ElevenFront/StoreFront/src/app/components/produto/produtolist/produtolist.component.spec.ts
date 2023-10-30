import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ProdutolistComponent } from './produtolist.component';

describe('ProdutolistComponent', () => {
  let component: ProdutolistComponent;
  let fixture: ComponentFixture<ProdutolistComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ProdutolistComponent]
    });
    fixture = TestBed.createComponent(ProdutolistComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
