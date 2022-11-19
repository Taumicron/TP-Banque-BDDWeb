package fr.banque.services;

import fr.banque.entites.Compte;
import fr.banque.repositories.CompteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CompteService {
    @Autowired
    private CompteRepository repCompte;

    public void saveCompte(Compte c){
        this.repCompte.save(c);
    }
}
