package fr.banque.controllers.dto.compte;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GetComptesResponse {
    private String iban;
    private String solde;
    private String intituleCompte;
    private String typeCompte;
    private List<GetComptesTitulaireResponse> titulairesCompte;
    private List<GetComptesTransactionResponse> transactions;

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class GetComptesTitulaireResponse {
        private String idClient;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class GetComptesTransactionResponse {
        private String id;
        private String montant;
        private String typeTransaction;
        private String typeSource;
        private String idSource;
    }
}
