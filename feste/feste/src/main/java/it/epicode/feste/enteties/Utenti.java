package it.epicode.feste.enteties;




import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name="utenti")
@Data
@Builder(setterPrefix = "with")
@AllArgsConstructor
@NoArgsConstructor
public class Utenti {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String email;
    private String password;
    private String nome;
    private String cognome;

    private String ruolo;

    @ManyToMany(mappedBy = "utentiIscritti", cascade = CascadeType.PERSIST)
    private List<Eventi> eventiIscritti;


    @OneToMany(mappedBy = "organizzatore", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Eventi> eventiCreati;



}
