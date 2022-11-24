package fr.banque.entites;

import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@DiscriminatorValue("C")
public class TransactionCarte extends Transaction {
    @ManyToOne
    @JoinColumn(name = "carte_numCarte")
    private Carte carte;
}
