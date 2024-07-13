package it.epicode.feste.services;


import it.epicode.feste.config.JwtUtils;
import it.epicode.feste.enteties.Eventi;
import it.epicode.feste.enteties.Utenti;
import it.epicode.feste.repositories.UtentiRepository;
import it.epicode.feste.services.dto.LoginResponseDto;
import it.epicode.feste.services.dto.RegisterUserDto;
import it.epicode.feste.services.dto.RegisteredUserDto;
import it.epicode.feste.services.dto.UtentiToLoginResponseDtoMapperImpl;
import it.epicode.feste.services.exception.UtenteNonTrovatoException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class UtentiService {


    private final UtentiToLoginResponseDtoMapperImpl mapper;

    @Autowired
    public UtentiService(UtentiToLoginResponseDtoMapperImpl mapper) {
        this.mapper = mapper;
    }

    @Autowired
    UtentiRepository utentiRep;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    private AuthenticationManager auth;

    @Autowired
    private JwtUtils jwt;

    @Autowired
    Mapper<RegisterUserDto, Utenti> mapEntity;
    @Autowired
    Mapper<Utenti, RegisteredUserDto> mapRegisteredUser;
    @Autowired
    Mapper<Utenti, LoginResponseDto> mapLogin;


    public RegisteredUserDto register(RegisterUserDto user) {
        var u = mapEntity.map(user);
        var p = encoder.encode(user.getPassword());
        log.info("Password encrypted: {}", p);
        u.setPassword(p);

        if (user.getRuolo() != null) {
            u.setRuolo(user.getRuolo());
        }

        var ut = mapRegisteredUser.map(utentiRep.save(u));

        return ut;
    }


    public Optional<LoginResponseDto> login(String username, String password) {

        Authentication authentication = auth.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        Utenti utente = utentiRep.findOneByUsername(username).orElseThrow(() -> new UsernameNotFoundException("Utente non trovato"));
        System.out.println("Utente recuperato: " + utente);
        System.out.println("Id dell'utente recuperato: " + utente.getId());


        LoginResponseDto dto = mapper.mapUtentiToLoginResponseDto(utente);

        String token = jwt.generateToken(authentication);
        dto.setToken(token);

        System.out.println("LoginResponseDto generato: " + dto);

        return Optional.of(dto);
    }




    public List<Eventi> getEventiIscritto(Long utenteId) {
        Utenti utente = utentiRep.findById(utenteId)
                .orElseThrow(() -> new UtenteNonTrovatoException("Utente non trovato"));

        return utente.getEventiIscritti();
    }






}
