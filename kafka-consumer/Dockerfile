# Pull base image.
FROM ubuntu:latest

RUN  \
# Update
apt-get update -y && \
# Install Java
apt-get install default-jre -y

ADD ./target/kafka-consumer-1.0-SNAPSHOT-jar-with-dependencies.jar kafka-consumer.jar

CMD java -jar kafka-consumer.jar
