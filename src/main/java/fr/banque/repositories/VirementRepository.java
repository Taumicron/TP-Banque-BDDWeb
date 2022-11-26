package fr.banque.repositories;

import fr.banque.entites.Virement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VirementRepository extends JpaRepository<Virement, Integer> {
    List<Virement> findAllByCompte_Iban(String iban);
}
