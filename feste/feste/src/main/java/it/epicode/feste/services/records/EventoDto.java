package it.epicode.feste.services.records;


import it.epicode.feste.enteties.TipoEvento;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;



public record EventoDto(
        @NotBlank String nome,
        @NotBlank String descrizione,
        @NotNull Integer numeroMassimoPrenotazioni,
        @NotBlank String luogo,
        @NotBlank String provincia,
        @NotNull TipoEvento tipo,
        @NotNull Long organizzatoreId
) {}
