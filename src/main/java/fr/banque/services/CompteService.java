package fr.banque.services;

import fr.banque.controllers.dto.BadRequestException;
import fr.banque.controllers.dto.NotFoundException;
import fr.banque.controllers.dto.carte.CreateCarteRequest;
import fr.banque.controllers.dto.carte.CreateCarteResponse;
import fr.banque.controllers.dto.compte.CreateCompteRequest;
import fr.banque.controllers.dto.compte.CreateCompteResponse;
import fr.banque.controllers.dto.compte.GetCartesCompteResponse;
import fr.banque.controllers.dto.compte.GetComptesResponse;
import fr.banque.entites.*;
import fr.banque.repositories.*;
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
    @Autowired
    private TransactionCarteRepository repTranCarte;
    @Autowired
    private VirementRepository repVirement;
    private String generer_numero_compte(){
        String numC = "";
        String nb = "0123456789";
        Random r = new Random();
        for(int i = 0; i < 11; i++){
                numC+= nb.charAt(r.nextInt(nb.length()));
        }
        return numC;
    }

    private String generer_iban(Client c, String numCompte){
        String iban = "FR76";
        iban+= String.valueOf(c.getCodeBanque());
        iban+= String.valueOf(c.getCodeGuichet());
        iban+= String.valueOf(numCompte);
        iban+= String.valueOf(97- ((89L *c.getCodeBanque() + 15L *c.getCodeGuichet() + 3L* Long.valueOf(numCompte) )%97));

        return iban;
    }
    public CreateCompteResponse saveCompte(CreateCompteRequest request) throws BadRequestException {
        if (request.getIntituleCompte() == null || request.getTypeCompte() == null || request.getTitulairesCompte() == null){
            throw new BadRequestException("Mauvais format de requête.");
        }

        String numCompte = generer_numero_compte();

        List<Client> titulaires = this.repClient.findAllById(request.getTitulairesCompte().stream().map(c -> c.getIdClient()).collect(Collectors.toList()));
        if (titulaires.size() != request.getTitulairesCompte().size()){
            throw new BadRequestException("Client(s) non trouvé(s).");
        }

        if (titulaires.size() == 0 || titulaires.size() > 2){
            throw new BadRequestException("Le compte doit avoir 1 ou 2 titulaires.");
        }

        Compte toSave = Compte.builder()
                        .intituleCompte(request.getIntituleCompte())
                        .typeCompte(request.getTypeCompte())
                        .solde(0.0)
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
        if (!iban.matches("^FR\\d{12}[0-9A-Z]{11}\\d{2}")){
            throw new BadRequestException("Le format de l'iban n'est pas correct.");
        } else if (c.getTitulaireCarte() == null){
            throw new BadRequestException("Titulaire de carte non fourni.");
        } else if (c.getCode() == null){
            throw new BadRequestException("Code non fourni.");
        }

        Optional<Client> titu = repClient.findById(c.getTitulaireCarte());
        Optional<Compte> compteTitu = repCompte.findById(iban);
        if (titu.isEmpty()){
            throw new BadRequestException("Le titulaire de la carte n'a pas été trouvé.");
        } else if (compteTitu.isEmpty()){
            throw new BadRequestException("Le compte avec l'iban spécifié n'a pas été trouvé.");
        } else if (compteTitu.get().getCartes().size() > 1){
            throw new BadRequestException("Ce compte ne peut pas avoir de carte supplémentaire.");
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

    public List<GetComptesResponse> getComptesClient(String str) throws NotFoundException, BadRequestException {
        Integer id = null;
        try {
            id = Integer.valueOf(str);
        } catch(Exception e){
            throw new BadRequestException("L'id n'est pas sous format numérique.");
        }
        if (this.repClient.findById(id).isEmpty()){
            throw new BadRequestException("Aucun client trouvé avec cet Id.");
        }
        List<Compte> comptes = this.repCompte.findAllByTitulaires_IdClient(id);
        if (comptes.isEmpty()) {
            throw new NotFoundException("Ce client n'a pas de compte.");
        }
        List<GetComptesResponse> toReturn = new ArrayList<GetComptesResponse>();

        for (Compte c : comptes) {
            List<GetComptesResponse.GetComptesTitulaireResponse> titulairesId = new ArrayList<>();
            for(Client titulaire: c.getTitulaires())
                titulairesId.add(GetComptesResponse.GetComptesTitulaireResponse.builder().idClient(titulaire.getIdClient().toString()).build());

            List<GetComptesResponse.GetComptesTransactionResponse> transactionsId = new ArrayList<GetComptesResponse.GetComptesTransactionResponse>();

            for (TransactionCarte t : this.repTranCarte.findAllByCompte_Iban(c.getIban())) {
                transactionsId.add(GetComptesResponse.GetComptesTransactionResponse.builder()
                        .id(t.getIdTransaction().toString())
                        .montant(Double.valueOf(Math.abs(t.getMontant())).toString())
                        .typeTransaction(t.getMontant() > 0 ? "DEBIT" : "CREDIT")
                        .typeSource("CB")
                        .idSource(t.getCompte().getIban())
                        .build());
            }
            for (Virement v : this.repVirement.findAllByCompte_Iban(c.getIban())) {
                transactionsId.add(GetComptesResponse.GetComptesTransactionResponse.builder()
                        .id(v.getIdTransaction().toString())
                        .montant(Double.valueOf(Math.abs(v.getMontant())).toString())
                        .typeTransaction(v.getMontant() > 0 ? "DEBIT" : "CREDIT")
                        .typeSource("VIREMENT")
                        .idSource(v.getMontant() > 0 ? v.getCompte().getIban() : v.getCompteCible().getIban())
                        .build());
            }


        toReturn.add(GetComptesResponse.builder()
                .iban(c.getIban())
                .solde(c.getSolde().toString())
                .intituleCompte(c.getIntituleCompte())
                .typeCompte(c.getTypeCompte())
                .titulairesCompte(titulairesId)
                .transactions(transactionsId)
                .build());
    }

        return toReturn;
    }

    public List<GetCartesCompteResponse> getCartesCompte(String iban) throws NotFoundException, BadRequestException, Exception {
        if (!iban.matches("^FR\\d{12}[0-9A-Z]{11}\\d{2}")){
            throw new BadRequestException("Le format de l'iban n'est pas correct.");
        }
        if (this.repCompte.findById(iban).isEmpty()){
            throw new BadRequestException("Aucun compte avec cet iban.");
        }
        List<Carte> listCartes = this.repCarte.findAllByCompteCarte_Iban(iban);
        if (listCartes.isEmpty()){
            throw new NotFoundException("Ce compte n'a pas de carte.");
        }
        List<GetCartesCompteResponse> toReturn = new ArrayList<>();
        for (Carte c :listCartes) {
            List<GetCartesCompteResponse.GetCartesCompteTitulaire> titulaire = new ArrayList<>();
            titulaire.add(GetCartesCompteResponse.GetCartesCompteTitulaire.builder().idClient(c.getTitulaire().getIdClient().toString()).build());
            toReturn.add(GetCartesCompteResponse.builder()
                            .numeroCarte(c.getNumCarte())
                            .dateExpiration(c.getDateExp())
                            .titulaireCarte(titulaire)
                    .build());
        }

        return toReturn ;
    }
}

