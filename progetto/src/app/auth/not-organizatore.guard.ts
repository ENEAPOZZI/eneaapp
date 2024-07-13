import { Injectable } from '@angular/core';
import { Observable, map } from 'rxjs';
import { AuthService } from './auth.service';
import { Router, UrlTree } from '@angular/router';

@Injectable({
  providedIn: 'root'
})



export class NotOrganizatoreGuard  {

  constructor(private authSvc: AuthService, private router: Router) {}

  canActivate(): Observable<boolean | UrlTree> {
    return this.authSvc.isOrganizer().pipe(
      map(isOrganizer => {
        if (!isOrganizer) {
          return true;
        } else {
          return this.router.createUrlTree(['/home']);
        }
      })
    );

  }
  canActivateChild(): Observable<boolean | UrlTree> {
    return this.canActivate();
  }

}
