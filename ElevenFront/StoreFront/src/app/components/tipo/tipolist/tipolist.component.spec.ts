import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TipolistComponent } from './tipolist.component';

describe('TipolistComponent', () => {
  let component: TipolistComponent;
  let fixture: ComponentFixture<TipolistComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [TipolistComponent]
    });
    fixture = TestBed.createComponent(TipolistComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
