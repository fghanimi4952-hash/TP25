package com.microservices.client.service;

import com.microservices.client.model.Client;
import com.microservices.client.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service métier pour la gestion des clients
 * 
 * @Service : Indique que cette classe est un service Spring
 * Contient la logique métier de l'application
 */
@Service
public class ClientService {

    /**
     * Injection du repository pour accéder à la base de données
     */
    @Autowired
    private ClientRepository clientRepository;

    /**
     * Récupère tous les clients
     * 
     * @return Liste de tous les clients
     */
    public List<Client> getAllClients() {
        return clientRepository.findAll();
    }

    /**
     * Récupère un client par son ID
     * 
     * @param id L'identifiant du client
     * @return Le client trouvé ou null si non trouvé
     */
    public Client getClientById(Long id) {
        Optional<Client> client = clientRepository.findById(id);
        return client.orElse(null);
    }

    /**
     * Crée un nouveau client
     * 
     * @param client Le client à créer
     * @return Le client créé avec son ID généré
     */
    public Client createClient(Client client) {
        return clientRepository.save(client);
    }

    /**
     * Met à jour un client existant
     * 
     * @param id L'identifiant du client à mettre à jour
     * @param client Les nouvelles données du client
     * @return Le client mis à jour ou null si non trouvé
     */
    public Client updateClient(Long id, Client client) {
        Optional<Client> existingClient = clientRepository.findById(id);
        if (existingClient.isPresent()) {
            client.setId(id); // S'assurer que l'ID est correct
            return clientRepository.save(client);
        }
        return null;
    }

    /**
     * Supprime un client
     * 
     * @param id L'identifiant du client à supprimer
     * @return true si supprimé, false si non trouvé
     */
    public boolean deleteClient(Long id) {
        if (clientRepository.existsById(id)) {
            clientRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
