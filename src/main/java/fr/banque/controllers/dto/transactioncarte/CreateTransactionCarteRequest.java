package fr.banque.controllers.dto.transactioncarte;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateTransactionCarteRequest {
    private Integer montant;
    private String dateCreation;
}
