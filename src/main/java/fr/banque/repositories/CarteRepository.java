package fr.banque.repositories;

import fr.banque.entites.Carte;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CarteRepository extends JpaRepository<Carte, String> {
    public List<Carte> findAllByCompteCarte_Iban(String iban);

}
