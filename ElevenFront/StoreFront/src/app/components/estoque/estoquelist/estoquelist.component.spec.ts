import { ComponentFixture, TestBed } from '@angular/core/testing';
import { EstoquelistComponent } from './estoquelist.component';
import { FormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { DatePipe } from '@angular/common';

describe('EstoquelistComponent', () => {
  let component: EstoquelistComponent;
  let fixture: ComponentFixture<EstoquelistComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [EstoquelistComponent],
      imports: [FormsModule,HttpClientModule],
      providers: [DatePipe]
    });
    fixture = TestBed.createComponent(EstoquelistComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
