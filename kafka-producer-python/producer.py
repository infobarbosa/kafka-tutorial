from kafka import KafkaProducer
import os
import sys
import logging
import random
import time

# Configuração do Logger
logging.basicConfig(
    level=logging.INFO,
    format='%(asctime)s - %(name)s - %(levelname)s - %(message)s'
)
logger = logging.getLogger(__name__)

def on_send_success(record_metadata, message_key, message_value):
    logger.info(f"K: {message_key}"
                f". P: {record_metadata.partition}"
                f". OS: {record_metadata.offset}"
                f". TS: {record_metadata.timestamp}"
                f". {message_value}")

def on_send_error(excp, message_key):
    logger.error(f"Erro processando a mensagem {message_key}", exc_info=excp)

def main():
    BOOTSTRAP_SERVERS_CONFIG = os.getenv("BOOTSTRAP_SERVERS_CONFIG")

    if not BOOTSTRAP_SERVERS_CONFIG:
        logger.error("Variavel de ambiente BOOTSTRAP_SERVERS_CONFIG não definida.\nUtilize export BOOTSTRAP_SERVERS_CONFIG=localhost:9092.")
        sys.exit(1)

    itens = [
        "GELADEIRA", "TELEVISOR", "MAQUINA DE LAVAR", "COMPUTADOR", "MOUSE",
        "VENTILADOR", "MESA DE ESCRITORIO", "FRITADEIRA ELETRICA", "CELULAR SAMSUNG", "RELOGIO"
    ]

    producer = None
    try:
        producer = KafkaProducer(
            bootstrap_servers=BOOTSTRAP_SERVERS_CONFIG.split(','),
            acks='all',
            key_serializer=lambda k: k.to_bytes(4, byteorder='big', signed=True) if k is not None else None, # Serializador para Inteiro
            value_serializer=lambda v: v.encode('utf-8') if v is not None else None, # Serializador para String
            client_id='producer-tutorial-py', # Similar ao Java
            linger_ms=100,
            retries=3
        )

        topic = os.getenv("KAFKA_TOPIC", "tutorial-python")
        logger.info(f"Iniciando Kafka Producer. Enviando mensagens para o tópico: {topic}")

        for i in range(100000): # Similar ao loop de 100000 do Java
            message_key = i
            item_key = random.randint(0, len(itens) - 1)
            message_value = f"{itens[item_key]} vendido em {int(time.time() * 1000)}"

            # Envia a mensagem e adiciona callbacks
            future = producer.send(topic, key=message_key, value=message_value)
            future.add_callback(lambda rm: on_send_success(rm, message_key, message_value))
            future.add_errback(lambda exc: on_send_error(exc, message_key))

            # Coloca pra dormir um pouco, similar ao Thread.sleep(100)
            time.sleep(0.1)

    except KeyboardInterrupt:
        logger.info("Produtor interrompido pelo usuário.")
    except Exception as e:
        logger.error(f"Problemas durante a produção: {e}", exc_info=True)
    finally:
        if producer:
            logger.info("Realizando flush e fechando o produtor Kafka...")
            try:
                producer.flush(timeout=10) # Tenta fazer flush das mensagens pendentes
            except Exception as e:
                logger.error(f"Erro durante o flush do produtor: {e}", exc_info=True)
            finally:
                producer.close(timeout=10) # Fecha o produtor
                logger.info("Produtor fechado.")

if __name__ == "__main__":
    main()
