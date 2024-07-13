package it.epicode.feste.controllers;


import it.epicode.feste.controllers.exceptions.FieldValidationException;
import it.epicode.feste.enteties.Eventi;
import it.epicode.feste.enteties.TipoEvento;
import it.epicode.feste.services.EventiService;
import it.epicode.feste.services.exception.EventoNonTrovatoException;
import it.epicode.feste.services.exception.UtenteNonTrovatoException;
import it.epicode.feste.services.records.EventoDto;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@RestController
@CrossOrigin(origins = "http://localhost:59685")
@RequestMapping("/eventi")
public class EventiController {


    @Autowired
    private EventiService eventiService;

    @PostMapping("/crea")
    public ResponseEntity<?> creaEvento(@RequestBody @Valid EventoDto eventoDto, BindingResult validation, UriComponentsBuilder uri) {
        if (validation.hasErrors()) {
            throw new FieldValidationException(validation.getAllErrors());
        }

        Eventi nuovoEvento = eventiService.creaEvento(eventoDto);

        var headers = new HttpHeaders();
        headers.setLocation(uri.path("/api/eventi/{id}").buildAndExpand(nuovoEvento.getId()).toUri());

        return new ResponseEntity<>(nuovoEvento, headers, HttpStatus.CREATED);
    }




    @PostMapping("iscrizione/{idEvento}/iscrivi/{idUtente}")
    public ResponseEntity<?> iscriviUtenteAllEvento(@PathVariable Long idEvento, @PathVariable Long idUtente) {
        try {
            eventiService.iscriviUtenteAllEvento(idEvento, idUtente);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (EventoNonTrovatoException | UtenteNonTrovatoException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>("Errore durante l'iscrizione all'evento", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping("/organizzati/{organizzatoreId}")
    public ResponseEntity<List<Eventi>> getEventiOrganizzati(@PathVariable Long organizzatoreId) {
        try {
            List<Eventi> eventiOrganizzati = eventiService.getEventiOrganizzati(organizzatoreId);
            return new ResponseEntity<>(eventiOrganizzati, HttpStatus.OK);
        } catch (UtenteNonTrovatoException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @DeleteMapping("/{eventoId}/elimina")
    public ResponseEntity<?> eliminaEvento(@PathVariable Long eventoId) {
        try {
            eventiService.eliminaEvento(eventoId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (EventoNonTrovatoException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/tutti")
    public ResponseEntity<List<Eventi>> getAllEventi() {
        List<Eventi> eventi = eventiService.getAllEventi();
        return ResponseEntity.ok(eventi);
    }



}
















