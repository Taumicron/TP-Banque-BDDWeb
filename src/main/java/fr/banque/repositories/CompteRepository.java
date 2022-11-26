package fr.banque.repositories;

import fr.banque.entites.Compte;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CompteRepository extends JpaRepository<Compte, String> {
    //@Query(value = "SELECT distinct * FROM Compte WHERE Compte.iban in (select DISTINCT iban from (compte inner join client) cc WHERE ID_CLIENT=%id%)")
    //public List<Compte> findAllClientsCompte(Integer id);

    public List<Compte> findAllByTitulaires_IdClient(Integer idClient);

}
