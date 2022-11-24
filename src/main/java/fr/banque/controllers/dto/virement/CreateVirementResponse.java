package fr.banque.controllers.dto.virement;

import lombok.*;

import java.util.List;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateVirementResponse {
    private String idVirement;
    private String dateCreation;
    private List<CreateVirementTransactionResponse> transactions;

    @Getter
    @Setter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class CreateVirementTransactionResponse{
        private String id;
        private String montant;
        private String typeTransaction;
        private String typeSource;
        private String idSource;
    }
}
