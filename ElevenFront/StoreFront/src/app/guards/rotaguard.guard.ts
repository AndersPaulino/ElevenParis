import { Injectable } from '@angular/core';
import { CanActivate, Router } from '@angular/router';
import { AuthService } from '../services/auth/authservice';

@Injectable({
  providedIn: 'root'
})
export class Rotaguard implements CanActivate  {

  constructor(private authService: AuthService, private router: Router) {}

  canActivate(): boolean {
    const token = this.authService.getToken();
    if (!token) {
      this.router.navigate(['/login']);
      return false;
    }
    return true;
  }
  
}
