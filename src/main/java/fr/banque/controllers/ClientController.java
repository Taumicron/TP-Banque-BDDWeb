package fr.banque.controllers;

import fr.banque.controllers.dto.client.CreateClientRequest;
import fr.banque.controllers.dto.client.CreateClientResponse;
import fr.banque.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("clients")
public class ClientController {
    @Autowired
    private ClientService serClient;

    @PostMapping
    public CreateClientResponse createClient(@RequestBody CreateClientRequest request){
        return this.serClient.saveClient(request);
    }
}
