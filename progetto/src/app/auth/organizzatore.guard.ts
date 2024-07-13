import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, CanActivate, CanActivateChild, GuardResult, MaybeAsync, Router, RouterStateSnapshot, UrlTree } from '@angular/router';
import { AuthService } from './auth.service';
import { Observable, map } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class OrganizzatoreGuard  {



  constructor(private authSvc: AuthService, private router: Router) {}

  canActivate(): Observable<boolean | UrlTree> {

      return this.authSvc.isOrganizer().pipe(
        map(isOrganizer => {
          if (isOrganizer) {
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
