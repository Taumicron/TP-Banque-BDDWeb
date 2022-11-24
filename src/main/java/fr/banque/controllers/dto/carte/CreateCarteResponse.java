package fr.banque.controllers.dto.carte;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateCarteResponse {
    private int titulaireCarte;
    private String numCarte;
    private String dateExpiration;
}
