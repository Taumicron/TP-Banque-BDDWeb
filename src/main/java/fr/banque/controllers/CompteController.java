package fr.banque.controllers;

import fr.banque.controllers.dto.ErreurRequestMsg;
import fr.banque.controllers.dto.client.CreateClientRequest;
import fr.banque.controllers.dto.client.CreateClientResponse;
import fr.banque.controllers.dto.compte.CreateCompteRequest;
import fr.banque.controllers.dto.compte.CreateCompteResponse;
import fr.banque.entites.Compte;
import fr.banque.services.CompteService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("comptes")
public class CompteController {
    @Autowired
    private CompteService serCompte;

    @PostMapping
    public ResponseEntity createCompte(@RequestBody CreateCompteRequest request){
        try{
            return ResponseEntity.created(null).body(this.serCompte.saveCompte(request));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ErreurRequestMsg("Le message d'erreur fonctionnelle sera dans ce champ"));
        }
    }
}
