package com.microservices.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * Application principale du service Gateway
 * 
 * Le Gateway sert de point d'entrée unique pour tous les microservices.
 * Il route les requêtes vers les services appropriés en utilisant Consul pour la découverte.
 * 
 * @SpringBootApplication : Combine @Configuration, @EnableAutoConfiguration et @ComponentScan
 * @EnableDiscoveryClient : Active la découverte de services via Consul
 */
@SpringBootApplication
@EnableDiscoveryClient // Permet au Gateway de découvrir les services via Consul
public class GatewayServiceApplication {

    /**
     * Point d'entrée de l'application Spring Boot Gateway
     * 
     * @param args Arguments de la ligne de commande
     */
    public static void main(String[] args) {
        SpringApplication.run(GatewayServiceApplication.class, args);
        System.out.println("✅ Gateway Service démarré avec succès!");
        System.out.println("📍 Gateway disponible sur: http://localhost:8888");
    }
}
