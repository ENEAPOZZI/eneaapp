package it.epicode.feste.repositories;

import it.epicode.feste.enteties.Utenti;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;

public interface UtentiRepository extends JpaRepository<Utenti, Long>, PagingAndSortingRepository<Utenti, Long> {


    Optional<Utenti> findOneByUsernameAndPassword(String username, String password);
    Optional<Utenti> findOneByUsername(String username);



    Optional<Utenti> findByUsername(String username);
}
