#!/bin/bash

echo "Iniciando o Produtor Python..."

# Ativa o ambiente virtual
source venv/bin/activate

# Navega até o diretório do produtor Python e executa o script
cd kafka-producer-python/
python producer.py

# Desativa o ambiente virtual (opcional, mas boa prática)
deactivate
