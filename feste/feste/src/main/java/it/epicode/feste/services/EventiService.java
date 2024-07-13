package it.epicode.feste.services;


import it.epicode.feste.enteties.Eventi;
import it.epicode.feste.enteties.Utenti;
import it.epicode.feste.repositories.EventiRepository;
import it.epicode.feste.repositories.UtentiRepository;
import it.epicode.feste.services.exception.EventoNonTrovatoException;
import it.epicode.feste.services.exception.UtenteNonTrovatoException;
import it.epicode.feste.services.records.EventoDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
public class EventiService  {

    @Autowired
    private EventiRepository eventiRepository;

    @Autowired
    private UtentiRepository utentiRepository;


    public List<Eventi> getAllEventi() {
        return eventiRepository.findAll();
    }

    // ORGANIZZATORE CREA L'EVENTO
    public Eventi creaEvento(EventoDto eventoDto) {

            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = null;
            if (authentication.getPrincipal() instanceof UserDetails) {
                username = ((UserDetails) authentication.getPrincipal()).getUsername();
            } else {
                username = authentication.getPrincipal().toString();
            }

            Utenti organizzatore = utentiRepository.findByUsername(username)
                    .orElseThrow(() -> new UtenteNonTrovatoException("Organizzatore non trovato"));

            Eventi.EventiBuilder eventiBuilder = Eventi.builder()
                    .withNome(eventoDto.nome())
                    .withDescrizione(eventoDto.descrizione())
                    .withNumeroMassimoPrenotazioni(eventoDto.numeroMassimoPrenotazioni())
                    .withLuogo(eventoDto.luogo())
                    .withProvincia(eventoDto.provincia())
                    .withTipo(eventoDto.tipo());

            Eventi evento = eventiBuilder.build();
            System.out.println(evento);
            evento.setOrganizzatore(organizzatore);

            return eventiRepository.save(evento);

    }

    // ISCRIZIONE  ALL'EVENTO  DELL'UTENTE
    public void iscriviUtenteAllEvento(Long idEvento, Long idUtente) {
        Eventi evento = eventiRepository.findById(idEvento)
                .orElseThrow(() -> new EventoNonTrovatoException("Evento non trovato"));

        // Controlla se l'utente esiste
        Utenti utente = utentiRepository.findById(idUtente)
                .orElseThrow(() -> new UtenteNonTrovatoException("Utente non trovato"));

        evento.getUtentiIscritti().add(utente);

        eventiRepository.save(evento);
    }



    // Metodo per ottenere tutti gli eventi organizzati da un utente, inclusi quelli passati
    public List<Eventi> getEventiOrganizzati(Long organizzatoreId) {
        Utenti organizzatore = utentiRepository.findById(organizzatoreId)
                .orElseThrow(() -> new UtenteNonTrovatoException("Organizzatore non trovato"));

        List<Eventi> eventiOrganizzati = eventiRepository.findByOrganizzatore(organizzatore);



        return eventiOrganizzati;
    }


    // ELIMINA EVENTO TRAMITE UN PULSANTE
    public void eliminaEvento(Long eventoId) {
        Eventi evento = eventiRepository.findById(eventoId)
                .orElseThrow(() -> new EventoNonTrovatoException("Evento non trovato"));

        eventiRepository.delete(evento);
    }



}

