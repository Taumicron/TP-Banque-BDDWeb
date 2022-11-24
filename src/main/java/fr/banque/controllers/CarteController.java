package fr.banque.controllers;


import fr.banque.services.CarteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CarteController {
    @Autowired
    private CarteService serCarte;


}
