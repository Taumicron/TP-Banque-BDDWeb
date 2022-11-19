package fr.banque.controllers;

import fr.banque.entites.Compte;
import fr.banque.services.CompteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("comptes")
public class CompteController {
    @Autowired
    private CompteService serCompte;

    @PostMapping
    public String getCompte(){
        this.serCompte.saveCompte(Compte.builder().build());
        return "Compte sauvegardé.";
    }
}
