import { HTTP_INTERCEPTORS, HttpErrorResponse, HttpEvent, HttpHandler, HttpInterceptor, HttpRequest } from '@angular/common/http';
import { Injectable, inject } from '@angular/core';
import { Router } from '@angular/router';
import { Observable, catchError, of, throwError } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class HttpRequestInterceptor implements HttpInterceptor {

  router = inject(Router);

  intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {

    let token = localStorage.getItem('token');
    if (token) {
      request = request.clone({
        setHeaders: { Authorization: 'Bearer ' + token }
      });
    }

    return next.handle(request).pipe(catchError(x => this.errorHandler(x)));
  }




  private errorHandler(err: HttpErrorResponse): Observable<any> {
    if (err.status === 401) {
      alert('401 - tratar');
      this.router.navigate(['/login']);
      return of(err.message);
    } else if (err.status === 403) {
      alert('403 - tratar');
      return of(err.message);
    }
    return throwError(() => err);
  }

}

export const httpInterceptorProviders = [
  { provide: HTTP_INTERCEPTORS, useClass: HttpRequestInterceptor, multi: true },
];