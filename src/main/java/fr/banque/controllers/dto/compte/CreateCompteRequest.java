package fr.banque.controllers.dto.compte;

import fr.banque.entites.Client;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateCompteRequest {
    private String intituleCompte;
    private String typeCompte;
    private List<ClientCompteRequest> titulairesCompte;


    @Getter
    @Setter
    @NoArgsConstructor
    public static class ClientCompteRequest {
        private Integer idClient;
    }
}
