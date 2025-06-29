#!/bin/bash

echo "Iniciando a construção das imagens Docker para clientes Python..."

# 1. Construir a imagem Docker para o Produtor Python
echo "Removendo imagens antigas do Produtor Python..."
docker rmi infobarbosa/kafka-producer-python:latest --force 2>/dev/null
if [ $? -ne 0 ]; then
    echo "Nenhuma imagem antiga do Produtor Python encontrada ou erro ao removê-la."
fi

echo "Construindo imagem Docker para o Produtor Python..."
docker build -t infobarbosa/kafka-producer-python:latest -f ./kafka-producer-python/Dockerfile .

if [ $? -ne 0 ]; then
    echo "Erro na construção da imagem do Produtor Python. Abortando."
    exit 1
fi
echo "Imagem do Produtor Python construída com sucesso."

# 2. Construir a imagem Docker para o Consumidor Python
echo "Removendo imagens antigas do Consumidor Python..."
docker rmi infobarbosa/kafka-consumer-python:latest --force 2>/dev/null
if [ $? -ne 0 ]; then
    echo "Nenhuma imagem antiga do Consumidor Python encontrada ou erro ao removê-la."
fi

echo "Construindo imagem Docker para o Consumidor Python..."
docker build -t infobarbosa/kafka-consumer-python:latest -f ./kafka-consumer-python/Dockerfile .

if [ $? -ne 0 ]; then
    echo "Erro na construção da imagem do Consumidor Python. Abortando."
    exit 1
fi
echo "Imagem do Consumidor Python construída com sucesso."

echo "Todas as imagens Docker para clientes Python foram construídas com sucesso!"