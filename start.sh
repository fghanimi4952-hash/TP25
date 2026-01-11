#!/bin/bash

# Script de démarrage rapide pour le TP 25
# Conteneurisation des Microservices avec Docker + Consul

echo "🚀 Démarrage de l'architecture microservices..."
echo ""

# Vérifier que Docker est installé
if ! command -v docker &> /dev/null; then
    echo "❌ Docker n'est pas installé. Veuillez installer Docker d'abord."
    exit 1
fi

# Vérifier que Docker Compose est disponible
if ! command -v docker compose &> /dev/null; then
    echo "❌ Docker Compose n'est pas disponible. Veuillez installer Docker Compose."
    exit 1
fi

echo "✅ Docker et Docker Compose sont installés"
echo ""

# Construire et démarrer tous les services
echo "📦 Construction des images et démarrage des conteneurs..."
docker compose up -d --build

echo ""
echo "⏳ Attente du démarrage des services (30 secondes)..."
sleep 30

echo ""
echo "📊 État des conteneurs:"
docker compose ps

echo ""
echo "✅ Architecture démarrée avec succès!"
echo ""
echo "📍 Services disponibles:"
echo "   - Consul UI:        http://localhost:8500"
echo "   - phpMyAdmin:       http://localhost:8081"
echo "   - Gateway:          http://localhost:8888"
echo "   - Client Service:   http://localhost:8088"
echo "   - Voiture Service:  http://localhost:8089"
echo ""
echo "📝 Pour voir les logs: docker compose logs -f [service-name]"
echo "🛑 Pour arrêter: docker compose down"
