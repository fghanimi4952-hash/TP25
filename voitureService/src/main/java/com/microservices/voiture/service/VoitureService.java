package com.microservices.voiture.service;

import com.microservices.voiture.model.Voiture;
import com.microservices.voiture.repository.VoitureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service métier pour la gestion des voitures
 * 
 * @Service : Indique que cette classe est un service Spring
 * Contient la logique métier de l'application
 */
@Service
public class VoitureService {

    /**
     * Injection du repository pour accéder à la base de données
     */
    @Autowired
    private VoitureRepository voitureRepository;

    /**
     * Récupère toutes les voitures
     * 
     * @return Liste de toutes les voitures
     */
    public List<Voiture> getAllVoitures() {
        return voitureRepository.findAll();
    }

    /**
     * Récupère une voiture par son ID
     * 
     * @param id L'identifiant de la voiture
     * @return La voiture trouvée ou null si non trouvée
     */
    public Voiture getVoitureById(Long id) {
        Optional<Voiture> voiture = voitureRepository.findById(id);
        return voiture.orElse(null);
    }

    /**
     * Récupère toutes les voitures d'un client
     * 
     * @param clientId L'identifiant du client
     * @return Liste des voitures du client
     */
    public List<Voiture> getVoituresByClientId(Long clientId) {
        return voitureRepository.findByClientId(clientId);
    }

    /**
     * Crée une nouvelle voiture
     * 
     * @param voiture La voiture à créer
     * @return La voiture créée avec son ID généré
     */
    public Voiture createVoiture(Voiture voiture) {
        return voitureRepository.save(voiture);
    }

    /**
     * Met à jour une voiture existante
     * 
     * @param id L'identifiant de la voiture à mettre à jour
     * @param voiture Les nouvelles données de la voiture
     * @return La voiture mise à jour ou null si non trouvée
     */
    public Voiture updateVoiture(Long id, Voiture voiture) {
        Optional<Voiture> existingVoiture = voitureRepository.findById(id);
        if (existingVoiture.isPresent()) {
            voiture.setId(id); // S'assurer que l'ID est correct
            return voitureRepository.save(voiture);
        }
        return null;
    }

    /**
     * Supprime une voiture
     * 
     * @param id L'identifiant de la voiture à supprimer
     * @return true si supprimée, false si non trouvée
     */
    public boolean deleteVoiture(Long id) {
        if (voitureRepository.existsById(id)) {
            voitureRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
