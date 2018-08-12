# Projetos de exemplo utilizados no tutorial de Kafka

# Comandos utilizados no tutorial
kafka-topics --zookeeper zookeeper1:2181/kafka --list

kafka-topics --zookeeper zookeeper1:2181/kafka --describe --topic teste
kafka-topics --zookeeper zookeeper1:2181/kafka --describe --topic teste2

kafka-topics --zookeeper zookeeper1:2181/kafka --create --topic palestra-kafka --partitions 50 --replication-factor 3

kafka-topics --zookeeper zookeeper1:2181/kafka --delete --topic teste1

kafka-console-producer --broker-list kafka1:9092 --topic palestra-kafka 

kafka-console-consumer --bootstrap-server kafka1:9092 --topic palestra-kafka


