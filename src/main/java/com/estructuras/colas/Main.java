package com.estructuras.colas;

public class Main {
    public static void main(String[] args) {
        Thread productorThread = new Thread(new Productor());
        Thread consumidorThread = new Thread(new Consumidor());

        productorThread.start();
        consumidorThread.start();

        // try {
        // productorThread.join();
        // consumidorThread.join();
        // } catch (InterruptedException e) {
        // e.printStackTrace();
        // }
    }
}