package fr.banque.controllers.dto.carte;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateCarteRequest {
    private Integer titulaireCarte;
    private String code;
}
