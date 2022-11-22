package fr.banque.services;

import fr.banque.controllers.dto.client.CreateClientRequest;
import fr.banque.controllers.dto.client.CreateClientResponse;
import fr.banque.entites.Client;
import fr.banque.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ClientService {
    @Autowired
    private ClientRepository repClient;


    public CreateClientResponse saveClient(CreateClientRequest c){
        Client toCreate = this.repClient.save(Client.builder().prenom(c.getPrenom())
                .nom(c.getNom())
                .dateNaissance(c.getDateNaissance())
                .telephone(c.getTelephone())
                .adressePostale(c.getAdressePostale())
                .dateCreation(LocalDateTime.now().toString())
                .build());

        return buildCreateTodoResponse(this.repClient.save(toCreate));

    }

    private CreateClientResponse buildCreateTodoResponse(Client c) {
        return CreateClientResponse.builder()
                .idClient(c.getIdClient())
                .prenom(c.getPrenom())
                .nom(c.getNom())
                .dateNaissance(c.getDateNaissance())
                .telephone(c.getTelephone())
                .adressePostale(c.getAdressePostale())
                .dateCreation(c.getDateCreation())
                .build();
    }
}
