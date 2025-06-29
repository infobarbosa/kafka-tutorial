#!/bin/bash

echo "Iniciando o cluster Kafka (KRaft) via Docker Compose..."

# Inicia o cluster Kafka usando o arquivo docker-compose.yml.kraft
docker-compose -f docker-compose.yml.kraft up -d

if [ $? -ne 0 ]; then
    echo "Erro ao iniciar o cluster Kafka (KRaft). Verifique os logs."
    exit 1
fi

echo "Cluster Kafka (KRaft) inicializado com sucesso!"
echo "Lembre-se de que o KAFKA_CLUSTER_ID no docker-compose.yml.kraft deve ser um UUID real."
