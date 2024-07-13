package it.epicode.feste.repositories;

import it.epicode.feste.enteties.Eventi;
import it.epicode.feste.enteties.TipoEvento;
import it.epicode.feste.enteties.Utenti;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.time.LocalDate;
import java.util.List;

public interface EventiRepository extends JpaRepository<Eventi, Long>, PagingAndSortingRepository<Eventi, Long> {

    List<Eventi> findByNomeContainingIgnoreCase(String nome);


    List<Eventi> findByTipo(TipoEvento tipo);

    List<Eventi> findByProvinciaIgnoreCase(String provincia);

    // Ricerca per nome e data

    // Ricerca per nome e provincia
    List<Eventi> findByNomeContainingIgnoreCaseAndProvinciaIgnoreCase(String nome, String provincia);

    // Ricerca per nome e tipo
    List<Eventi> findByNomeContainingIgnoreCaseAndTipo(String nome, TipoEvento tipo);



    // Ricerca per provincia e tipo
    List<Eventi> findByProvinciaIgnoreCaseAndTipo(String provincia, TipoEvento tipo);



    // Ricerca per tutti i parametri
    List<Eventi> findByOrganizzatore(Utenti organizzatore);



    List<Eventi> findByNomeContainingIgnoreCaseAndProvinciaIgnoreCaseAndTipo(String nome, String provincia, TipoEvento tipo);


}
