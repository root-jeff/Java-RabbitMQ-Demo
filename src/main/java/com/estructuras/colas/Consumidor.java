package com.estructuras.colas;

import com.rabbitmq.client.*;

public class Consumidor implements Runnable {
    private final static String COLA_NOMBRE = "mi_cola"; // Cambia a "cola_lista" si usas ProductorList

    @Override
    public void run() {
        try {
            ConnectionFactory factory = new ConnectionFactory();
            factory.setHost("localhost");
            Connection connection = factory.newConnection();
            Channel channel = connection.createChannel();

            channel.queueDeclare(COLA_NOMBRE, false, false, false, null);
            System.out.println("Esperando mensajes...");

            DeliverCallback callback = (tag, delivery) -> {
                String mensaje = new String(delivery.getBody(), "UTF-8");
                try {
                    Thread.sleep(2000); // << AQUI es el retraso
                    System.out.println("Recibido: '" + mensaje);

                    // Confirmacion manual para el mensaje procesado:
                    channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            };

            channel.basicConsume(COLA_NOMBRE, false, callback, tag -> { // auto ack false para manual
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
