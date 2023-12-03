import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TipoListComponent } from './tipolist.component';

describe('TipolistComponent', () => {
  let component: TipoListComponent;
  let fixture: ComponentFixture<TipoListComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [TipoListComponent]
    });
    fixture = TestBed.createComponent(TipoListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
