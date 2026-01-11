package com.microservices.voiture.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entité JPA représentant une Voiture dans la base de données
 * 
 * @Entity : Indique que cette classe est une entité JPA
 * @Table : Spécifie le nom de la table dans la base de données
 */
@Entity
@Table(name = "voitures")
@Data // Lombok : génère automatiquement getters, setters, toString, equals, hashCode
@NoArgsConstructor // Lombok : génère un constructeur sans arguments
@AllArgsConstructor // Lombok : génère un constructeur avec tous les arguments
public class Voiture {

    /**
     * Identifiant unique de la voiture (clé primaire)
     * @GeneratedValue : La valeur est générée automatiquement par la base de données
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Marque de la voiture (ex: Toyota, BMW, etc.)
     */
    @Column(nullable = false)
    private String marque;

    /**
     * Modèle de la voiture
     */
    @Column(nullable = false)
    private String modele;

    /**
     * Couleur de la voiture
     */
    private String couleur;

    /**
     * Immatriculation de la voiture (unique)
     */
    @Column(unique = true, nullable = false)
    private String immatriculation;

    /**
     * Prix de la voiture
     */
    @Column(nullable = false)
    private Double prix;

    /**
     * ID du client propriétaire (relation avec le service Client)
     */
    private Long clientId;
}
