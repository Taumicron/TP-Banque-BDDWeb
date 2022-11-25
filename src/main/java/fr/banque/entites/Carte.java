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
    private String mdp; //MDP hash par algorithme MD5
    private String dateExp;
    @ManyToOne
    private Compte compteCarte;
    @ManyToOne
    private Client titulaire;

}
