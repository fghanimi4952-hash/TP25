package com.microservices.voiture;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * Application principale du microservice Voiture
 * 
 * @SpringBootApplication : Combine @Configuration, @EnableAutoConfiguration et @ComponentScan
 * @EnableDiscoveryClient : Active l'enregistrement automatique dans Consul
 */
@SpringBootApplication
@EnableDiscoveryClient // Permet au service de s'enregistrer dans Consul
public class VoitureServiceApplication {

    /**
     * Point d'entrée de l'application Spring Boot
     * 
     * @param args Arguments de la ligne de commande
     */
    public static void main(String[] args) {
        SpringApplication.run(VoitureServiceApplication.class, args);
        System.out.println("✅ Voiture Service démarré avec succès!");
    }
}
