# ============================================
# Dockerfile Multi-Stage pour Gateway Service
# ============================================
# 
# ÉTAPE 1 : Stage de Build (Compilation)
# Utilise une image Maven pour compiler le projet
# ============================================
FROM maven:3.8.4-openjdk-17 AS builder

# Définir le répertoire de travail dans le conteneur
WORKDIR /app

# Copier les fichiers sources du projet
COPY ./src ./src

# Copier le fichier pom.xml pour résoudre les dépendances
COPY ./pom.xml .

# Compiler le projet et créer le JAR
# - clean : nettoie les fichiers de build précédents
# - package : compile et crée le JAR dans target/
RUN mvn clean package -DskipTests

# ============================================
# ÉTAPE 2 : Stage Runtime (Exécution)
# Utilise une image Java légère pour exécuter le JAR
# ============================================
FROM openjdk:17-jdk-alpine

# Créer un volume temporaire (optionnel, pour les fichiers temporaires)
VOLUME /tmp

# Définir l'argument pour le fichier JAR
# Le JAR sera copié depuis le stage builder
ARG JAR_FILE=target/*.jar

# Copier le JAR compilé depuis le stage builder vers le conteneur runtime
COPY ${JAR_FILE} gateway-service.jar

# Commande d'exécution : lancer le JAR Spring Boot
ENTRYPOINT ["java","-jar","/gateway-service.jar"]

# Explication pédagogique :
# - Stage 1 (builder) : contient Maven et compile le code → produit un JAR
# - Stage 2 (runtime) : contient uniquement Java → exécute le JAR
# Avantage : image finale plus légère (pas besoin de Maven en production)
