import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TipodetailsComponent } from './tipodetails.component';

describe('TipodetailsComponent', () => {
  let component: TipodetailsComponent;
  let fixture: ComponentFixture<TipodetailsComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [TipodetailsComponent]
    });
    fixture = TestBed.createComponent(TipodetailsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
