package ru.otus.L041;

public class BenchmarkMBean implements BenchmarkMBeanInterface extends MBeans{
    private volatile boolean stopFlag = false;
    private volatile int size;

    void run(){
        while(!stopFlag){












        }

    }

    public void setStopFlag() {
        stopFlag = false;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }
}
