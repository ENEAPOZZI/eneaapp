import { TipoEvento } from "./TipoEvento.enum";
import { Iuser } from "./iuser";

export interface Ievento {
  id: number;
  nome: string;
  descrizione: string;
  numeroMassimoPrenotazioni: number;
  luogo: string;
  provincia: string;
  tipo: TipoEvento;
  organizzatoreId: number;
  utentiIscritti: Iuser[];
  isExpired: boolean;
}
