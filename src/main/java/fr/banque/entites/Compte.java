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
    private Double solde;
    private String intituleCompte;
    private String typeCompte;
    private String numeroCompte;
    @ManyToMany
    @JoinTable(name="CLIENT_COMPTE",
            joinColumns=@JoinColumn(name="iban"),
            inverseJoinColumns=@JoinColumn(name="idClient")
    )
    @ToString.Exclude
    private List<Client> titulaires;
    @OneToMany(mappedBy = "idTransaction")
    @ToString.Exclude
    private List<Transaction> transactions;
    @OneToMany(mappedBy = "compteCarte")
    @ToString.Exclude
    private List<Carte> cartes;

}
