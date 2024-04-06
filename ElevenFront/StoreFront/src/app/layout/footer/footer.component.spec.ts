import { ComponentFixture, TestBed } from '@angular/core/testing';
import { FooterComponent } from './footer.component';
import { DebugElement } from '@angular/core';
import { By } from '@angular/platform-browser';

describe('FooterComponent', () => {
  let component: FooterComponent;
  let fixture: ComponentFixture<FooterComponent>;
  let debugElement: DebugElement;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [FooterComponent]
    });
    fixture = TestBed.createComponent(FooterComponent);
    component = fixture.componentInstance;
    debugElement = fixture.debugElement;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should contain company name "Eleven"', () => {
    const companyNameElement = debugElement.query(By.css('.company-name'));
    expect(companyNameElement.nativeElement.textContent).toContain('Eleven');
  });

  it('should contain address information', () => {
    const addressElement = debugElement.query(By.css('.address'));
    const addressText = 'Endereço: Rua da Empresa, Cidade, Estado, CEP';
    expect(addressElement.nativeElement.textContent).toContain(addressText);
  });

  it('should contain developer names', () => {
    const developerElements = debugElement.queryAll(By.css('.developers'));
    const expectedDevelopers = ['Anderso Paulino', 'Matheus Aguiar', 'Matheus Krohn', 'Lucas Gimenez'];

    expect(developerElements.length).toBe(expectedDevelopers.length);

    developerElements.forEach((element, index) => {
      expect(element.nativeElement.textContent).toContain(expectedDevelopers[index]);
    });
  });
});
