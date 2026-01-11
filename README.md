# TP 25 : Conteneurisation des Microservices avec Docker + Découverte de services via Consul


##  Objectifs du lab

À la fin de ce lab, il sera possible de :

-  Expliquer pourquoi Docker est utile en microservices
-  Créer un Dockerfile multi-stage pour un microservice Spring Boot
-  Orchestrer plusieurs conteneurs (MySQL, Consul, Gateway, Client, Voiture, phpMyAdmin) via Docker Compose
-  Comprendre la différence entre localhost (machine hôte) et les noms DNS Docker (mysql, consul)
-  Vérifier l'enregistrement automatique des services dans Consul
-  Diagnostiquer les problèmes classiques (ports, réseau, base, dépendances)



##  Prérequis

### Outils nécessaires

Avant de commencer, assurez-vous d'avoir installé :

- **Docker** (version 20.10 ou supérieure)
- **Docker Compose** (version 2.0 ou supérieure)
- **Maven** (optionnel, pour compiler localement)

### Vérifications

Exécutez ces commandes dans un terminal pour vérifier vos installations :

```bash
# Vérifier Docker
docker --version

# Vérifier Docker Compose
docker compose version

# Vérifier Maven (optionnel)
mvn -version
```

### Ports à vérifier

Assurez-vous que ces ports ne sont pas déjà utilisés :

- **8500** (Consul UI)
- **3306** (MySQL)
- **8081** (phpMyAdmin)
- **8888** (Gateway)
- **8088** (Client Service)
- **8089** (Voiture Service)

>  **Remarque** : Si un port est déjà utilisé, Docker Compose échouera avec un message "port is already allocated".

---

##  Structure du projet


<img width="495" height="723" alt="Capture d’écran 2026-01-11 à 14 06 40" src="https://github.com/user-attachments/assets/2e5e2864-66ff-465e-93f8-436304286896" />






##  Architecture

### Vue d'ensemble

```
┌─────────────────────────────────────────────────────────────┐
│                    Client (Navigateur)                      │
└──────────────────────────┬──────────────────────────────────┘
                           │
                           │ HTTP
                           ▼
┌─────────────────────────────────────────────────────────────┐
│              Gateway Service (Port 8888)                     │
│         Spring Cloud Gateway + Consul Discovery            │
└──────┬───────────────────────────────┬──────────────────────┘
       │                               │
       │ Route: /api/clients/**       │ Route: /api/voitures/**
       │                               │
       ▼                               ▼
┌──────────────────┐          ┌──────────────────┐
│  Client Service  │          │ Voiture Service   │
│   (Port 8088)    │          │   (Port 8089)     │
└────────┬─────────┘          └────────┬──────────┘
         │                             │
         │                             │
         └──────────────┬──────────────┘
                        │
                        ▼
         ┌──────────────────────────┐
         │   MySQL (Port 3306)      │
         │  - Micro_ClientDB        │
         │  - Micro_VoitureDB       │
         └──────────────────────────┘
                        │
         ┌──────────────┴──────────────┐
         │                             │
         ▼                             ▼
┌──────────────────┐          ┌──────────────────┐
│  Consul (8500)   │          │  phpMyAdmin      │
│  Service Discovery│          │   (Port 8081)    │
└──────────────────┘          └──────────────────┘
```

### Services

1. **MySQL** : Base de données pour stocker les données des clients et voitures
2. **Consul** : Service de découverte pour enregistrer et localiser les microservices
3. **Gateway Service** : Point d'entrée unique qui route les requêtes vers les services appropriés
4. **Client Service** : Microservice de gestion des clients (CRUD)
5. **Voiture Service** : Microservice de gestion des voitures (CRUD)
6. **phpMyAdmin** : Interface web pour gérer MySQL

---

## 🚀 Guide d'installation et d'utilisation

### Étape 1 : Vérifier la structure

Assurez-vous d'être dans le dossier racine du projet :

```bash
cd "TP 25"
ls -la
```

Vous devriez voir :
- `docker-compose.yml`
- `clientService/`
- `voitureService/`
- `gatewayService/`

### Étape 2 : Lancer toute l'architecture

Depuis le dossier racine, exécutez :

```bash
# Construire les images et démarrer tous les conteneurs
docker compose up -d --build
```

> **Explication** :
> - `up` : Démarre les conteneurs
> - `-d` : Mode détaché (en arrière-plan)
> - `--build` : Reconstruit les images avant de démarrer

### Étape 3 : Vérifier l'état des conteneurs

```bash
# Voir l'état de tous les conteneurs
docker compose ps
```

Vous devriez voir tous les services avec le statut "Up" :

```
NAME                      STATUS          PORTS
client-service-container  Up              0.0.0.0:8088->8088/tcp
consul-container          Up              0.0.0.0:8500->8500/tcp
gateway-service-container Up              0.0.0.0:8888->8888/tcp
mysql-container1          Up              0.0.0.0:3306->3306/tcp
phpmyadmin-container      Up              0.0.0.0:8081->80/tcp
voiture-service-container Up              0.0.0.0:8089->8089/tcp
```

### Étape 4 : Consulter les logs

Si vous rencontrez des problèmes, consultez les logs :

```bash
# Logs de tous les services
docker compose logs

# Logs d'un service spécifique
docker compose logs client-service
docker compose logs voiture-service
docker compose logs gateway-service
docker compose logs consul

# Suivre les logs en temps réel
docker compose logs -f gateway-service
```

---

##  Vérifications

### Vérification 1 : Consul UI

1. Ouvrez votre navigateur et allez sur : **http://localhost:8500**
2. Cliquez sur l'onglet **"Services"**
3. Vous devriez voir apparaître :
   - `gateway`
   - `service-client`
   - `service-voiture`

>  **Checkpoint** : Si vous voyez ces 3 services, la découverte fonctionne !

### Vérification 2 : phpMyAdmin

1. Ouvrez votre navigateur et allez sur : **http://localhost:8081**
2. Connectez-vous avec :
   - **Host** : `mysql`
   - **User** : `root`
   - **Password** : `root`
3. Vous devriez voir les bases de données :
   - `Micro_ClientDB`
   - `Micro_VoitureDB`

>  **Checkpoint** : Si vous voyez ces bases, MySQL fonctionne !

### Vérification 3 : Tester les services

#### Test du Client Service (directement)

```bash
# Vérifier que le service est actif
curl http://localhost:8088/api/clients/health

# Créer un client
curl -X POST http://localhost:8088/api/clients \
  -H "Content-Type: application/json" \
  -d '{
    "nom": "Dupont",
    "prenom": "Jean",
    "email": "jean.dupont@example.com",
    "adresse": "123 Rue de la Paix, Paris"
  }'

# Récupérer tous les clients
curl http://localhost:8088/api/clients
```

#### Test du Voiture Service (directement)

```bash
# Vérifier que le service est actif
curl http://localhost:8089/api/voitures/health

# Créer une voiture
curl -X POST http://localhost:8089/api/voitures \
  -H "Content-Type: application/json" \
  -d '{
    "marque": "Toyota",
    "modele": "Corolla",
    "couleur": "Rouge",
    "immatriculation": "AB-123-CD",
    "prix": 25000.0,
    "clientId": 1
  }'

# Récupérer toutes les voitures
curl http://localhost:8089/api/voitures
```

#### Test via le Gateway

```bash
# Tester le Gateway (point d'entrée unique)
curl http://localhost:8888/api/clients/health
curl http://localhost:8888/api/voitures/health

# Créer un client via le Gateway
curl -X POST http://localhost:8888/api/clients \
  -H "Content-Type: application/json" \
  -d '{
    "nom": "Martin",
    "prenom": "Sophie",
    "email": "sophie.martin@example.com",
    "adresse": "456 Avenue des Champs, Lyon"
  }'
```

---

## 💡 Explications techniques

### Pourquoi Docker en microservices ?

Docker apporte plusieurs avantages cruciaux :

1. **Isolation** : Chaque service a son propre environnement
2. **Reproductibilité** : Même machine, même résultat
3. **Démarrage global** : Un seul fichier Compose pour tout lancer
4. **Gestion des dépendances** : MySQL, Consul, etc. sont automatiquement configurés
5. **Portabilité** : Fonctionne sur n'importe quelle machine avec Docker

### Dockerfile Multi-Stage

#### Structure

```dockerfile
# Stage 1 : Build (Compilation)
FROM maven:3.8.4-openjdk-17 AS builder
WORKDIR /app
COPY ./src ./src
COPY ./pom.xml .
RUN mvn clean package

# Stage 2 : Runtime (Exécution)
FROM openjdk:17-jdk-alpine
COPY ${JAR_FILE} client-service.jar
ENTRYPOINT ["java","-jar","/client-service.jar"]
```

#### Avantages

- **Image finale légère** : Pas besoin de Maven en production
- **Séparation des préoccupations** : Build vs Runtime
- **Sécurité** : Moins de dépendances = moins de vulnérabilités

### Docker Compose

#### Réseau Docker

Tous les services sont dans le même réseau (`microservices-network`). Cela permet :

- **Communication par nom** : `mysql`, `consul` au lieu de `localhost`
- **Isolation** : Les services ne sont pas accessibles depuis l'extérieur sauf via les ports exposés

#### Points importants

1. **Noms DNS** : En Docker, les noms de services deviennent des noms DNS
   -  `jdbc:mysql://mysql:3306/...` (correct)
   -  `jdbc:mysql://localhost:3306/...` (incorrect)

2. **Dépendances** : `depends_on` garantit l'ordre de démarrage

3. **Variables d'environnement** : Configuration via `environment`

### Consul - Découverte de services

#### Comment ça marche ?

1. **Enregistrement** : Chaque service s'enregistre automatiquement dans Consul au démarrage
2. **Découverte** : Le Gateway utilise Consul pour trouver les services
3. **Health Checks** : Consul vérifie périodiquement que les services sont actifs

#### Configuration

```properties
spring.cloud.consul.host=consul
spring.cloud.consul.port=8500
spring.cloud.consul.discovery.enabled=true
```

---

##  Dépannage

### Problème : Port déjà utilisé

**Symptôme** : `Error: port is already allocated`

**Solution** :
```bash
# Trouver le processus qui utilise le port
lsof -i :8500  # Pour Consul
lsof -i :3306  # Pour MySQL

# Arrêter le processus ou changer le port dans docker-compose.yml
```

### Problème : Service ne démarre pas

**Symptôme** : Conteneur en statut "Exited"

**Solution** :
```bash
# Voir les logs du service
docker compose logs client-service

# Redémarrer le service
docker compose restart client-service

# Reconstruire l'image
docker compose up -d --build client-service
```

### Problème : Service non visible dans Consul

**Symptôme** : Service démarré mais absent de Consul UI

**Vérifications** :
1. Vérifier que `spring.cloud.consul.discovery.enabled=true`
2. Vérifier que `spring.cloud.consul.host=consul` (pas `localhost`)
3. Vérifier les logs : `docker compose logs client-service`
4. Attendre quelques secondes (enregistrement asynchrone)

### Problème : Erreur de connexion à MySQL

**Symptôme** : `Communications link failure` ou `Access denied`

**Vérifications** :
1. Vérifier que l'URL utilise `mysql` et non `localhost`
2. Vérifier les credentials : `root` / `root`
3. Vérifier que MySQL est démarré : `docker compose ps mysql`
4. Vérifier les logs MySQL : `docker compose logs mysql`

### Problème : Gateway ne route pas les requêtes

**Symptôme** : `503 Service Unavailable` ou `404 Not Found`

**Vérifications** :
1. Vérifier que les services sont enregistrés dans Consul
2. Vérifier la configuration du routage dans `application.properties`
3. Vérifier les logs du Gateway : `docker compose logs gateway-service`

### Commandes utiles

```bash
# Arrêter tous les services
docker compose down

# Arrêter et supprimer les volumes ( supprime les données)
docker compose down -v

# Redémarrer un service spécifique
docker compose restart client-service

# Voir l'utilisation des ressources
docker stats

# Entrer dans un conteneur
docker exec -it client-service-container sh
```

---
