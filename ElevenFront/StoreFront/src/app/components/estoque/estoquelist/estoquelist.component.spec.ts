import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EstoquelistComponent } from './estoquelist.component';

describe('EstoquelistComponent', () => {
  let component: EstoquelistComponent;
  let fixture: ComponentFixture<EstoquelistComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [EstoquelistComponent]
    });
    fixture = TestBed.createComponent(EstoquelistComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
