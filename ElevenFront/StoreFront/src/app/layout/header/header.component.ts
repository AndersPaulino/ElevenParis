
import { Component, inject } from '@angular/core';
import { Router } from '@angular/router';
import { LoginService } from 'src/app/services/login/login.service';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.scss']
})
export class HeaderComponent {
  router = inject(Router);
  loginService = inject(LoginService)

  deslogar(){
    this.router.navigate(['/login']);
  }
  
  isRouteActive(route: string): string {
    return this.router.url === route ? 'active' : '';
  }
}

