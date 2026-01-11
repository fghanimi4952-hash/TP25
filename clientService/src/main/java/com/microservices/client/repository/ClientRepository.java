package com.microservices.client.repository;

import com.microservices.client.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository JPA pour les opérations CRUD sur les clients
 * 
 * JpaRepository fournit automatiquement :
 * - save(), findAll(), findById(), delete(), etc.
 * 
 * @Repository : Indique que cette interface est un composant Spring de type Repository
 */
@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {

    /**
     * Recherche un client par son email
     * Spring Data JPA génère automatiquement l'implémentation de cette méthode
     * 
     * @param email L'email du client à rechercher
     * @return Un Optional contenant le client s'il existe
     */
    Optional<Client> findByEmail(String email);
}
