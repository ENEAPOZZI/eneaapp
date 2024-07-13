import { JwtHelperService } from '@auth0/angular-jwt';
import { Injectable } from '@angular/core';
import { Iuser } from '../models/iuser';
import { BehaviorSubject, Observable, map, tap } from 'rxjs';
import { Router } from '@angular/router';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { environment } from '../../environments/environment.development';
import { Ievento } from '../models/Ievento';
import { ILoginData } from '../models/i-login-data';
import { IuserRegistration } from '../models/iuser-registration';

// INTERFACCIA PER LA RISPOSTA
type AccessData = {
  id: number,
  username: string,
  nome: string,
  cognome: string,
  ruolo: string,
  email:string,
  password:string,
  eventiIscritti: [],
  eventiCreati:[],
  token: string
}

@Injectable({
  providedIn: 'root'
})

export class AuthService {

/******************* METODI BASE PER AUTENTICAZIONE *******************/


jwtHelper: JwtHelperService = new JwtHelperService()

authSubject = new BehaviorSubject<Iuser|null>(null);

user$ = this.authSubject.asObservable()


isLoggedIn$ = this.user$.pipe(
  map(user => !!user),
  tap(user =>  this.syncIsLoggedIn = user)
)

syncIsLoggedIn:boolean = false;

constructor(private http:HttpClient, private router:Router) {
  this.restoreUser()
}

  registerUrl:string = environment.registerUrl
  loginUrl:string = environment.loginUrl


  register(newUser:IuserRegistration):Observable<AccessData>{
    const headers = new HttpHeaders({
      'Content-Type': 'application/json'
    });

    return this.http.post<AccessData>(this.registerUrl, newUser, { headers }).pipe(
    tap(data => {
      this.authSubject.next(data);
      console.log('utente registrato', data);
      localStorage.setItem('accessData', JSON.stringify(data));
      this.router.navigate(['/home']);
    })
  );
  }


  login(loginData:ILoginData):Observable<AccessData>{
    return this.http.post<AccessData>(this.loginUrl,loginData)
    .pipe(tap(data => {

      this.authSubject.next(data)
      console.log('utente loggato' , data)
      localStorage.setItem('accessData', JSON.stringify(data))

    }))
  }

  logout(){

    this.authSubject.next(null)
    localStorage.removeItem('accessData')
    this.router.navigate(['/auth/login'])

  }

  getAccessToken():string{

    const userJson = localStorage.getItem('accessData')
    if(!userJson) return '';

    const accessData:AccessData = JSON.parse(userJson)
    if(this.jwtHelper.isTokenExpired(accessData.token)) return '';

    return accessData.token

  }

  autoLogout(jwt:string){

    const expDate = this.jwtHelper.getTokenExpirationDate(jwt) as Date;
    const expMs = expDate.getTime() - new Date().getTime();

    setTimeout(()=>{
      this.logout()
    },expMs)

  }


  restoreUser() {

    let userJson = localStorage.getItem('accessData') || sessionStorage.getItem('accessData');
    if (!userJson) return;

    const accessData: AccessData = JSON.parse(userJson);
    if (this.jwtHelper.isTokenExpired(accessData.token)) return;

    this.authSubject.next(accessData);
    this.autoLogout(accessData.token);

  }


  /******************* FINE METODI BASE PER AUTENTICAZIONE *******************/


  getUserId(): Observable<number | null> {

    return this.user$.pipe(
      map(user => {
        if (user && user.id) {
          return user.id;
        }
        const token = this.getAccessToken();
        if (token) {
          const decodedToken: any = jwt_decode(token);
          return decodedToken.userId || null;
        }
        return null;
      })
    );

  }


/*** METODO CHE CONTROLLA SE L'UTENTE E' UN ORGANIZZATORE ***/


isOrganizer(): Observable<boolean> {

  return this.user$.pipe(
    map(user => {
      if (!user || !user.ruolo) {
        return false;
      }

      return user.ruolo.toLowerCase() === 'organizzatore';
    })
  );

}

 /************************  METODI PER SPECIFICI PER L'APPLICAZIONE ************************/


        /*****  METODI PER VISUALIZZARE EVENTI A CUI SI E' ISCRITTI  *****/


  getUserEventsUrl:string = environment.getUserEventsUrl

  getUserEvents(userId: number): Observable<Ievento[]> {

    const url = `${this.getUserEventsUrl}${userId}/eventi`;
    return this.http.get<Ievento[]>(url);

  }


  /******************* ERRORI *******************/


    /*****  AUTENTICAZIONE *****/


  errors(err: any) {

    switch (err.error) {
        case "Email and Password are required":
            return new Error('Email e password obbligatorie');
            break;
        case "Email already exists":
            return new Error('Utente esistente');
            break;
        case 'Email format is invalid':
            return new Error('Email scritta male');
            break;
        case 'Cannot find user':
            return new Error('utente inesistente');
            break;
            default:
        return new Error('Errore');
            break;
    }

  }

}

  /*****  TOKEN *****/


function jwt_decode(token: string): any {

  throw new Error('Function not implemented.');

}

