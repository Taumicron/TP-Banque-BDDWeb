package fr.banque.services;

import fr.banque.controllers.dto.BadRequestException;
import fr.banque.controllers.dto.NotFoundException;
import fr.banque.controllers.dto.client.*;
import fr.banque.entites.Client;
import fr.banque.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ClientService {
    @Autowired
    private ClientRepository repClient;


    public CreateClientResponse saveClient(CreateClientRequest c) throws BadRequestException {
        if (c.getPrenom() == null){
            throw new BadRequestException("Aucun prénom renseigné.");
        } else if (c.getNom() == null){
            throw new BadRequestException("Aucun nom renseigné.");
        } else if (c.getDateNaissance() == null){
            throw new BadRequestException("Aucune date de naissance renseignée.");
        } else if (c.getTelephone() == null){
            throw new BadRequestException("Aucun numéro de téléphone renseigné.");
        } else if (c.getAdressePostale() == null){
            throw new BadRequestException("Aucune adresse postale renseignée.");
        } else if (c.getPrenom().matches(".*\\d.*")){
            throw new BadRequestException("Le prénom n'est pas au bon format.");
        } else if (c.getNom().matches(".*\\d.*")){
            throw new BadRequestException("Le nom n'est pas au bon format.");
        } else if (!c.getTelephone().matches("(0|\\+33|0033)[1-9][0-9]{8}")){
            throw new BadRequestException("Le numéro de téléphone n'est pas au bon format.");
        }

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
        if (nom.matches(".*\\d.*"))
            throw new BadRequestException("Le nom ne peut pas contenir de chiffres.");

        if (prenom.matches(".*\\d.*"))
            throw new BadRequestException("Le prénom ne peut pas contenir de chiffres.");
        List<Client> listClients = this.repClient.findAllByNomAndPrenom(nom, prenom);
        if (listClients.isEmpty()){
            throw new NotFoundException("Aucun client trouvé avec les paramètres fournis.");
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

    public PutClientResponse modifClient(PutClientRequest request) throws BadRequestException {
        if (request.getId() == null){
            throw new BadRequestException("Aucun id renseigné.");
        } else if (request.getPrenom() == null){
            throw new BadRequestException("Aucun prénom renseigné.");
        } else if (request.getNom() == null){
            throw new BadRequestException("Aucun nom renseigné.");
        } else if (request.getDateNaissance() == null){
            throw new BadRequestException("Aucune date de naissance renseignée.");
        } else if (request.getTelephone() == null){
            throw new BadRequestException("Aucun numéro de téléphone renseigné.");
        } else if (request.getAdressePostale() == null){
            throw new BadRequestException("Aucune adresse postale renseignée.");
        } else if (request.getPrenom().matches(".*\\d.*")){
            throw new BadRequestException("Le prénom n'est pas au bon format.");
        } else if (request.getNom().matches(".*\\d.*")){
            throw new BadRequestException("Le nom n'est pas au bon format.");
        } else if (!request.getTelephone().matches("(0|\\+33|0033)[1-9][0-9]{8}")){
            throw new BadRequestException("Le numéro de téléphone n'est pas au bon format.");
        }

        Optional<Client> clientOpt = this.repClient.findById(request.getId());
        if (clientOpt.isEmpty()){
            throw new BadRequestException("Aucun client trouvé avec cet id.");
        }

        Client c = clientOpt.get();
        Client toSave = Client.builder()
                .idClient(c.getIdClient())
                .nom(request.getNom())
                .prenom(request.getPrenom())
                .dateNaissance(request.getDateNaissance())
                .telephone(request.getTelephone())
                .adressePostale(request.getAdressePostale())
                .dateCreation(c.getDateCreation())
                .comptes(c.getComptes())
                .cartes(c.getCartes())
                .codeBanque(c.getCodeBanque())
                .codeGuichet(c.getCodeGuichet())
                .build();

        return buildPutClientResponse(this.repClient.save(toSave));
    }

    public PutClientResponse buildPutClientResponse(Client c){
        return PutClientResponse.builder()
                .id(c.getIdClient())
                .prenom(c.getPrenom())
                .nom(c.getNom())
                .dateNaissance(c.getDateNaissance())
                .telephone(c.getTelephone())
                .adressePostale(c.getAdressePostale())
                .dateModification(LocalDateTime.now().toString())
                .build();
    }
}
