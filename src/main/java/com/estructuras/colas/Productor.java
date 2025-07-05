package com.estructuras.colas;

import java.time.LocalTime;

import com.rabbitmq.client.*;

public class Productor implements Runnable {
    private final static String COLA_NOMBRE = "mi_cola";

    @Override
    public void run() {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");

        try (Connection connection = factory.newConnection();
                Channel channel = connection.createChannel()) {

            channel.queueDeclare(COLA_NOMBRE, false, false, false, null);

            int contador = 1;
            while (true) {
                String mensaje = "Mensaje #" + contador + " [" + LocalTime.now() + "]";
                channel.basicPublish("", COLA_NOMBRE, null, mensaje.getBytes());
                System.out.println("Enviado: '" + mensaje + "'");
                contador++;
                Thread.sleep(2000); // cada 2 segundos
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
