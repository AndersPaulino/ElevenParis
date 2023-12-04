import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EstoquedetailsComponent } from './estoquedetails.component';
import { FormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';

describe('EstoquedetailsComponent', () => {
  let component: EstoquedetailsComponent;
  let fixture: ComponentFixture<EstoquedetailsComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [EstoquedetailsComponent],
      imports: [FormsModule,HttpClientModule],
    });
    fixture = TestBed.createComponent(EstoquedetailsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
