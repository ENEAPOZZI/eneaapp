package it.epicode.feste.services.interfaceservices;

import it.epicode.feste.enteties.Utenti;
import it.epicode.feste.services.dto.LoginResponseDto;
import it.epicode.feste.services.dto.RegisterUserDto;
import it.epicode.feste.services.dto.RegisteredUserDto;

import java.util.Optional;

public interface UtentiInterface {

    RegisteredUserDto register(RegisterUserDto user);

    Optional<LoginResponseDto> login(String username, String password);

    Utenti update(Long id, Utenti ut);

    void sendMailRegistrazione(String email);
}
