package fr.banque.services;

import fr.banque.controllers.dto.BadRequestException;
import fr.banque.controllers.dto.NotFoundException;
import fr.banque.controllers.dto.client.CreateClientRequest;
import fr.banque.controllers.dto.client.CreateClientResponse;
import fr.banque.controllers.dto.client.GetClientResponse;
import fr.banque.entites.Client;
import fr.banque.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class ClientService {
    @Autowired
    private ClientRepository repClient;


    public CreateClientResponse saveClient(CreateClientRequest c) throws BadRequestException {
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

    public List<GetClientResponse> getClient(String nom, String prenom) throws BadRequestException, NotFoundException {
        for(char c : nom.toCharArray())
            if (Character.isDigit(c))
                throw new BadRequestException();

        for(char c : prenom.toCharArray())
            if (Character.isDigit(c))
                throw new BadRequestException();
        List<Client> listClients = this.repClient.findAllByNomAndPrenom(nom, prenom);
        if (listClients.isEmpty()){
            throw new NotFoundException();
        }
        List<GetClientResponse> toReturn = new ArrayList<GetClientResponse>();
        for (Client c: listClients) {
            toReturn.add(GetClientResponse.builder()
                            .id(c.getIdClient())
                            .prenom(c.getPrenom())
                            .nom(c.getNom())
                            .dateNaissance(c.getDateNaissance())
                            .telephone(c.getTelephone())
                            .adressePostale(c.getAdressePostale())
                            .dateCreation(c.getDateCreation())
                    .build());
        }

        return toReturn;
    }
}
