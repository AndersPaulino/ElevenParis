import { inject } from '@angular/core';
import { CanActivateFn, Router } from '@angular/router';
import { LoginService } from '../services/login/login.service'; 

export const rotaguardGuard: CanActivateFn = (route, state) => {

  let loginService = inject(LoginService);
  let roteador = inject(Router);

  if (loginService.getToken() == null) {
    roteador.navigate(['/login']);
    return false;
  } else {
    return true;
  }
};