import { Component } from '@angular/core';
import { AuthService } from '../../auth/auth.service';
import { Iuser } from '../../models/iuser';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrl: './navbar.component.scss'
})
export class NavbarComponent {

  isUserLoggedIn:boolean = false;
  isOrganizer:boolean = false

  user: Iuser | null = null;

  constructor(public authSvc: AuthService) {}


  ngOnInit() {
    this.authSvc.isLoggedIn$.subscribe(data => {
      this.isUserLoggedIn = data;
      console.log(data)
    });

    this.authSvc.isOrganizer().subscribe(isOrganizer => {
      this.isOrganizer = isOrganizer;
    });

    this.authSvc.user$.subscribe(user => {
      this.user = user;
      console.log('utente navbar',user)
    });
  }


  logout() {
    this.authSvc.logout();
  }

}
