package com.estructuras.colas;

public class Main {
    public static void main(String[] args) {
        Thread productorThread = new Thread(new ProductorList());
        Thread consumidorThread = new Thread(new Consumidor());

        // productorThread.start();

        try {
            System.out.println("Esperando 30 segundos antes de iniciar el consumidor...");
            Thread.sleep(10000); // espera 10 segundos
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        consumidorThread.start();
    }
}