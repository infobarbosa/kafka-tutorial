#!/bin/bash

echo "Iniciando o Consumidor Python..."

# Ativa o ambiente virtual
source venv/bin/activate

# Navega até o diretório do consumidor Python e executa o script
cd kafka-consumer-python/
python consumer.py

# Desativa o ambiente virtual (opcional, mas boa prática)
deactivate
