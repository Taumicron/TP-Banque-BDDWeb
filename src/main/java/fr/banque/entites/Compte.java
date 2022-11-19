package fr.banque.entites;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Entity
public class Compte {
    @Id
    //Generated Value, IBAN sous contraintes. (avec une interface ?)
    private String iban;
    private int solde;
    private String intituleCompte;
    private String typeCompte;
    @ManyToMany
    private List<Client> titulaires;
    @OneToMany(mappedBy = "idTransaction")
    private List<Transaction> transactions;
    @ManyToMany
    private List<Carte> cartes;
}
