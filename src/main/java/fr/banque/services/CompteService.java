package fr.banque.services;

import fr.banque.controllers.dto.BadRequestException;
import fr.banque.controllers.dto.compte.CreateCompteRequest;
import fr.banque.controllers.dto.compte.CreateCompteResponse;
import fr.banque.entites.Client;
import fr.banque.entites.Compte;
import fr.banque.repositories.ClientRepository;
import fr.banque.repositories.CompteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class CompteService {
    @Autowired
    private CompteRepository repCompte;

    @Autowired
    private ClientRepository repClient;

    private String generer_iban(Client c, long numCompte){
        String iban = "FR76";
        iban+= String.valueOf(c.getCodeBanque());
        iban+= String.valueOf(c.getCodeGuichet());
        iban+= String.valueOf(numCompte);
        iban+= String.valueOf(97- ((89*c.getCodeBanque() + 15*c.getCodeGuichet() + 3* numCompte )%97));

        return iban;
    }
    public CreateCompteResponse saveCompte(CreateCompteRequest request) throws BadRequestException {
        long numCompte = new Random().nextLong((long)1E12);

        List<Client> titulaires = this.repClient.findAllById(request.getTitulairesCompte().stream().map(c -> c.getIdClient()).collect(Collectors.toList()));
        if (titulaires.size() == 0 || titulaires.size() > 2){
            throw new BadRequestException();
        }

        Compte toSave = Compte.builder()
                        .intituleCompte(request.getIntituleCompte())
                        .typeCompte(request.getTypeCompte())
                        .titulaires(titulaires)
                        .numeroCompte(numCompte)
                        .iban(generer_iban(titulaires.get(0), numCompte))
                        .build();

        return buildCreateCompteResponse(this.repCompte.save(toSave));
    }

    private CreateCompteResponse buildCreateCompteResponse(Compte c){
        return CreateCompteResponse.builder()
                .intituleCompte(c.getIntituleCompte())
                .typeCompte(c.getTypeCompte())
                .titulaires(c.getTitulaires().stream().map(temp -> CreateCompteResponse.CreateCompteClientResponse.builder().idClient(temp.getIdClient())
                        .build()).collect(Collectors.toList()))
                .iban(c.getIban())
                .dateCreation(LocalDateTime.now().toString())
                .build();
    }
}
