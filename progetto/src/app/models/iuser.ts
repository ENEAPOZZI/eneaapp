import { Ievento } from "./Ievento";

export interface Iuser {
  id: number;
  username: string;
  email: string;
  password: string;
  nome: string;
  cognome: string;
  ruolo: string;
  token: string;
  eventiIscritti: Ievento[];
  eventiCreati: Ievento[];
}
