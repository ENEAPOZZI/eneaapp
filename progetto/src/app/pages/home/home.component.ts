import { Component } from '@angular/core';
import { Ievento } from '../../models/Ievento';
import { EventiService } from '../../eventi.service';
import { AuthService } from '../../auth/auth.service';
import { Iuser } from '../../models/iuser';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrl: './home.component.scss'
})
export class HomeComponent {


  eventi: Ievento[] = [];
  user:Iuser|undefined;

  constructor(private eventisvc: EventiService, private authSvc: AuthService) { }

  ngOnInit(): void {
    this.loadProducts();
    this.authSvc.user$.subscribe(user => {
      this.user = user || undefined;
      console.log('Utente attuale nel componente Home:', user);

    });
  }


  loadProducts(){
    this.eventisvc.event$.subscribe(eventi => {
      this.eventi = eventi
    })
  }

}
