package it.epicode.feste.enteties;



import jakarta.persistence.*;
import lombok.*;

import java.util.List;


@Entity
@Table(name = "eventi")
@Data
@Builder(setterPrefix = "with")
@AllArgsConstructor
@NoArgsConstructor
public class Eventi  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String descrizione;
    private int numeroMassimoPrenotazioni;
    private String luogo;
    private String provincia;
    private String numeroPartecipanti;

    @Enumerated(EnumType.STRING)
    private TipoEvento tipo;

    @ManyToOne()
    @JoinColumn(name = "utente_id")
    private Utenti organizzatore;

    @ManyToMany
    @JoinTable(
            name = "utenti_eventi",
            joinColumns = @JoinColumn(name = "evento_id"),
            inverseJoinColumns = @JoinColumn(name = "utente_id")
    )
    private List<Utenti> utentiIscritti;

    private boolean isExpired;


}
