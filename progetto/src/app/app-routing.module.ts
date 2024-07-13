import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomeComponent } from './pages/home/home.component';
import { GuestGuard } from './auth/guest.guard';
import { AuthGuard } from './auth/auth.guard';
import { AggiungieventoComponent } from './pages/aggiungievento/aggiungievento.component';
import { OrganizzatoreGuard } from './auth/organizzatore.guard';
import { DettaglioeventoComponent } from './pages/dettaglioevento/dettaglioevento.component';
import { NotOrganizatoreGuard } from './auth/not-organizatore.guard';
import { EventiComponent } from './pages/eventi/eventi.component';
import { PaginapersonaleutenteComponent } from './pages/paginapersonaleutente/paginapersonaleutente.component';

const routes: Routes = [

  {
    path:'home',
    component:HomeComponent,
  },

  { path: 'auth', loadChildren: () => import('./auth/auth.module').then(m => m.AuthModule),
    canActivate: [GuestGuard],
    canActivateChild: [GuestGuard],
  },

  {
    path: "aggiungi-evento",
    component:AggiungieventoComponent,
    canActivate: [AuthGuard,OrganizzatoreGuard]
  },

  {
    path: "dettaglio-evento/:id",
    component:DettaglioeventoComponent,
    canActivate: [AuthGuard,NotOrganizatoreGuard]
  },

  {
    path: "eventi",
    component:EventiComponent,
    canActivate: [AuthGuard,NotOrganizatoreGuard]
  },



  {
    path: "pagina-utente/:id",
    component:PaginapersonaleutenteComponent,
    canActivate: [AuthGuard]
  },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
