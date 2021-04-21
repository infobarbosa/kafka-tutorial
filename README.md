# Pré-requisitos
    - Java (JDK)
    - Maven
    - Docker
    - Docker Compose

# Build do projeto
Na pasta raiz do projeto:
```
mvn clean package
```
> Atenção! O projeto utiliza `dockerfile-maven-plugin`, do Spotify. Dessa forma o Maven cuida da criação das imagens do produtor e consumidor automaticamente.

# Build da imagem do produtor
> Atenção! O projeto utiliza `dockerfile-maven-plugin`, do Spotify. Dessa forma não é necessário fazer o build da imagem manualmente.
```
docker build . -t kafka-producer-tutorial-image
```

# Build da imagem do consumidor
> Atenção! O projeto utiliza `dockerfile-maven-plugin`, do Spotify. Dessa forma não é necessário fazer o build da imagem manualmente.
```
docker build . -t kafka-consumer-tutorial-image
```
 
# Inicializando todas as imagens de uma só vez
```
docker-compose up -d
```

# Inicializando apenas o Zookeeper
```
docker-compose up -d zookeeper-1 zookeeper-2 zookeeper-3
```
# Inicializando o Kafka
```
docker-compose up -d kafka-1 kafka-2 kafka-3
```

### `KAFKA_NUM_PARTITIONS`
Perceba que o parâmetro `KAFKA_NUM_PARTITIONS` no arquivo docker-compose.yml está ajustado para **6**. Ou seja, um tópico criado automaticamente terá 6 partições por padrão.

### `KAFKA_DEFAULT_REPLICATION_FACTOR`
Perceba que o parâmetro `KAFKA_DEFAULT_REPLICATION_FACTOR` no arquivo docker-compose.yml está ajustado para **3**. Ou seja, um tópico criado automaticamente terá fator de replicação igual a 3 por padrão.

# Logs do Zookeeper
```
docker logs -f zookeeper1
```

# Logs do Kafka
```
docker logs -f kafka-1
```

# Inicializando o Produtor
```
docker-compose up -d producer1
```

# Logs do produtor
```
docker logs -f producer1
```

# Inicializando o Consumidor
```
docker-compose up -d consumer1
docker-compose up -d consumer2
```
# Logs do Consumidor
```
docker logs -f consumer1
docker logs -f consumer2
```

# Inspecionando Consumer Groups

### List
```
docker exec -it kafka-1 kafka-consumer-groups --bootstrap-server kafka-1:9092 --list
```

### Describe
```
docker exec -it kafka-1 kafka-consumer-groups --bootstrap-server kafka-1:9092 --describe --group consumer-tutorial-group
```

### Reset Offsets
> O commando `--reset-offsets` só funciona quando o tópico não possui nenhuma aplicação cliente ativa. Caso contrário a seguinte mensagem será emitida:
> `Error: Assignments can only be reset if the group 'consumer-tutorial-group' is inactive, but the current state is Stable.`


Opções:
- `--shift-by <qualquer número inteiro positivo ou negativo>`
- `--to-current`
- `--to-latest`
- `--to-offset <offset_integer>`
- `--to-datetime <datetime_string>`
- `--by-duration <duration_string>`

`--reset-offsets`, quando utilizado __sem__ a opção `--execute` funciona apenas como um __preview__ do resultado do comando.

Exemplo de __preview__
```
docker exec -it kafka-1 kafka-consumer-groups --bootstrap-server kafka-1:9092 --group consumer-tutorial-group --topic teste --reset-offsets --to-earliest 
```

Exemplo com __--execute__
```
docker exec -it kafka-1 kafka-consumer-groups --bootstrap-server kafka-1:9092 --group consumer-tutorial-group --topic teste --reset-offsets --to-earliest --execute
```
Exemplo com __--to-datetime__
```
docker exec -it kafka-1 kafka-consumer-groups --bootstrap-server kafka-1:9092 --group consumer-tutorial-group --topic teste --reset-offsets --to-datetime 2021-04-21T15:10:00.000 --execute
```

Atenção a esta mensagem:
```
Error: Assignments can only be reset if the group 'consumer-tutorial-group' is inactive, but the current state is Stable.
```

# Ativando uma nova aplicação Producer 
> Alterar o valor parâmetro  `--name` de acordo com a necessidade.
```
docker run --env BOOTSTRAP_SERVERS_CONFIG=kafka-1:9092 --name producer2 --network=kafka-tutorial_kafkalabs -it infobarbosa/kafka-producer:1.0-SNAPSHOT
```

# Ativando uma nova aplicação Consumer
> Alterar o valor do parâmetro  `--name` de acordo com a necessidade.
```
docker run -d --env BOOTSTRAP_SERVERS_CONFIG=kafka-1:9092 --name consumer3 --network=kafka-tutorial_kafkalabs -it infobarbosa/kafka-consumer:1.0-SNAPSHOT
```

# Listando os tópicos
```
docker exec -it kafka-1 kafka-topics --zookeeper zookeeper1:2181 --list
```

# Descrevendo um tópico
```
docker exec -it kafka-1 kafka-topics --zookeeper zookeeper1:2181 --describe --topic teste
```

# Criando um tópico
```
docker exec -it kafka-1 kafka-topics --zookeeper zookeeper1:2181 --create --topic palestra-kafka --partitions 50 --replication-factor 3
```

# Eliminando um tópico
```
docker exec -it kafka-1 kafka-topics --zookeeper zookeeper1:2181 --delete --topic palestra-kafka
```

# Publicando em um tópico
```
docker exec -it kafka-1 kafka-console-producer --broker-list kafka-1:9092 --topic teste
```

# Subscrevendo um tópico
```
docker exec -it kafka-1 kafka-console-consumer --bootstrap-server kafka-1:9092 --topic teste
```

# Interrompendo (e eliminando) o laboratório
```
docker-compose down
```
