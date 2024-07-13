import { Component } from '@angular/core';
import { Ievento } from '../../models/Ievento';
import { ActivatedRoute, Router } from '@angular/router';
import { EventiService } from '../../eventi.service';
import { Iuser } from '../../models/iuser';
import { AuthService } from '../../auth/auth.service';

@Component({
  selector: 'app-dettaglioevento',
  templateUrl: './dettaglioevento.component.html',
  styleUrl: './dettaglioevento.component.scss'
})
export class DettaglioeventoComponent {

  evento: Ievento | undefined;
  currentUser: Iuser   | null = null;
  showSuccessMessage = false;
  showErrorMessage = false;


  constructor(
    private route: ActivatedRoute,
    private eventoSvc: EventiService,
    private router: Router,
    private authService: AuthService
  ) { }

  ngOnInit(): void {
    this.getEvent();

    this.authService.user$.subscribe(user => {
      this.currentUser = user;
      console.log('Current User:', this.currentUser);
    });

  }


  getEvent(): void {
    const id = +this.route.snapshot.paramMap.get('id')!;
    this.eventoSvc.getEventtById(id).subscribe(evento => {
    this.evento = evento;
    console.log(this.evento);
  });
  }

  iscriviUtente(idEvento: number, idUtente: number): void {
    this.eventoSvc.iscriviUtenteAllEvento(idEvento, idUtente)
      .subscribe({
        next: () => {
          console.log('Iscrizione completata con successo!');
          this.showSuccessAlert();
        },
        error: (error) => {
          console.error('Errore durante l\'iscrizione:', error);
          this.showErrorAlert();
        }
      });
  }

  showSuccessAlert(): void {
    this.showSuccessMessage = true;
    setTimeout(() => {
      this.showSuccessMessage = false;
    }, 3000);
  }

  showErrorAlert(): void {
    this.showErrorMessage = true;
    setTimeout(() => {
      this.showErrorMessage = false;
    }, 3000);
  }

  closeAlert(): void {
    this.showSuccessMessage = false;
    this.showErrorMessage = false;
  }

}
