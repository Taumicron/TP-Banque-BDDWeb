package fr.banque.controllers.dto.compte;

import fr.banque.entites.Client;
import lombok.*;

import java.util.List;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateCompteResponse {
    private String intituleCompte;
    private String typeCompte;
    private List<CreateCompteClientResponse> titulaires;
    private String iban;
    private String dateCreation;

    @Getter
    @Setter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class CreateCompteClientResponse {
        private Integer idClient;
    }
}
