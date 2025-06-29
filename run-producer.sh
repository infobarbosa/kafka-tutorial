#!/bin/bash

echo "Iniciando o Produtor Python..."

# Executa a imagem infobarbosa/kafka-producer-python:latest
docker run --rm \
    --name kafka-producer-python \
    --network kafka-tutorial_kafka-net \
    -e BOOTSTRAP_SERVERS_CONFIG="kafka-kraft-1:9092,kafka-kraft-2:9192,kafka-kraft-3:9292" \
    -e TOPIC_NAME=tutorial-python \
    infobarbosa/kafka-producer-python:latest

echo "Produtor Python finalizado."
