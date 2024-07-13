import { Component } from '@angular/core';
import { AuthService } from '../auth.service';
import { Router } from '@angular/router';
import { Iuser } from '../../models/iuser';
import { IuserRegistration } from '../../models/iuser-registration';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrl: './register.component.scss'
})
export class RegisterComponent {


  registerData: Partial<IuserRegistration> =  {};


  constructor(
    private authSvc: AuthService,
    private router: Router
  ) { }

  signUp(): void {
       const data: IuserRegistration = {
      email: this.registerData.email || '',
      username:this.registerData.username || '',
      password:this.registerData.password || '',
      nome:this.registerData.nome || '',
      cognome:this.registerData.cognome || '',
      ruolo:  this.registerData.ruolo || '',
    };

    console.log(data)
    this.authSvc.register(data).subscribe(() => {

    });
  }


}





