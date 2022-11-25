package fr.banque.controllers.dto.client;

import lombok.*;

// Identique à CreateClientRequest

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PutClientRequest {
    private Integer id;
    private String prenom;
    private String nom;
    private String dateNaissance;
    private String telephone;
    private String adressePostale;
}