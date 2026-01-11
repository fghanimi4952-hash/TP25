package com.microservices.voiture.repository;

import com.microservices.voiture.model.Voiture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository JPA pour les opérations CRUD sur les voitures
 * 
 * JpaRepository fournit automatiquement :
 * - save(), findAll(), findById(), delete(), etc.
 * 
 * @Repository : Indique que cette interface est un composant Spring de type Repository
 */
@Repository
public interface VoitureRepository extends JpaRepository<Voiture, Long> {

    /**
     * Recherche une voiture par son immatriculation
     * Spring Data JPA génère automatiquement l'implémentation de cette méthode
     * 
     * @param immatriculation L'immatriculation de la voiture à rechercher
     * @return Un Optional contenant la voiture si elle existe
     */
    Optional<Voiture> findByImmatriculation(String immatriculation);

    /**
     * Recherche toutes les voitures d'un client
     * 
     * @param clientId L'identifiant du client
     * @return Liste des voitures du client
     */
    List<Voiture> findByClientId(Long clientId);
}
