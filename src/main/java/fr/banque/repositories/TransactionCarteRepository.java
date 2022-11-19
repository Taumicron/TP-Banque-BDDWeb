package fr.banque.repositories;

import fr.banque.entites.TransactionCarte;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionCarteRepository extends JpaRepository<TransactionCarte, Integer> {
}
