package fr.banque.services;

import fr.banque.controllers.dto.BadRequestException;
import fr.banque.controllers.dto.NotFoundException;
import fr.banque.controllers.dto.transactioncarte.CreateTransactionCarteRequest;
import fr.banque.controllers.dto.transactioncarte.CreateTransactionCarteResponse;
import fr.banque.entites.Carte;
import fr.banque.entites.Compte;
import fr.banque.entites.TransactionCarte;
import fr.banque.repositories.CarteRepository;
import fr.banque.repositories.CompteRepository;
import fr.banque.repositories.TransactionCarteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TransactionCarteService {
    @Autowired
    private TransactionCarteRepository repTransactionCarte;
    @Autowired
    private CompteRepository repCompte;
    @Autowired
    private CarteRepository repCarte;

    public CreateTransactionCarteResponse saveTransactionCarte(CreateTransactionCarteRequest request, String iban, String numeroCarte) throws NotFoundException, BadRequestException {
        if (iban.length() != 27 || numeroCarte.length() != 16){
            throw new BadRequestException();
        }
        Optional<Compte> compteOpt = this.repCompte.findById(iban);
        Optional<Carte> carteOpt = this.repCarte.findById(numeroCarte);
        if (compteOpt.isEmpty() || carteOpt.isEmpty()){
            throw new NotFoundException();
        }


        Compte compte = compteOpt.get();
        Carte carte = carteOpt.get();

        if (request.getMontant() > 0 && compte.getSolde() < request.getMontant()){
            throw new BadRequestException();
        }

        TransactionCarte toSaveTraCarte = TransactionCarte.builder()
                .carte(carte)
                .montant(request.getMontant().doubleValue())
                .dateTransaction(request.getDateCreation())
                .compte(compte)
                .intitule(request.getMontant() > 0 ? "Débit" : "Crédit" +" de "+ Math.abs(request.getMontant()) +" avec la CB finissant par "+ numeroCarte.substring(numeroCarte.length() - 4))
                .build();

        compte.setSolde(compte.getSolde() - request.getMontant());
        this.repCompte.save(compte);
        return buildCreateTransactionCarteResponse(this.repTransactionCarte.save(toSaveTraCarte));
    }

    public CreateTransactionCarteResponse buildCreateTransactionCarteResponse(TransactionCarte t){
        return CreateTransactionCarteResponse.builder()
                .idTransaction(t.getIdTransaction())
                .montant(t.getMontant())
                .typeTransaction(t.getMontant() > 0 ? "DEBIT" : "CREDIT")
                .dateCreation(t.getDateTransaction())
                .build();
    }
}
