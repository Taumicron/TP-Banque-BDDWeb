package fr.banque.controllers;

import fr.banque.entites.Virement;
import fr.banque.services.VirementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("virements")
public class VirementController {
    @Autowired
    private VirementService serVirement;

    @PostMapping
    public String getVirement(){
        this.serVirement.saveVirement(Virement.builder().build());
        return "Virement sauvegardé.";
    }
}
