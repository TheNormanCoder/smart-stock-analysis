#!/bin/bash

echo "🔧 Verifica cartelle necessarie..."

if [ ! -d "./datasets" ]; then
  echo " Creazione cartella ./datasets"
  mkdir -p datasets
else
  echo " Cartella ./datasets già esistente"
fi

if [ ! -d "./models" ]; then
  echo " Creazione cartella ./models"
  mkdir -p models
else
  echo " Cartella ./models già esistente"
fi

echo " Avvio Docker con build..."
docker-compose up --build
