package fr.banque.services;

import fr.banque.controllers.dto.BadRequestException;
import fr.banque.controllers.dto.virement.CreateVirementRequest;
import fr.banque.controllers.dto.virement.CreateVirementResponse;
import fr.banque.entites.Compte;
import fr.banque.entites.Virement;
import fr.banque.repositories.CompteRepository;
import fr.banque.repositories.VirementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class VirementService {
    @Autowired
    private VirementRepository repVirement;
    @Autowired
    private CompteRepository repCompte;

    public CreateVirementResponse saveVirement(CreateVirementRequest request) throws BadRequestException {
        if (request.getIbanCompteEmetteur() == null || request.getIbanCompteBeneficiaire() == null || request.getMontant() == null || request.getLibelleVirement() == null){
            throw new BadRequestException("Mauvais format de requête.");
        }
        else if (!request.getIbanCompteBeneficiaire().matches("^FR\\d{12}[0-9A-Z]{11}\\d{2}")){
            throw new BadRequestException("L'iban du bénéficiare n'est pas au bon format.");
        } else if (!request.getIbanCompteEmetteur().matches("^FR\\d{12}[0-9A-Z]{11}\\d{2}")){
            throw new BadRequestException("L'iban de l'émetteur n'est pas au bon format.");
        }

        Optional<Compte> emetteurOpt = repCompte.findById(request.getIbanCompteEmetteur());
        Optional<Compte> beneficiaireOpt = repCompte.findById(request.getIbanCompteBeneficiaire());


        if (emetteurOpt.isEmpty()){
            throw new BadRequestException("L'émetteur est introuvable.");
        } else if (beneficiaireOpt.isEmpty()){
            throw new BadRequestException("Le bénéficiaire est introuvable.");
        } else if (emetteurOpt.get().getSolde()-request.getMontant() < 0){
            throw new BadRequestException("Le solde de l'émetteur ne lui permet pas d'effectuer ce virement.");
        }

        Compte emetteur = emetteurOpt.get();
        Compte beneficiaire = beneficiaireOpt.get();

        Virement toSaveEmetteur = Virement.builder()
                .dateTransaction(LocalDateTime.now().toString())
                .compte(emetteur)
                .montant(request.getMontant())
                .intitule(request.getLibelleVirement())
                .compteCible(beneficiaire)
                .build();

        Virement toSaveBeneficiaire = Virement.builder()
                .dateTransaction(LocalDateTime.now().toString())
                .compte(beneficiaire)
                .montant(- request.getMontant())
                .intitule(request.getLibelleVirement())
                .compteCible(emetteur)
                .build();

        emetteur.setSolde(emetteur.getSolde() - request.getMontant());
        beneficiaire.setSolde(beneficiaire.getSolde() + request.getMontant());
        this.repCompte.save(emetteur);
        this.repCompte.save(beneficiaire);
        this.repVirement.save(toSaveBeneficiaire);
        return buildCreateVirementResponse(this.repVirement.save(toSaveEmetteur));
    }

    public CreateVirementResponse buildCreateVirementResponse(Virement v){
        List<CreateVirementResponse.CreateVirementTransactionResponse> transactions = new ArrayList<CreateVirementResponse.CreateVirementTransactionResponse>();
        CreateVirementResponse.CreateVirementTransactionResponse transaction = CreateVirementResponse.CreateVirementTransactionResponse.builder()
                .id(v.getCompteCible().getIban())
                .montant(v.getMontant().toString())
                .typeTransaction(v.getMontant() > 0 ? "CREDIT" : "DEBIT")
                .typeSource("VIREMENT")
                .idSource(String.valueOf(v.getCompte().getIban()))
                .build();
        transactions.add(transaction);
        return CreateVirementResponse.builder()
                .idVirement(v.getIdTransaction().toString())
                .dateCreation(v.getDateTransaction())
                .transactions(transactions)
                .build();
    }
}
