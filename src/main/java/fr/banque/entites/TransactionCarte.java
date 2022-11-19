package fr.banque.entites;

import lombok.*;
import org.springframework.stereotype.Component;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransactionCarte extends Transaction {
    @ManyToOne
    @JoinColumn(name = "carte_numCarte")
    private Carte carte;
}
