#!/bin/bash

echo "Iniciando o Consumidor Python..."

# Configura a variavel CONTAINER_NAME para kafka-consumer-python concatenado com um numero aleatório
CONTAINER_NAME="kafka-consumer-python-$((RANDOM % 9000 + 1000))"

docker run --rm \
    --name $CONTAINER_NAME \
    --network kafka-tutorial_kafka-net \
    -e BOOTSTRAP_SERVERS_CONFIG="kafka-kraft-1:9092,kafka-kraft-2:9192,kafka-kraft-3:9292" \
    -e KAFKA_TOPIC=kafka-tutorial \
    infobarbosa/kafka-consumer-python:latest
