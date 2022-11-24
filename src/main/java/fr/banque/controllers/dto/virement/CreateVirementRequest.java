package fr.banque.controllers.dto.virement;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CreateVirementRequest {
    private String ibanCompteEmetteur;
    private String ibanCompteBeneficiaire;
    private Double montant;
    private String libelleVirement;
}
