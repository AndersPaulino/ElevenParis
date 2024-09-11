
import { Component, inject } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from 'src/app/services/auth/authservice';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.scss']
})
export class HeaderComponent {
  router = inject(Router);
  loginService = inject(AuthService)

  deslogar() {
    this.loginService.logout(); // Chama o método de logout
    this.router.navigate(['/login']); // Redireciona para a página de login
  }
  
  
  isRouteActive(route: string): string {
    return this.router.url === route ? 'active' : '';
  }
}

