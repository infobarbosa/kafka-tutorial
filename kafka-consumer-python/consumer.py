import os
import sys
import logging
import random
import time
from kafka import KafkaConsumer

# Configuração do Logger
logging.basicConfig(
    level=logging.INFO,
    format='%(asctime)s - %(name)s - %(levelname)s - %(message)s'
)
logger = logging.getLogger(__name__)

def main():
    BOOTSTRAP_SERVERS_CONFIG = os.getenv("BOOTSTRAP_SERVERS_CONFIG")
    
    if not BOOTSTRAP_SERVERS_CONFIG:
        logger.error("Variavel de ambiente BOOTSTRAP_SERVERS_CONFIG não definida.\nUtilize export BOOTSTRAP_SERVERS_CONFIG=localhost:9092.")
        sys.exit(1)

    client_id_suffix = random.randint(0, 2**31 - 1)
    client_id = f'consumer-py-{client_id_suffix}'
    group_id = os.getenv("KAFKA_GROUP_ID", "consumer-tutorial-group-py")
    topic = os.getenv("KAFKA_TOPIC", "kafka-tutorial")

    logger.info(f"Iniciando Kafka Consumer para o tópico: {topic}")
    logger.info(f"Bootstrap Servers: {BOOTSTRAP_SERVERS_CONFIG}")
    logger.info(f"Client ID: {client_id}")
    logger.info(f"Group ID: {group_id}")

    consumer = None
    try:
        consumer = KafkaConsumer(
            bootstrap_servers=BOOTSTRAP_SERVERS_CONFIG.split(','),
            client_id=client_id,
            group_id=group_id,
            key_deserializer=lambda k: int.from_bytes(k, byteorder='big', signed=True) if k is not None else None,
            value_deserializer=lambda v: v.decode('utf-8') if v is not None else None,
            enable_auto_commit=False, 
            heartbeat_interval_ms=100,
            auto_offset_reset='earliest'
        )

        consumer.subscribe([topic])
        logger.info(f"Inscrito no tópico: {topic}")

        while True:
            # Poll por 100ms, similar ao Duration.ofMillis(100)
            records_polled = consumer.poll(timeout_ms=100) 
            
            for topic_partition, messages_in_partition in records_polled.items():
                for record in messages_in_partition:
                    message_key = record.key
                    message_value = record.value
                    offset = record.offset
                    partition = record.partition
                    timestamp = record.timestamp

                    consumer.commit() 

                    logger.info(f"K: {message_key}; P: {partition}; OS: {offset}; TS: {timestamp}; V: {message_value}")

                    # Coloca pra dormir um pouco, (100ms)
                    time.sleep(0.1)

    except KeyboardInterrupt:
        logger.info("Consumidor interrompido pelo usuário.")
    except Exception as e:
        logger.error(f"Problemas durante o consumo: {e}", exc_info=True)
    finally:
        if consumer:
            logger.info("Fechando o consumidor Kafka...")
            consumer.close()

if __name__ == '__main__':
    main()