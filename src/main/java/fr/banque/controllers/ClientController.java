package fr.banque.controllers;

import fr.banque.controllers.dto.ErreurRequestMsg;
import fr.banque.controllers.dto.NotFoundException;
import fr.banque.controllers.dto.client.CreateClientRequest;
import fr.banque.controllers.dto.BadRequestException;
import fr.banque.controllers.dto.client.GetClientResponse;
import fr.banque.controllers.dto.client.PutClientRequest;
import fr.banque.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("clients")
public class ClientController {
    @Autowired
    private ClientService serClient;

    @PostMapping
    public ResponseEntity createClient(@RequestBody CreateClientRequest request){
        try {
            return ResponseEntity.created(null).body(this.serClient.saveClient(request));
        } catch (BadRequestException e){
            return ResponseEntity.badRequest().body(new ErreurRequestMsg(e.getMessage()));
        } catch (Exception e){
            return ResponseEntity.internalServerError().body(new ErreurRequestMsg(e.getMessage()));
        }
    }

    @GetMapping
    public ResponseEntity getClient(@RequestParam("nom") String nom, @RequestParam("prenom") String prenom){
        List<GetClientResponse> toReturn = null;
        try {
            toReturn = this.serClient.getClient(nom, prenom);
        } catch (BadRequestException e){
            return ResponseEntity.badRequest().body(new ErreurRequestMsg(e.getMessage()));
        } catch (NotFoundException e){
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (Exception e){
            return ResponseEntity.internalServerError().body(new ErreurRequestMsg("Erreur interne du serveur."));

        }
        return ResponseEntity.ok(toReturn);
    }

    @PutMapping
    public ResponseEntity modifClient(@RequestBody PutClientRequest request){
        try {
            return ResponseEntity.ok().body(this.serClient.modifClient(request));
        } catch (BadRequestException e){
            return ResponseEntity.badRequest().body(new ErreurRequestMsg(e.getMessage()));
        } catch (Exception e){
            return ResponseEntity.internalServerError().body(new ErreurRequestMsg("Erreur interne du serveur."));
        }
    }
}
