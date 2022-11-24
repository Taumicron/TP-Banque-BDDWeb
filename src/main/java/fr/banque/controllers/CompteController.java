package fr.banque.controllers;

import fr.banque.controllers.dto.BadRequestException;
import fr.banque.controllers.dto.ErreurRequestMsg;
import fr.banque.controllers.dto.carte.CreateCarteRequest;
import fr.banque.controllers.dto.client.CreateClientRequest;
import fr.banque.controllers.dto.client.CreateClientResponse;
import fr.banque.controllers.dto.compte.CreateCompteRequest;
import fr.banque.controllers.dto.compte.CreateCompteResponse;
import fr.banque.entites.Compte;
import fr.banque.services.CompteService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("comptes")
public class CompteController {
    @Autowired
    private CompteService serCompte;

    @PostMapping
    public ResponseEntity createCompte(@RequestBody CreateCompteRequest request){
        try {
            return ResponseEntity.created(null).body(this.serCompte.saveCompte(request));
        } catch (BadRequestException e){
            return ResponseEntity.badRequest().body(new ErreurRequestMsg("Le message d'erreur fonctionnelle sera dans ce champ"));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(new ErreurRequestMsg("Le message d'erreur fonctionnelle sera dans ce champ"));
        }
    }

    @PostMapping("/{iban}/cartes")
    public ResponseEntity createCarte(@RequestBody CreateCarteRequest request, @PathVariable("iban") String iban){
        try {
            return ResponseEntity.created(null).body(this.serCompte.saveCarte(request, iban));
        } catch (BadRequestException e){
            return ResponseEntity.badRequest().body(new ErreurRequestMsg("Le message d'erreur fonctionnelle sera dans ce champ"));
        } catch (Exception e){
            return ResponseEntity.badRequest().body(new ErreurRequestMsg("Le message d'erreur fonctionnelle sera dans ce champ"));
        }
    }
}
