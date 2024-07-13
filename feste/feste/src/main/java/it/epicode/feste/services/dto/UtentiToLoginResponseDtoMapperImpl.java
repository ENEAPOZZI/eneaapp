package it.epicode.feste.services.dto;

import it.epicode.feste.enteties.Utenti;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;


@Component
public class UtentiToLoginResponseDtoMapperImpl {

    public LoginResponseDto mapUtentiToLoginResponseDto(Utenti utenti) {
        return LoginResponseDto.builder()
                .withId(utenti.getId())
                .withUsername(utenti.getUsername())
                .withNome(utenti.getNome())
                .withEmail(utenti.getEmail())
                .withCognome(utenti.getCognome())
                .withRuolo(utenti.getRuolo())
                .build();
    }
}

