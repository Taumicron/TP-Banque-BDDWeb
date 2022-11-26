package fr.banque.controllers.dto.client;

import lombok.*;

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