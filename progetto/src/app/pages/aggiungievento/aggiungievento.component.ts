import { Component } from '@angular/core';
import { EventiService } from '../../eventi.service';
import { Router } from '@angular/router';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { AuthService } from '../../auth/auth.service';

@Component({
  selector: 'app-aggiungievento',
  templateUrl: './aggiungievento.component.html',
  styleUrl: './aggiungievento.component.scss'
})
export class AggiungieventoComponent {



  eventoForm: FormGroup;


  constructor(
    private eventiService: EventiService,
     private formBuilder: FormBuilder,
     private router: Router,
     private authService: AuthService) {
    this.eventoForm = this.formBuilder.group({
      nome: ['', Validators.required],
      descrizione: ['', Validators.required],
      numeroMassimoPrenotazioni: ['', Validators.required],
      luogo: ['', Validators.required],
      provincia: ['', Validators.required],
      tipo: ['', Validators.required],
      organizzatoreId: [null, Validators.required]
    });
  }


  ngOnInit() {
    // Iscriviti all'osservabile per ottenere l'ID dell'utente
    this.authService.getUserId().subscribe({
      next: userId => {
        if (userId) {
          this.eventoForm.patchValue({ organizzatoreId: userId });
        } else {
          console.error('ID utente non trovato');
        }
      },
      error: error => {
        console.error('Errore durante l\'ottenimento dell\'ID utente:', error);
      }
    });
  }


  onSubmit() {
    if (this.eventoForm.valid) {
      const eventoDto = this.eventoForm.value;

      this.eventiService.creaEvento(eventoDto).subscribe({
        next: (nuovoEvento) => {
          console.log('Nuovo evento creato:', nuovoEvento);
          this.router.navigate(['/home']);
        },
        error: (error) => {
          console.error('Errore durante la creazione dell\'evento:', error);
        }
      });
    } else {
      console.error('Form non valido');
    }
  }




}
