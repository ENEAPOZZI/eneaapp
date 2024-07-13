package it.epicode.feste.services.dto;


import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
public class LoginResponseDto {
    private long id;
    private String username;
    private String nome;
    private String cognome;
    private String email;
    private String ruolo;
    private String token;

    @Builder(setterPrefix = "with")
    public LoginResponseDto(long id, String username, String nome, String cognome, String ruolo, String token, String email) {
        this.id = id;
        this.username = username;
        this.nome = nome;
        this.cognome = cognome;
        this.email = email;
        this.ruolo = ruolo;
        this.token = token;
    }
}
