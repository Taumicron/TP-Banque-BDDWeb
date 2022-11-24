package fr.banque.services;

import fr.banque.repositories.CarteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CarteService {
    @Autowired
    private CarteRepository repCarte;

}
