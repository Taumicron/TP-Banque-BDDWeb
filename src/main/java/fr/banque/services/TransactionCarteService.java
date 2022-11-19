package fr.banque.services;

import fr.banque.entites.TransactionCarte;
import fr.banque.repositories.TransactionCarteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransactionCarteService {
    @Autowired
    private TransactionCarteRepository repTransactionCarte;

    public void saveTransactionCarte(TransactionCarte t){
        this.repTransactionCarte.save(t);
    }
}
