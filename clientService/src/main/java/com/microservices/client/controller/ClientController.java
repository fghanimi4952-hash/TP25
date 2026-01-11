package com.microservices.client.controller;

import com.microservices.client.model.Client;
import com.microservices.client.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Contrôleur REST pour gérer les opérations sur les clients
 * 
 * @RestController : Combine @Controller et @ResponseBody
 * Toutes les méthodes retournent directement des objets JSON
 * 
 * @RequestMapping : Préfixe de chemin pour toutes les routes de ce contrôleur
 */
@RestController
@RequestMapping("/api/clients")
public class ClientController {

    /**
     * Injection de dépendance du service Client
     * @Autowired : Spring injecte automatiquement une instance de ClientService
     */
    @Autowired
    private ClientService clientService;

    /**
     * Récupère tous les clients
     * 
     * GET /api/clients
     * 
     * @return Liste de tous les clients
     */
    @GetMapping
    public ResponseEntity<List<Client>> getAllClients() {
        List<Client> clients = clientService.getAllClients();
        return ResponseEntity.ok(clients);
    }

    /**
     * Récupère un client par son ID
     * 
     * GET /api/clients/{id}
     * 
     * @param id L'identifiant du client
     * @return Le client trouvé ou 404 si non trouvé
     */
    @GetMapping("/{id}")
    public ResponseEntity<Client> getClientById(@PathVariable Long id) {
        Client client = clientService.getClientById(id);
        if (client != null) {
            return ResponseEntity.ok(client);
        }
        return ResponseEntity.notFound().build();
    }

    /**
     * Crée un nouveau client
     * 
     * POST /api/clients
     * 
     * @param client Le client à créer (reçu en JSON dans le body)
     * @return Le client créé avec le statut 201 (Created)
     */
    @PostMapping
    public ResponseEntity<Client> createClient(@RequestBody Client client) {
        Client createdClient = clientService.createClient(client);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdClient);
    }

    /**
     * Met à jour un client existant
     * 
     * PUT /api/clients/{id}
     * 
     * @param id L'identifiant du client à mettre à jour
     * @param client Les nouvelles données du client
     * @return Le client mis à jour ou 404 si non trouvé
     */
    @PutMapping("/{id}")
    public ResponseEntity<Client> updateClient(@PathVariable Long id, @RequestBody Client client) {
        Client updatedClient = clientService.updateClient(id, client);
        if (updatedClient != null) {
            return ResponseEntity.ok(updatedClient);
        }
        return ResponseEntity.notFound().build();
    }

    /**
     * Supprime un client
     * 
     * DELETE /api/clients/{id}
     * 
     * @param id L'identifiant du client à supprimer
     * @return 204 (No Content) si supprimé, 404 si non trouvé
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteClient(@PathVariable Long id) {
        boolean deleted = clientService.deleteClient(id);
        if (deleted) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    /**
     * Endpoint de santé pour vérifier que le service est actif
     * 
     * GET /api/clients/health
     * 
     * @return Message de confirmation
     */
    @GetMapping("/health")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("Client Service is running!");
    }
}
