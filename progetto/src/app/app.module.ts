import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';



import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';

import { HttpClientModule } from '@angular/common/http';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';
import { NavbarComponent } from './GeneralComponent/navbar/navbar.component';
import { EventiComponent } from './pages/eventi/eventi.component';
import { AggiungieventoComponent } from './pages/aggiungievento/aggiungievento.component';
import { PaginapersonaleutenteComponent } from './pages/paginapersonaleutente/paginapersonaleutente.component';
import { DettaglioeventoComponent } from './pages/dettaglioevento/dettaglioevento.component';
import { HomeComponent } from './pages/home/home.component';
import { FooterComponent } from './GeneralComponent/footer/footer.component';


@NgModule({
  declarations: [
    AppComponent,
    NavbarComponent,
    EventiComponent,
    AggiungieventoComponent,
    PaginapersonaleutenteComponent,
    DettaglioeventoComponent,
    HomeComponent,
    FooterComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    ReactiveFormsModule,
    FormsModule,
    RouterModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
