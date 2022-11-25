package fr.banque.controllers.dto.transactioncarte;

import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateTransactionCarteResponse {
    private Integer idTransaction;
    private Double montant;
    private String typeTransaction;
    private String dateCreation;
}
