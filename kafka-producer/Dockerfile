# Pull base image.
FROM ubuntu:latest

RUN \
# Update
apt-get update -y && \
# Install Java
apt-get install default-jre -y

ADD ./target/kafka-producer-1.0-SNAPSHOT-jar-with-dependencies.jar kafka-producer.jar

CMD java -jar kafka-producer.jar
