package com.microservices.voiture.controller;

import com.microservices.voiture.model.Voiture;
import com.microservices.voiture.service.VoitureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Contrôleur REST pour gérer les opérations sur les voitures
 * 
 * @RestController : Combine @Controller et @ResponseBody
 * Toutes les méthodes retournent directement des objets JSON
 * 
 * @RequestMapping : Préfixe de chemin pour toutes les routes de ce contrôleur
 */
@RestController
@RequestMapping("/api/voitures")
public class VoitureController {

    /**
     * Injection de dépendance du service Voiture
     * @Autowired : Spring injecte automatiquement une instance de VoitureService
     */
    @Autowired
    private VoitureService voitureService;

    /**
     * Récupère toutes les voitures
     * 
     * GET /api/voitures
     * 
     * @return Liste de toutes les voitures
     */
    @GetMapping
    public ResponseEntity<List<Voiture>> getAllVoitures() {
        List<Voiture> voitures = voitureService.getAllVoitures();
        return ResponseEntity.ok(voitures);
    }

    /**
     * Récupère une voiture par son ID
     * 
     * GET /api/voitures/{id}
     * 
     * @param id L'identifiant de la voiture
     * @return La voiture trouvée ou 404 si non trouvée
     */
    @GetMapping("/{id}")
    public ResponseEntity<Voiture> getVoitureById(@PathVariable Long id) {
        Voiture voiture = voitureService.getVoitureById(id);
        if (voiture != null) {
            return ResponseEntity.ok(voiture);
        }
        return ResponseEntity.notFound().build();
    }

    /**
     * Récupère toutes les voitures d'un client
     * 
     * GET /api/voitures/client/{clientId}
     * 
     * @param clientId L'identifiant du client
     * @return Liste des voitures du client
     */
    @GetMapping("/client/{clientId}")
    public ResponseEntity<List<Voiture>> getVoituresByClientId(@PathVariable Long clientId) {
        List<Voiture> voitures = voitureService.getVoituresByClientId(clientId);
        return ResponseEntity.ok(voitures);
    }

    /**
     * Crée une nouvelle voiture
     * 
     * POST /api/voitures
     * 
     * @param voiture La voiture à créer (reçue en JSON dans le body)
     * @return La voiture créée avec le statut 201 (Created)
     */
    @PostMapping
    public ResponseEntity<Voiture> createVoiture(@RequestBody Voiture voiture) {
        Voiture createdVoiture = voitureService.createVoiture(voiture);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdVoiture);
    }

    /**
     * Met à jour une voiture existante
     * 
     * PUT /api/voitures/{id}
     * 
     * @param id L'identifiant de la voiture à mettre à jour
     * @param voiture Les nouvelles données de la voiture
     * @return La voiture mise à jour ou 404 si non trouvée
     */
    @PutMapping("/{id}")
    public ResponseEntity<Voiture> updateVoiture(@PathVariable Long id, @RequestBody Voiture voiture) {
        Voiture updatedVoiture = voitureService.updateVoiture(id, voiture);
        if (updatedVoiture != null) {
            return ResponseEntity.ok(updatedVoiture);
        }
        return ResponseEntity.notFound().build();
    }

    /**
     * Supprime une voiture
     * 
     * DELETE /api/voitures/{id}
     * 
     * @param id L'identifiant de la voiture à supprimer
     * @return 204 (No Content) si supprimée, 404 si non trouvée
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVoiture(@PathVariable Long id) {
        boolean deleted = voitureService.deleteVoiture(id);
        if (deleted) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    /**
     * Endpoint de santé pour vérifier que le service est actif
     * 
     * GET /api/voitures/health
     * 
     * @return Message de confirmation
     */
    @GetMapping("/health")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("Voiture Service is running!");
    }
}
