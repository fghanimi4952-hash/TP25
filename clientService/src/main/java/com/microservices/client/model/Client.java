package com.microservices.client.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entité JPA représentant un Client dans la base de données
 * 
 * @Entity : Indique que cette classe est une entité JPA
 * @Table : Spécifie le nom de la table dans la base de données
 */
@Entity
@Table(name = "clients")
@Data // Lombok : génère automatiquement getters, setters, toString, equals, hashCode
@NoArgsConstructor // Lombok : génère un constructeur sans arguments
@AllArgsConstructor // Lombok : génère un constructeur avec tous les arguments
public class Client {

    /**
     * Identifiant unique du client (clé primaire)
     * @GeneratedValue : La valeur est générée automatiquement par la base de données
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Nom du client
     */
    @Column(nullable = false)
    private String nom;

    /**
     * Prénom du client
     */
    @Column(nullable = false)
    private String prenom;

    /**
     * Email du client
     */
    @Column(unique = true, nullable = false)
    private String email;

    /**
     * Adresse du client
     */
    private String adresse;
}
