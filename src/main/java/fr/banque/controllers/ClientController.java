package fr.banque.controllers;

import fr.banque.controllers.dto.ErreurRequestMsg;
import fr.banque.controllers.dto.client.CreateClientRequest;
import fr.banque.controllers.dto.client.CreateClientResponse;
import fr.banque.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity createClient(@RequestBody CreateClientRequest request){
        try{
            return ResponseEntity.created(null).body(this.serClient.saveClient(request));
        } catch (Exception e){
            return ResponseEntity.badRequest().body(new ErreurRequestMsg("Le message d'erreur fonctionnelle sera dans ce champ"));
        }
    }
}
