package fr.banque.entites;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public abstract class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int idTransaction;
    private Date dateTransaction;
    @ManyToOne
    @JoinColumn(name = "compte_iban")
    private Compte compte;
    private int montant;
    private String intitule;
}
