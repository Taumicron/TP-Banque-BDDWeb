package fr.banque.controllers;

import fr.banque.services.TransactionCarteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("transactionscarte")
public class TransactionCarteController {
    @Autowired
    private TransactionCarteService serTransactionCarte;

}
