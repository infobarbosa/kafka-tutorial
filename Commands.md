# Pré-requisitos
    - docker
    - docker-compose

# Build da imagem do produtor
docker build . -t kafka-producer-tutorial-image

# Build da imagem do consumidor
docker build . -t kafka-consumer-tutorial-image
    
# Inicializando todas as imagens de uma só vez
docker-compose up -d

# Interrompendo todas as imagens
docker-compose down

# Logs do Zookeeper
docker logs -f zookeeper1

# Logs do Kafka
docker logs -f kafka-1

# Logs do produtor
docker logs -f producer1

# Logs do Consumidor
docker logs -f consumer1
docker logs -f consumer2

# Listando os tópicos
docker exec -it kafka-1 kafka-topics --zookeeper zookeeper1:2181 --list

# Descrevendo um tópico
docker exec -it kafka-1 kafka-topics --zookeeper zookeeper1:2181 --describe --topic teste

# Criando um tópico
docker exec -it kafka-1 kafka-topics --zookeeper zookeeper1:2181 --create --topic palestra-kafka --partitions 50 --replication-factor 3

# Eliminando um tópico
docker exec -it kafka-1 kafka-topics --zookeeper zookeeper1:2181 --delete --topic palestra-kafka

# Publicando em um tópico
docker exec -it kafka-1 kafka-console-producer --broker-list kafka-1:9092 --topic teste

# Subscrevendo um tópico
docker exec -it kafka-1 kafka-console-consumer --bootstrap-server kafka-1:9092 --topic teste

# Ativando a aplicação Producer 
export BOOTSTRAP_SERVERS_CONFIG=[servidor kafka]:[porta]  
java -jar kafka-producer-tutorial-1.0-SNAPSHOT-jar-with-dependencies.jar

# Ativando a aplicação Consumer
export BOOTSTRAP_SERVERS_CONFIG=[servidor kafka]:[porta]  
java -jar kafka-consumer-tutorial-1.0-SNAPSHOT-jar-with-dependencies.jar

