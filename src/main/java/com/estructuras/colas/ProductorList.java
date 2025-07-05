package com.estructuras.colas;

import java.time.LocalTime;

import com.rabbitmq.client.*;
import java.util.LinkedList;
import java.util.Queue;

public class ProductorList implements Runnable {
    private final static String COLA_NOMBRE = "mi_cola";

    @Override
    public void run() {
        Queue<String> colaLista = new LinkedList<>();
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");

        try (Connection connection = factory.newConnection();
                Channel channel = connection.createChannel()) {

            channel.queueDeclare(COLA_NOMBRE, false, false, false, null);
            int contador = 1;

            while (true) {
                String mensaje = "MensajeList #" + contador + " [" + LocalTime.now() + "]";
                colaLista.offer(mensaje); // encolar

                String mensajeAEnviar = colaLista.poll(); // desencolar
                if (mensajeAEnviar != null) {
                    channel.basicPublish("", COLA_NOMBRE, null, mensajeAEnviar.getBytes());
                    System.out.println("[LISTA] Enviado: '" + mensajeAEnviar + "'");
                }

                contador++;
                Thread.sleep(2000);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}