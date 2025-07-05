package com.estructuras.colas;

import com.rabbitmq.client.*;

public class Consumidor implements Runnable {
    private final static String COLA_NOMBRE = "mi_cola";

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
                    System.out.println("Recibido: '" + mensaje + "', procesando...");
                    Thread.sleep(5000); // << AQUI es el retraso
                    System.out.println("Procesado: '" + mensaje + "'");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            };

            channel.basicConsume(COLA_NOMBRE, true, callback, tag -> {
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
