package ru.otus.l021;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        ObjectFactory factory = new ObjectFactory();
        MemoryCounter memoryCounter = new MemoryCounter(factory);
        factory.generateObjects();

        long[] arr = new long[1000];
        for(int i = 0; i < 1000; i++){
            arr[i] = 1000L;
        }

        MemoryCounter.printObjectSize(arr);
    }
}
