import { Component } from '@angular/core';
import { EventiService } from '../../eventi.service';
import { Observable } from 'rxjs';
import { Ievento } from '../../models/Ievento';
import { Iuser } from '../../models/iuser';
import { AuthService } from '../../auth/auth.service';

@Component({
  selector: 'app-eventi',
  templateUrl: './eventi.component.html',
  styleUrl: './eventi.component.scss'
})
export class EventiComponent {


  eventi$: Observable<Ievento[]>= new Observable<Ievento[]>();


  currentUser: Iuser | null = null;

  successMessage: boolean | null = null;
  errorMessage: boolean | null = null;

  constructor(private eventiService: EventiService, private authService: AuthService) { }

  ngOnInit(): void {
    this.authService.user$.subscribe(user => {
      this.currentUser = user;
      console.log('Utente corrente:', this.currentUser);
    });
    this.caricaEventi();
  }

  caricaEventi(): void {
    this.eventi$ = this.eventiService.getAllEvent();
  }



  iscriviUtenteAllEvento(eventoId: number) {
    if (this.currentUser) {
      const idUtente = this.currentUser.id;
      this.eventiService.iscriviUtenteAllEvento(eventoId, idUtente).subscribe(
        () => {
          console.log(`L'utente con id ${idUtente} è stato iscritto all'evento con id ${eventoId}`);
          this.showSuccessAlert()
        },
        error => {
          console.error('Errore durante l\'iscrizione:', error);

          this.showErrorAlert()

        }
      );
    }
  }

  showSuccessAlert(): void {
    this.successMessage = true;
    setTimeout(() => {
      this.successMessage = false;
    }, 3000);
  }

  showErrorAlert(): void {
    this.errorMessage = true;
    setTimeout(() => {
      this.errorMessage = false;
    }, 3000);
  }

  closeAlert(): void {
    this.successMessage = false;
    this.errorMessage = false;
  }


}
