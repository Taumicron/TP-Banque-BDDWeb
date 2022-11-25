package fr.banque.controllers;

import fr.banque.controllers.dto.BadRequestException;
import fr.banque.controllers.dto.ErreurRequestMsg;
import fr.banque.controllers.dto.NotFoundException;
import fr.banque.controllers.dto.carte.CreateCarteRequest;
import fr.banque.controllers.dto.compte.CreateCompteRequest;
import fr.banque.controllers.dto.transactioncarte.CreateTransactionCarteRequest;
import fr.banque.services.CompteService;
import fr.banque.services.TransactionCarteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("comptes")
public class CompteController {
    @Autowired
    private CompteService serCompte;

    @Autowired
    private TransactionCarteService serTraCarte;
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
            return ResponseEntity.internalServerError().body(new ErreurRequestMsg("Le message d'erreur fonctionnelle sera dans ce champ"));
        }
    }

    @PostMapping("/{iban}/cartes/{numeroCarte}/paiement")
    public ResponseEntity createTransactionCarte(@RequestBody CreateTransactionCarteRequest request, @PathVariable("iban") String iban, @PathVariable("numeroCarte") String numeroCarte){
        try {
            return ResponseEntity.created(null).body(this.serTraCarte.saveTransactionCarte(request, iban, numeroCarte));
        } catch (BadRequestException e) {
            return ResponseEntity.badRequest().body(new ErreurRequestMsg("Le message d'erreur fonctionnelle sera dans ce champ"));
        } catch (NotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErreurRequestMsg("Le message d'erreur fonctionnelle sera dans ce champ"));
        } catch (Exception e){
            return ResponseEntity.internalServerError().body(new ErreurRequestMsg("Le message d'erreur fonctionnelle sera dans ce champ"));
        }
    }
}
