package fr.banque.services;

import fr.banque.entites.Virement;
import fr.banque.repositories.VirementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VirementService {
    @Autowired
    private VirementRepository repVirement;

    public void saveVirement(Virement v){
        this.repVirement.save(v);
    }
}
