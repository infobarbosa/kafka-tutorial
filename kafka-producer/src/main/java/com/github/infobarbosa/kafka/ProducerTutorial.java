package com.github.infobarbosa.kafka;

import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.serialization.IntegerSerializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Random;

public class ProducerTutorial {
    private final static Logger logger = LoggerFactory.getLogger(ProducerTutorial.class);

    public static void main(String[] args) {
        Random random = new Random();
        final String BOOTSTRAP_SERVERS_CONFIG = System.getenv("BOOTSTRAP_SERVERS_CONFIG");

        if(BOOTSTRAP_SERVERS_CONFIG == null){
            logger.error("Variavel de ambiente BOOTSTRAP_SERVERS_CONFIG não definida.\nUtilize export BOOTSTRAP_SERVERS_CONFIG=localhost:9092.");
            System.exit(0);
        }

        List<String> itens = new ArrayList<String>();
        itens.add("GELADEIRA");
        itens.add("TELEVISOR");
        itens.add("MAQUINA DE LAVAR");
        itens.add("COMPUTADOR");
        itens.add("MOUSE");
        itens.add("VENTILADOR");
        itens.add("MESA DE ESCRITORIO");
        itens.add("FRITADEIRA ELETRICA");
        itens.add("CELULAR SAMSUNG");
        itens.add("RELOGIO");

        Properties properties = new Properties();
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVERS_CONFIG);
        properties.put(ProducerConfig.ACKS_CONFIG, "all");
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, IntegerSerializer.class.getName());
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.put(ProducerConfig.CLIENT_ID_CONFIG, "producer-tutorial");
        properties.put(ProducerConfig.LINGER_MS_CONFIG, "100");
        properties.put(ProducerConfig.RETRIES_CONFIG, "3");

        KafkaProducer<Integer, String> producer = new KafkaProducer<Integer, String>(properties);
        final String topic = "teste";

        ProducerRecord<Integer, String> record = null;
        
        for(int i=0; i < 100000; i++){
            Integer messageKey = i;
            Integer itemKey = Math.abs(random.nextInt()%10);
            String messageValue = itens.get(itemKey) + " vendido em " + System.currentTimeMillis();

            record = new ProducerRecord<>(topic, messageKey, messageValue);
            producer.send(record, new Callback() {
                @Override
                public void onCompletion(RecordMetadata metadata, Exception exception) {
                    if(exception != null)
                        logger.error("Erro processando a mensagem "  + messageKey, exception.getMessage());
                    else
                        logger.info("K: " + messageKey
                                + ". P: " + metadata.partition()
                                + ". OS: " + metadata.offset()
                                + ". TS: " + metadata.timestamp()
                                + ". " + messageValue
                        );
                }
            });

            //coloca pra dormir um pouco
            try {
                Thread.sleep(100);
            }
            catch(InterruptedException e){
                logger.error("problemas durante o sono.", e);
            }
        }

        try{
            producer.close();
        }
        catch(Exception e){
            logger.error("Problemas fechando o producer.", e);
        }
    }
}
