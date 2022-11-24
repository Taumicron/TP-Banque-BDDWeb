package fr.banque.entites;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public abstract class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer idTransaction;
    private String dateTransaction;
    @ManyToOne
    @JoinColumn(name = "compte_iban")
    private Compte compte;
    private Double montant;
    private String intitule;
}
