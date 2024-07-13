import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Ievento } from './models/Ievento';
import { BehaviorSubject, Observable, catchError, tap } from 'rxjs';
import { environment } from '../environments/environment.development';
import { AuthService } from './auth/auth.service';

@Injectable({
  providedIn: 'root'
})
export class EventiService {


  private eventsCache: Ievento[] = [];
  private eventSubject = new BehaviorSubject<Ievento[]>([]);
  public event$ = this.eventSubject.asObservable();

  constructor(private http: HttpClient  , private authService: AuthService)
  {

    this.getAllEvent().subscribe(data => {
      this.eventsCache = data;
      this.eventSubject.next(data);
    });

  }


  tuttiEventiUrl= environment.getAllEvent

  getAllEvent(): Observable<Ievento[]> {
    const headers = new HttpHeaders().set('Cache-Control', 'no-cache');
    return  this.http.get<Ievento[]>(this.tuttiEventiUrl,{headers});
  }


  creaEventiUrl= environment.registerEvent

  creaEvento(eventoDto: Ievento): Observable<any> {

    const token = this.authService.getAccessToken();
    console.log('Token JWT:', token);
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${token}`
    });

    console.log('ecco il token',this.authService.getAccessToken())
    return this.http.post<any>(this.creaEventiUrl, eventoDto,{ headers }).pipe(
      tap((nuovoEvento: any) => {
        this.eventsCache = [...this.eventsCache, nuovoEvento];
        this.eventSubject.next(this.eventsCache);
      }),
      catchError(error => {
        console.error('Errore durante la creazione dell evento:', error);
        throw error;
      })
    );

  }


  private updateUrl = environment.updateEvent

  updateEvent(idEvento: number, eventoDto: Ievento): Observable<Ievento> {

    const url =  `${this.updateUrl}/${idEvento}`;

    return this.http.put<Ievento>(url, eventoDto).pipe(
      tap((eventoAggiornato: Ievento) => {
        // Aggiorna la cache degli eventi con l'evento aggiornato
        const index = this.eventsCache.findIndex(evento => evento.id === idEvento);
        if (index !== -1) {
          this.eventsCache[index] = eventoAggiornato;
          this.eventSubject.next(this.eventsCache);
        }
      }),
      catchError(error => {
        console.error('Errore durante l\'aggiornamento dell\'evento:', error);
        throw error;
      })
    );
  }


  private iscrizoneUrl = environment.iscrizioneEvent

  iscriviUtenteAllEvento(idEvento: number, idUtente: number): Observable<void> {

    const url = `${this.iscrizoneUrl}${idEvento}/iscrivi/${idUtente}`;
    return this.http.post<void>(url, null, {
      headers: new HttpHeaders({
        'Accept': 'application/json'
      })
    });

  }


  private eventiorganizzatiurl = environment.eventiorganizzati

  getEventiOrganizzati(organizzatoreId: number): Observable<Ievento[]> {

    const url = `${this.eventiorganizzatiurl}${organizzatoreId}`;
    return this.http.get<Ievento[]>(url);

  }


  private eliminaeventourl = environment.deletaevento

  eliminaEvento(eventoId: number): Observable<void> {

    console.log('id evento da eliminare' + eventoId)
    const url = `${this.eliminaeventourl}${eventoId}`;;
    return this.http.delete<void>(url).pipe(
      tap(() => this.rimuovieventolista(eventoId))
    );

  }

  rimuovieventolista(eventoId: number): void {

    const currentProducts = this.eventSubject.value;
    const updatedProducts = currentProducts.filter(evento => evento.id !== eventoId);
    this.eventSubject.next(updatedProducts);

  }


  private eventosingoloUrl = environment.eventosingolo

  getEventtById(id: number): Observable<Ievento> {

    const url = `${this.eventosingoloUrl}${id}`;
    return this.http.get<Ievento>(url);

  }

  private cancellazioneIscrizione = environment.cancellazioneIscrizione



  cancellaIscrizioneUtenteDaEvento(idEvento: number, idUtente: number): Observable<any> {
    const url = `${this.cancellazioneIscrizione}${idEvento}/utenti/${idUtente}`;
    const token = this.authService.getAccessToken();

    const headers = new HttpHeaders({
      'Authorization': `Bearer ${token}`
    });

    return this.http.delete(url, { headers });
  }

}

