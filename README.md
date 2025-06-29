# Tutorial de Apache Kafka: Produtores e Consumidores
Author: Prof. Barbosa  
Contact: infobarbosa@gmail.com  
Github: [infobarbosa](https://github.com/infobarbosa)

Este repositório contém um tutorial completo para estudantes de pós-graduação, focado em demonstrar os conceitos fundamentais do Apache Kafka através de exemplos práticos.

O ambiente é orquestrado com Docker Compose, facilitando a inicialização de um cluster Kafka e a execução de aplicações produtoras e consumidoras desenvolvidas em **Java** e **Python**.


## 1. Pré-requisitos

Antes de começar, garanta que você tenha as seguintes ferramentas instaladas em seu sistema:

*   **Docker e Docker Compose:** Essenciais para criar e gerenciar o ambiente Kafka.
*   **Java (JDK 11+):** Necessário para executar os exemplos em Java.
*   **Maven:** Utilizado para compilar e empacotar as aplicações Java.
*   **Python (3.8+):** Necessário para executar os exemplos em Python.
*   **Git:** Para clonar este repositório.

## 2. Estrutura do Projeto

O repositório está organizado da seguinte forma:

```
/
├── kafka-consumer-java/        # Aplicação Java que consome mensagens
├── kafka-producer-java/        # Aplicação Java que produz mensagens
├── kafka-consumer-python/      # Script Python que consome mensagens
├── kafka-producer-python/      # Script Python que produz mensagens
├── docker-compose.yml          # Arquivo principal para orquestrar o ambiente
└── pom.xml                     # POM pai para os módulos Java
```

## 3. Como Executar o Tutorial (Passo a Passo)

Siga os passos abaixo para configurar e executar o ambiente completo.

### Passo 3.1: Iniciar o Ambiente Kafka com Docker

Este repositório oferece duas configurações de ambiente Kafka via Docker Compose:

1.  **Com Zookeeper (Padrão):** `docker-compose.yml.zookeeper` (configuração tradicional)
2.  **Com KRaft (Sem Zookeeper):** `docker-compose.yml.kraft` (configuração moderna, requer um `KAFKA_CLUSTER_ID` gerado)

Para iniciar o ambiente com Zookeeper (recomendado para este tutorial):

```bash
# Inicia todos os serviços (Kafka, Zookeeper, etc.) em background
docker-compose -f docker-compose.yml.zookeeper up -d
```

Para iniciar o ambiente com KRaft (avançado):

```bash
# Certifique-se de ter gerado um KAFKA_CLUSTER_ID e atualizado o docker-compose.yml.kraft
docker-compose -f docker-compose.yml.kraft up -d
```

Para verificar se os contêineres estão rodando, você pode usar o comando:
```bash
docker-compose ps
```

Para visualizar os logs de um serviço específico (ex: `kafka-1` ou `kafka-kraft-1`):
```bash
docker logs -f kafka-1
```

### Passo 3.2: Executando os Exemplos em Java

As aplicações Java são gerenciadas pelo Maven.

**Compilando o projeto:**

Primeiro, compile e empacote as aplicações Java. Na raiz do projeto, execute:
```bash
mvn clean package
```

**Executando o Produtor Java:**

A aplicação `kafka-producer` enviará uma mensagem a cada segundo para o tópico `tutorial-java`.
```bash
java -jar kafka-producer-java/target/kafka-producer-1.2.jar
```

**Executando o Consumidor Java:**

A aplicação `kafka-consumer` se inscreverá no tópico `tutorial-java` para receber as mensagens.
```bash
java -jar kafka-consumer-java/target/kafka-consumer-1.2.jar
```

### Passo 3.3: Executando os Exemplos em Python

Para os scripts Python, é uma boa prática criar um ambiente virtual e instalar as dependências.

**Configurando o Ambiente Python:**

```bash
# Crie um ambiente virtual
python3 -m venv venv

# Ative o ambiente virtual
source venv/bin/activate

# Instale as dependências a partir do arquivo requirements.txt
pip install -r requirements.txt
```
*(Nota: Após terminar, você pode desativar o ambiente com o comando `deactivate`)*

**Executando o Produtor Python:**

O script `producer.py` enviará 10 mensagens para o tópico `tutorial-python`.

```bash
# Navegue até o diretório do produtor Python
cd kafka-producer-python/

# Execute o script
python producer.py
```

**Executando o Consumidor Python:**

O script `consumer.py` se inscreverá no tópico `tutorial-python` para receber as mensagens.

```bash
# Navegue até o diretório do consumidor Python
cd kafka-consumer-python/

# Execute o script
python consumer.py
```

## 4. Comandos Úteis do Kafka (via Docker)

Você pode executar comandos `kafka-cli` diretamente nos contêineres para administrar o cluster.

### Tópicos

*   **Listar todos os tópicos:**
    ```bash
    docker exec -it kafka-1 kafka-topics --bootstrap-server kafka-1:9092 --list
    ```

*   **Descrever um tópico específico (ex: `tutorial-java`):**
    ```bash
    docker exec -it kafka-1 kafka-topics --bootstrap-server kafka-1:9092 --describe --topic tutorial-java
    ```

*   **Criar um novo tópico:**
    ```bash
    docker exec -it kafka-1 kafka-topics --bootstrap-server kafka-1:9092 --create --topic meu-novo-topico --partitions 3 --replication-factor 3
    ```

*   **Publicar mensagens via console:**
    ```bash
    docker exec -it kafka-1 kafka-console-producer --broker-list kafka-1:9092 --topic meu-novo-topico
    ```

*   **Consumir mensagens via console:**
    ```bash
    docker exec -it kafka-1 kafka-console-consumer --bootstrap-server kafka-1:9092 --topic meu-novo-topico --from-beginning
    ```

### Grupos de Consumidores (Consumer Groups)

*   **Listar todos os grupos de consumidores:**
    ```bash
    docker exec -it kafka-1 kafka-consumer-groups --bootstrap-server kafka-1:9092 --list
    ```

*   **Descrever um grupo específico (ex: `consumer-tutorial-group`):**
    ```bash
    docker exec -it kafka-1 kafka-consumer-groups --bootstrap-server kafka-1:9092 --describe --group consumer-tutorial-group
    ```

*   **Resetar offsets de um grupo (ex: para o início do tópico):**
    > **Atenção:** O grupo de consumidores deve estar inativo para resetar os offsets.
    ```bash
    docker exec -it kafka-1 kafka-consumer-groups --bootstrap-server kafka-1:9092 --group consumer-tutorial-group --topic tutorial-java --reset-offsets --to-earliest --execute
    ```

## 5. Encerrando o Ambiente

Para parar e remover todos os contêineres, redes e volumes criados pelo Docker Compose, execute:

```bash
docker-compose down
```

## Parabéns! Sua Jornada no Kafka Começou!

Parabéns por ter chegado até aqui! Ao completar este tutorial, você não apenas executou produtores e consumidores, mas também deu os primeiros passos sólidos na compreensão de um dos sistemas de mensageria distribuída mais poderosos e amplamente utilizados no mercado: o Apache Kafka.

Você explorou conceitos fundamentais como:
*   **Orquestração de Ambientes:** Utilizando Docker Compose para configurar um cluster Kafka de forma eficiente.
*   **Produção e Consumo de Mensagens:** Implementando e executando aplicações em Java e Python.
*   **Configurações Essenciais:** Entendendo como externalizar e gerenciar parâmetros importantes.
*   **Ferramentas de Linha de Comando:** Administrando tópicos e grupos de consumidores diretamente no cluster.

Este é apenas o começo! O universo do Kafka é vasto e oferece inúmeras possibilidades. Encorajamos você a:

1.  **Aprofundar-se nos Conceitos:** Explore a documentação oficial do Kafka para entender mais sobre partições, replicação, garantias de entrega, e o funcionamento interno do KRaft.
2.  **Experimentar:** Modifique os exemplos, crie novos tópicos, teste diferentes configurações de produtores e consumidores.
3.  **Integrar:** Pense em como o Kafka pode ser aplicado em seus próprios projetos de pesquisa ou desenvolvimento.
4.  **Explorar o Ecossistema:** Descubra ferramentas como Kafka Streams, Kafka Connect, ksqlDB e outras tecnologias que complementam o Kafka.

Lembre-se: a melhor forma de aprender é praticando. Continue construindo, experimentando e desvendando o poder do Apache Kafka.

Se tiver dúvidas ou quiser compartilhar suas descobertas, não hesite em procurar seu professor ou a comunidade Kafka.

Boa sorte em sua jornada!

