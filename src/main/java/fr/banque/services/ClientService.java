package fr.banque.services;

import fr.banque.entites.Client;
import fr.banque.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClientService {
    @Autowired
    private ClientRepository repClient;

    public void saveClient(Client c){
        this.repClient.save(c);
    }
}
