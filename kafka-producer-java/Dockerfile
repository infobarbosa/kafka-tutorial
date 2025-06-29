# Pull base image.
FROM ubuntu:latest

RUN \
# Update
apt-get update -y && \
# Install Java
apt install -y openjdk-17-jre

ADD ./target/kafka-producer-1.2.jar kafka-producer.jar

CMD java -jar kafka-producer.jar
