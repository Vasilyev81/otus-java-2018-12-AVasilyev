package ru.otus.l021;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        ObjectFactory factory = new ObjectFactory();
        MemoryCounter memoryCounter = new MemoryCounter(factory);
        factory.generateObjects();
    }
}
