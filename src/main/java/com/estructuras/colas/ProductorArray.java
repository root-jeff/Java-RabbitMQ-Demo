package com.estructuras.colas;

import java.time.LocalTime;

import com.rabbitmq.client.*;

public class ProductorArray implements Runnable {
    private final static String COLA_NOMBRE = "mi_cola";
    private final static int CAPACIDAD = 10;

    // Implementación simple de cola circular con array
    static class ColaArray {
        String[] datos = new String[CAPACIDAD];
        int frente = 0;
        int fin = 0;
        int size = 0;

        boolean estaLlena() {
            return size == CAPACIDAD;
        }

        boolean estaVacia() {
            return size == 0;
        }

        void encolar(String mensaje) {
            if (estaLlena()) {
                System.out.println("⚠️ Cola llena. No se puede encolar: " + mensaje);
                return;
            }
            datos[fin] = mensaje;
            fin = (fin + 1) % CAPACIDAD;
            size++;
        }

        String desencolar() {
            if (estaVacia())
                return null;
            String mensaje = datos[frente];
            frente = (frente + 1) % CAPACIDAD;
            size--;
            return mensaje;
        }

        void imprimirEstado() {
            System.out.print("📦 Cola actual: [");
            for (int i = 0; i < size; i++) {
                int index = (frente + i) % CAPACIDAD;
                System.out.print(datos[index] + (i < size - 1 ? ", " : ""));
            }
            System.out.println("]");
        }
    }

    @Override
    public void run() {
        ColaArray cola = new ColaArray();
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");

        try (Connection connection = factory.newConnection();
                Channel channel = connection.createChannel()) {

            channel.queueDeclare(COLA_NOMBRE, false, false, false, null);
            int contador = 1;

            while (true) {
                String mensaje = "MensajeArr #" + contador + " [" + LocalTime.now() + "]";
                cola.encolar(mensaje);
                cola.imprimirEstado(); // Imprimir estado de la cola

                String mensajeAEnviar = cola.desencolar();
                if (mensajeAEnviar != null) {
                    channel.basicPublish("", COLA_NOMBRE, null, mensajeAEnviar.getBytes());
                    System.out.println("[ARRAY] Enviado: '" + mensajeAEnviar + "'");
                }

                contador++;
                Thread.sleep(2000);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
