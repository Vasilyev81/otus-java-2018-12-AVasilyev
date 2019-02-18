package ru.otus.L041;


public class Benchmark implements BenchmarkMBean {

    private volatile int size;

    @Override
    public void setSize(int size) {
        this.size = size;
    }

    @Override
    public int getSize() {
        return size;
    }


    @SuppressWarnings("InfiniteLoopStatment")
    void run() throws InterruptedException {
        int counter = 0;
        while (true) {
            final int local = size;
            final Object[] array = new Object[local];
            System.out.println("Array of size: " + array.length + " created.");
            for (int i = 0; i < local; i++) array[i] = new String(new char[0]);
            System.out.println("Created " + local + " objects.");
            counter++;
            if ((counter % 100) == 0) {
                GCMain.STORAGE.put(System.currentTimeMillis(), array);
            }
            Thread.sleep(100);
        }
    }
}














