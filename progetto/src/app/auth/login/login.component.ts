import { Component } from '@angular/core';
import { AuthService } from '../auth.service';
import { Router } from '@angular/router';
import { ILoginData } from '../../models/i-login-data';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrl: './login.component.scss'
})
export class LoginComponent {


  loginData: ILoginData = {
    username:'',
    password: ''
  };

  errorMessage: string = '';

  constructor(
    private authSvc: AuthService,
    private router: Router
  ) {}

  signIn() {
    if (!this.loginData.username || !this.loginData.password) {
      this.errorMessage = 'Inserisci email e password.';
      return;
    }

    this.authSvc.login(this.loginData)
      .subscribe( data => {
          console.log('dati di data', data )

          this.router.navigate(['/home']);
        },

      );
  }
}

