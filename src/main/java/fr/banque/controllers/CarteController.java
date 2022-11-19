package fr.banque.controllers;

import fr.banque.entites.Carte;
import fr.banque.services.CarteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("cartes")
public class CarteController {
    @Autowired
    private CarteService serCarte;

    @PostMapping
    public String getCarte(){
        this.serCarte.saveCarte(Carte.builder().build());
        return "Carte Sauvegardée.";
    }
}
