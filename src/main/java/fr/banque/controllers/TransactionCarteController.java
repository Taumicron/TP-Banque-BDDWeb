package fr.banque.controllers;

import fr.banque.entites.TransactionCarte;
import fr.banque.services.TransactionCarteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("transactionscarte")
public class TransactionCarteController {
    @Autowired
    private TransactionCarteService serTransactionCarte;

    @PostMapping
    public String getTransactionCompte(){
        this.serTransactionCarte.saveTransactionCarte(TransactionCarte.builder().build());
        return "Transaction Carte sauvegardée";
    }
}
