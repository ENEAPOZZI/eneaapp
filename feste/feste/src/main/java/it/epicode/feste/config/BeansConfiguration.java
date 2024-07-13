package it.epicode.feste.config;


import it.epicode.feste.enteties.Utenti;
import it.epicode.feste.services.Mapper;
import it.epicode.feste.services.dto.LoginResponseDto;
import it.epicode.feste.services.dto.RegisterUserDto;
import it.epicode.feste.services.dto.RegisteredUserDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.ArrayList;
import java.util.Properties;

@Configuration
public class BeansConfiguration {


    @Bean
    @Scope("singleton")
    Mapper<RegisterUserDto, Utenti> mapRegisterUser2UserEntity() {
        return (input) -> Utenti.builder() //
                .withCognome(input.getCognome())
                .withNome(input.getNome())
                .withEmail(input.getEmail())
                .withRuolo(input.getRuolo())
                .withPassword(input.getPassword()) //
                .withUsername(input.getUsername()) //
                .build();
    }

    @Bean
    @Scope("singleton")
    Mapper<Utenti, RegisteredUserDto> mapUserEntity2RegisteredUser() {
        return (input) -> RegisteredUserDto.builder()
                .withId(input.getId())
                .withUsername(input.getUsername())
                .withNome(input.getNome())
                .withCognome(input.getCognome())
                .withRuolo(input.getRuolo())
                .build();
    }

    @Bean
    @Scope("singleton")
    Mapper<Utenti, LoginResponseDto> mapUserEntity2LoginResponse() {
        return (input) -> LoginResponseDto.builder()
                .withUsername(input.getUsername())
                .withNome(input.getNome())
                .withCognome(input.getCognome())
                .withRuolo(input.getRuolo())
                .build();

    }
    @Bean
    public JavaMailSenderImpl getJavaMailSender(@Value("${gmail.mail.transport.protocol}" ) String protocol,
                                                @Value("${gmail.mail.smtp.auth}" ) String auth,
                                                @Value("${gmail.mail.smtp.starttls.enable}" )String starttls,
                                                @Value("${gmail.mail.debug}" )String debug,
                                                @Value("${gmail.mail.from}" )String from,
                                                @Value("${gmail.mail.from.password}" )String password,
                                                @Value("${gmail.smtp.ssl.enable}" )String ssl,
                                                @Value("${gmail.smtp.host}" )String host,
                                                @Value("${gmail.smtp.port}" )String port){

        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(host);
        mailSender.setPort(Integer.parseInt(port));

        mailSender.setUsername(from);
        mailSender.setPassword(password);

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", protocol);
        props.put("mail.smtp.auth", auth);
        props.put("mail.smtp.starttls.enable", starttls);
        props.put("mail.debug", debug);
        props.put("smtp.ssl.enable",ssl);

        return mailSender;
    }





}
