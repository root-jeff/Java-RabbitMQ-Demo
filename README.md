# 📬 Java + RabbitMQ Demo: Simulación de Colas (FIFO)

Este proyecto demuestra el funcionamiento de colas utilizando **RabbitMQ** como middleware de mensajería y **Java** como lenguaje de implementación.

## 📌 Objetivo

Simular una cola clásica FIFO donde un **Productor** encola mensajes y un **Consumidor** los procesa en orden, visualizando el comportamiento en tiempo real usando la interfaz web de RabbitMQ.

## ⚙️ Requisitos

- Java 8+
- Maven
- Docker

## 🚀 Cómo ejecutar

### 1. Inicia RabbitMQ con Docker:

```bash
docker run -d --hostname rabbit-host --name rabbitmq-demo -p 5672:5672 -p 15672:15672 rabbitmq:3-management
