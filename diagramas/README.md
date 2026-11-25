# Diagramas

Cada diagrama conceitual ou de arquitetura do tutorial existe em dois formatos, com o mesmo nome:

- **`.svg`** — a imagem que aparece no README.
- **`.excalidraw`** — a cena editável. Abra em [excalidraw.com](https://excalidraw.com) (menu **Abrir**, ou arraste o arquivo para a página) para editar ou desenhar por cima durante a aula.

Para atualizar uma imagem depois de editar a cena: **Exportar imagem → SVG**, com fundo, e salve por cima do `.svg` de mesmo nome. Salve também a cena (`.excalidraw`), para as duas versões não divergirem.

| Arquivo | Seção |
|---|---|
| `01-streaming-vs-fila-vs-db` | Parte 1 — Event Streaming × Fila Tradicional × Banco de Dados |
| `02-topico-particoes-log` | Parte 1 — Tópico, Partições, Offsets e Ordenação por Chave |
| `03-kraft-vs-zookeeper` | Parte 1 — Arquitetura Legada (ZooKeeper) × Kafka 4.x (KRaft) |
| `04-topologia-lab-vs-producao` | O ambiente — Topologia de Produção × Single-Container |
| `05-armazenamento-segmentos-disco` | Parte 3 — Modelo de Armazenamento em Disco, Índices e Zero-Copy |
| `06-retencao-vs-compactacao` | Parte 3 — Retenção (`delete`) × Compactação (`compact` + tombstone) |
| `07-consumer-groups-particoes` | Parte 4 — Consumer Groups, Partições e Múltiplos Grupos |
| `08-rebalance-eager-vs-cooperative` | Parte 4 — Rebalanceamento Eager × Cooperative Sticky |
| `09-replicacao-isr-self-healing` | Parte 6 — Replicação, ISR, High Watermark e Self-Healing |
| `10-dlq-e-pipeline-resiliente` | Parte 6 — Resiliência na Aplicação Python (Retry, DLQ e Commit Manual) |
