package fr.banque.entites;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Entity
public class Carte {
    @Id
    private String numCarte;
    private String mdp; //TODO Hash le mdp.
    private Date dateExp;
    @ManyToOne
    @JoinColumn(name = "compte_carte_iban")
    private Compte compteCarte;

}
