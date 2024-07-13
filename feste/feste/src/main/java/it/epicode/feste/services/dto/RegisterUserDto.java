package it.epicode.feste.services.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false, onlyExplicitlyIncluded = true)
@Builder(setterPrefix = "with")
@AllArgsConstructor
public class RegisterUserDto extends DtoBase {
    private String username;
    private String email;
    private String password;
    private String nome;
    private String cognome;
    private String ruolo;
}
