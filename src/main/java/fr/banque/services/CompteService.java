package fr.banque.services;

import fr.banque.controllers.dto.BadRequestException;
import fr.banque.controllers.dto.carte.CreateCarteRequest;
import fr.banque.controllers.dto.carte.CreateCarteResponse;
import fr.banque.controllers.dto.compte.CreateCompteRequest;
import fr.banque.controllers.dto.compte.CreateCompteResponse;
import fr.banque.entites.Carte;
import fr.banque.entites.Client;
import fr.banque.entites.Compte;
import fr.banque.repositories.CarteRepository;
import fr.banque.repositories.ClientRepository;
import fr.banque.repositories.CompteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class CompteService {
    @Autowired
    private CompteRepository repCompte;

    @Autowired
    private ClientRepository repClient;
    @Autowired
    private CarteRepository repCarte;

    private String generer_iban(Client c, long numCompte){
        String iban = "FR76";
        iban+= String.valueOf(c.getCodeBanque());
        iban+= String.valueOf(c.getCodeGuichet());
        iban+= String.valueOf(numCompte);
        iban+= String.valueOf(97- ((89L *c.getCodeBanque() + 15L *c.getCodeGuichet() + 3* numCompte )%97));

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


    //Hash le mdp passé en paramètres. Source : https://howtodoinjava.com/java/java-security/how-to-generate-secure-password-hash-md5-sha-pbkdf2-bcrypt-examples/
    private String hashMdp(String code) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(code.getBytes());
        byte[] bytes = md.digest();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
        }
        return sb.toString();

    }

    private String generer_num_carte(){
        String num = "";
        String temp = "0123456789";
        Random r = new Random();
        for(int i = 0; i < 16;i++){
            num+=temp.charAt(r.nextInt(temp.length()));
        }
        return num;
    }


    public CreateCarteResponse saveCarte(CreateCarteRequest c, String iban) throws BadRequestException, NoSuchAlgorithmException {
        Optional<Client> titu = repClient.findById(c.getTitulaireCarte());
        Optional<Compte> compteTitu = repCompte.findById(iban);
        if (titu.isEmpty() || compteTitu.isEmpty() || compteTitu.get().getCartes().size() > 1){
            throw new BadRequestException();
        }

        Carte toCreate = Carte.builder().titulaire(titu.get())
                .mdp(hashMdp(c.getCode()))
                .dateExp((LocalDateTime.now().plusYears(2)).toString()) //Carte valide 2 ans à compter de son inscription en BDD.
                .compteCarte(compteTitu.get())
                .numCarte(generer_num_carte())
                .build();
        return buildCreateCarteResponse(this.repCarte.save(toCreate));
    }

    private CreateCarteResponse buildCreateCarteResponse(Carte c){
        return CreateCarteResponse.builder()
                .titulaireCarte(c.getTitulaire().getIdClient())
                .numCarte(c.getNumCarte())
                .dateExpiration(c.getDateExp())
                .build();
    }

}

