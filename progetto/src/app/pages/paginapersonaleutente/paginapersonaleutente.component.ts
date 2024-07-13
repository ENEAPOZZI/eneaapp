import { Component } from '@angular/core';
import { Iuser } from '../../models/iuser';
import { AuthService } from '../../auth/auth.service';
import { Observable } from 'rxjs';
import { Ievento } from '../../models/Ievento';
import { EventiService } from '../../eventi.service';
@Component({
  selector: 'app-paginapersonaleutente',
  templateUrl: './paginapersonaleutente.component.html',
  styleUrl: './paginapersonaleutente.component.scss'
})
export class PaginapersonaleutenteComponent {

  currentUser: Iuser | null = null;
  isOrganizer$: Observable<boolean>;
  eventi$: Observable<Ievento[]> | undefined;
  organizedEvents$: Observable<Ievento[]> | undefined;
  userId: number | undefined;
  noOrganizer:boolean = false ;
  yesOrganizer:boolean = false ;


  constructor(private authService: AuthService,private eventService: EventiService) {

    this.isOrganizer$ = this.authService.isOrganizer()

  }


  ngOnInit(): void {

    this.authService.user$.subscribe(user => {

      this.currentUser = user;

      if (this.currentUser) {



        const userId = this.currentUser.id;

        this.eventi$ = this.authService.getUserEvents(this.currentUser.id);

        this.isOrganizer$.subscribe(isOrganizer => {

          if (isOrganizer) {

            this.userId = userId;

            this.yesOrganizer = true;

            if (this.userId !== undefined) {
              this.caricaEventiOrganizzati(this.userId);
            }

          }else {
            this.noOrganizer = true;
          }

        });

      }

    });

  }


  caricaEventiOrganizzati(organizzatoreId: number): void {

    this.organizedEvents$ = this.eventService.getEventiOrganizzati(organizzatoreId);

  }


  eliminaEvento(eventoId: number): void {

    this.eventService.eliminaEvento(eventoId).subscribe(() => {
      if (this.userId !== undefined) {
        this.caricaEventiOrganizzati(this.userId);
      }
    }, error => {
      console.error('Errore durante l\'eliminazione dell\'evento', error);
    });

  }

  cancellaIscrizioneDaEvento(eventoId: number): void {
    if (this.currentUser) {
      const userId = this.currentUser.id;
      this.eventService.cancellaIscrizioneUtenteDaEvento(eventoId, userId).subscribe(() => {
        console.log('Iscrizione cancellata con successo!');
        // Aggiorna l'elenco degli eventi iscritti dell'utente
        if (this.currentUser && this.currentUser.id) {
          this.eventi$ = this.authService.getUserEvents(this.currentUser.id);
        }
      }, error => {
        console.error('Errore durante la cancellazione dell\'iscrizione:', error);
      });
    }
  }
}


