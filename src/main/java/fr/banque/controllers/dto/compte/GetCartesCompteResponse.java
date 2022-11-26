package fr.banque.controllers.dto.compte;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GetCartesCompteResponse {
    private String numeroCarte;
    private String dateExpiration;
    private List<GetCartesCompteTitulaire> titulaireCarte;

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class GetCartesCompteTitulaire {
        private String idClient;
    }
}
