package fr.banque.repositories;

import fr.banque.entites.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClientRepository extends JpaRepository<Client, Integer> {
    public List<Client> findAllByNomAndPrenom(String nom, String prenom);
}
