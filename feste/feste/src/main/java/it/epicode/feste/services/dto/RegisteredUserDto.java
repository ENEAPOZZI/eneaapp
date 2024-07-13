package it.epicode.feste.services.dto;


import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
public class RegisteredUserDto {

    private long id;
    private String username;
    private String nome;
    private String cognome;
    private String ruolo;


    @Builder(setterPrefix = "with")
    public RegisteredUserDto(long id, String username, String nome, String cognome, String ruolo ) {
        this.id = id;
        this.username = username;
        this.nome = nome;
        this.cognome = cognome;
        this.ruolo = ruolo;

    }
}
