package fr.banque.controllers;

import fr.banque.entites.Client;
import fr.banque.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("clients")
public class ClientController {
    @Autowired
    private ClientService serClient;

    @PostMapping
    public String getClient(){
        this.serClient.saveClient(Client.builder().build());
        return "Client Sauvegardé.";
    }
}
