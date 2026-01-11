package com.microservices.client;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * Application principale du microservice Client
 * 
 * @SpringBootApplication : Combine @Configuration, @EnableAutoConfiguration et @ComponentScan
 * @EnableDiscoveryClient : Active l'enregistrement automatique dans Consul
 */
@SpringBootApplication
@EnableDiscoveryClient // Permet au service de s'enregistrer dans Consul
public class ClientServiceApplication {

    /**
     * Point d'entrée de l'application Spring Boot
     * 
     * @param args Arguments de la ligne de commande
     */
    public static void main(String[] args) {
        SpringApplication.run(ClientServiceApplication.class, args);
        System.out.println("✅ Client Service démarré avec succès!");
    }
}
