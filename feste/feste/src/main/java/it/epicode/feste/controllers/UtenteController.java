package it.epicode.feste.controllers;


import it.epicode.feste.controllers.exceptions.FieldValidationException;
import it.epicode.feste.controllers.models.LoginModel;
import it.epicode.feste.controllers.models.RegisterUserModel;
import it.epicode.feste.enteties.Eventi;
import it.epicode.feste.enteties.Utenti;

import it.epicode.feste.services.EventiService;
import it.epicode.feste.services.UtentiService;
import it.epicode.feste.services.dto.LoginResponseDto;
import it.epicode.feste.services.dto.RegisterUserDto;
import it.epicode.feste.services.dto.RegisteredUserDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.util.UriComponentsBuilder;


import java.util.List;


@Slf4j
@RestController
@RequestMapping("/utenti")
@CrossOrigin(origins = "http://localhost:59685")
public class UtenteController {


    @Autowired
    private UtentiService utentiService;

    @Autowired
    private EventiService eventiService;




    @PostMapping("/register")
    public ResponseEntity<RegisteredUserDto> register(
            @RequestBody @Validated RegisterUserModel model,
            BindingResult validation,
            UriComponentsBuilder uri) {

        if (validation.hasErrors()) {
            throw new FieldValidationException(validation.getAllErrors());
        }


        RegisterUserDto userDto = RegisterUserDto.builder()
                .withUsername(model.username())
                .withEmail(model.email())
                .withPassword(model.password())
                .withNome(model.nome())
                .withCognome(model.cognome())
                .withRuolo(model.ruolo())
                .build();

        RegisteredUserDto registeredUserDto = utentiService.register(userDto);

        var headers = new HttpHeaders();
        headers.add("Location", uri.path("/api/utenti/{id}").buildAndExpand(registeredUserDto.getId()).toString());

        return ResponseEntity.status(HttpStatus.CREATED).headers(headers).body(registeredUserDto);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Validated LoginModel model, //
                                   BindingResult validation) {
        if (validation.hasErrors()) {
            throw new FieldValidationException(validation.getAllErrors());
        }

        LoginResponseDto loginResponseDto = utentiService.login(model.username(), model.password())
                .orElseThrow(() -> new UsernameNotFoundException("Utente non trovato"));

        return ResponseEntity.ok(loginResponseDto);
    }



    @GetMapping("/{utenteId}/eventi")
    public ResponseEntity<List<Eventi>> getEventiIscritto(@PathVariable Long utenteId) {
        List<Eventi> eventi = utentiService.getEventiIscritto(utenteId);
        return new ResponseEntity<>(eventi, HttpStatus.OK);
    }





}








