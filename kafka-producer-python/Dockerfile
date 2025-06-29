FROM python:3.9-slim-buster

WORKDIR /app

# Copia o requirements.txt da raiz do contexto de build
COPY requirements.txt .
RUN pip install --no-cache-dir -r requirements.txt

# Copia o producer.py do seu diretório específico dentro do contexto de build
COPY kafka-producer-python/producer.py .

ENV BOOTSTRAP_SERVERS_CONFIG="localhost:9092"
ENV KAFKA_TOPIC="tutorial-python"

CMD ["python", "producer.py"]