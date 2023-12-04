import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HeaderComponent } from './header.component';
import { DebugElement } from '@angular/core';
import { By } from '@angular/platform-browser';

describe('HeaderComponent', () => {
  let component: HeaderComponent;
  let fixture: ComponentFixture<HeaderComponent>;
  let debugElement: DebugElement;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [HeaderComponent]
    });
    fixture = TestBed.createComponent(HeaderComponent);
    component = fixture.componentInstance;
    debugElement = fixture.debugElement;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should contain brand name "ElevenPy"', () => {
    const brandElement = debugElement.query(By.css('.btn'));
    expect(brandElement.nativeElement.textContent.trim()).toBe('ElevenPy');
  });

  it('should contain navigation links', () => {
    const navItemElements = debugElement.queryAll(By.css('.nav-item'));
    const expectedLinks = ['/admin/estoque', '/admin/movimentacao', '/admin/produto', '/admin/tipo', '/login'];

    expect(navItemElements.length).toBe(expectedLinks.length);

    navItemElements.forEach((element, index) => {
      const linkElement = element.query(By.css('.nav-link'));
      expect(linkElement.nativeElement.getAttribute('href')).toBe(expectedLinks[index]);
    });
  });

});
