package com.github.infobarbosa.kafka;

import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.serialization.IntegerDeserializer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.Arrays;
import java.util.Map;
import java.util.Properties;
import java.util.Random;

public class ConsumerTutorial {
    private static final Logger logger = LoggerFactory.getLogger(ConsumerTutorial.class.getName());

    public static void main(String[] args) {
        final String BOOTSTRAP_SERVERS_CONFIG = System.getenv("BOOTSTRAP_SERVERS_CONFIG");
        final int CLIENT_ID_CONFIG = new Random().nextInt();

        if(BOOTSTRAP_SERVERS_CONFIG == null){
            logger.error("Variavel de ambiente BOOTSTRAP_SERVERS_CONFIG não definida.\nUtilize export BOOTSTRAP_SERVERS_CONFIG=localhost:9092.");
            System.exit(0);
        }

        Properties properties = new Properties();
        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVERS_CONFIG);
        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, IntegerDeserializer.class.getName());
        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "false");
        properties.put(ConsumerConfig.CLIENT_ID_CONFIG, "consumer-" + CLIENT_ID_CONFIG);
        properties.put(ConsumerConfig.HEARTBEAT_INTERVAL_MS_CONFIG, "100");
        properties.put(ConsumerConfig.GROUP_ID_CONFIG, "consumer-tutorial-group");

        final String topic = "teste";

        try {
            KafkaConsumer<Integer, String> consumer = new KafkaConsumer<>(properties);

            try {
                consumer.subscribe(Arrays.asList(topic));

                while (true) {
                    ConsumerRecords<Integer, String> records = consumer.poll(Duration.ofMillis(100));
                    for (ConsumerRecord<Integer, String> record : records) {
                        Integer messageKey = record.key();
                        String messageValue = record.value();
                        long offset = record.offset();
                        long partition = record.partition();
                        long timestamp = record.timestamp();

                        consumer.commitAsync(new OffsetCommitCallback() {
                            @Override
                            public void onComplete(Map<TopicPartition, OffsetAndMetadata> offsets, Exception exception) {
                                logger.info("K: " + messageKey
                                        + "; P: " + partition
                                        + "; OS: " + offset
                                        + "; TS: " + timestamp
                                        + "; V: " + messageValue
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
                }
            } finally {
                consumer.close();
            }
        }
        catch(Exception e){
            logger.error("Problemas durante o consumo", e);
        }
    }
}
