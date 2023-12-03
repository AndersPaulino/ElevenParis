import { ComponentFixture, TestBed } from '@angular/core/testing';
import { RouterTestingModule } from '@angular/router/testing';
import { IndexComponent } from './index.component';
import { HeaderComponent } from '../header/header.component';
import { FooterComponent } from '../footer/footer.component';

describe('IndexComponent', () => {
  let component: IndexComponent;
  let fixture: ComponentFixture<IndexComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [IndexComponent, HeaderComponent, FooterComponent],
      imports: [RouterTestingModule]
    });
    fixture = TestBed.createComponent(IndexComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should contain app-header, router-outlet, and app-footer', () => {
    const compiled = fixture.nativeElement;
    const header = compiled.querySelector('app-header');
    const routerOutlet = compiled.querySelector('router-outlet');
    const footer = compiled.querySelector('app-footer');

    expect(header).toBeTruthy();
    expect(routerOutlet).toBeTruthy();
    expect(footer).toBeTruthy();
  });
});
