package it.epicode.feste.controllers.models;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;


import java.util.Arrays;

public record RegisterUserModel( //
                                 @NotBlank @Size(max = 125) String username,
                                 @NotBlank @Email @Size(max = 100) String email,
                                 @NotBlank @Size(max = 15) String password,
                                 @NotBlank @Size(max = 50) String nome,
                                 @NotBlank @Size(max = 50) String cognome,
                                 String ruolo
) {}


