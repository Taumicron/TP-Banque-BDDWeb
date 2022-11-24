package fr.banque.controllers;

import fr.banque.controllers.dto.BadRequestException;
import fr.banque.controllers.dto.ErreurRequestMsg;
import fr.banque.controllers.dto.virement.CreateVirementRequest;
import fr.banque.services.VirementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("virements")
public class VirementController {
    @Autowired
    private VirementService serVirement;

    @PostMapping
    public ResponseEntity createCompte(@RequestBody CreateVirementRequest request){
        try {
            return ResponseEntity.created(null).body(this.serVirement.saveVirement(request));
        } catch (BadRequestException e){
            return ResponseEntity.badRequest().body(new ErreurRequestMsg("Le message d'erreur fonctionnelle sera dans ce champ"));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(new ErreurRequestMsg("Le message d'erreur fonctionnelle sera dans ce champ"));
        }
    }
}
