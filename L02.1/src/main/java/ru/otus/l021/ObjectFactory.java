package ru.otus.l021;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;

public class ObjectFactory<T extends Object> {

    private T generatedObject;
    private Object Integer;

    public ObjectFactory() {
        this.generatedObject = null;
    }

    private void setGeneratedObject(T newObject) {
        T oldObject = generatedObject;
        generatedObject = newObject;
        changeSupport.firePropertyChange("generatedObject", oldObject, newObject);
    }

    public T getGeneratedObject() {
        return generatedObject;
    }

    private PropertyChangeSupport changeSupport = new PropertyChangeSupport(this);

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        changeSupport.addPropertyChangeListener(listener);
    }

    public void removeChangeListener(PropertyChangeListener listener) {
        changeSupport.removePropertyChangeListener(listener);
    }

    public Integer[] generateArrayOfIntegers(int numOfElements) {
        Integer[] result = new Integer[numOfElements];
        Arrays.fill(result, 0, numOfElements - 1, 1000);
        printObjInfo("Integer", numOfElements);
        return result;
    }

    public Long[] generateArrayOfLongs(int numOfElements) {
        Long[] result = new Long[numOfElements];
        Arrays.fill(result, 0, numOfElements - 1, 1000L);
        printObjInfo("Long", numOfElements);
        return result;
    }

    public String[] generateArrayOfStrings(int numOfElements) {
        String[] result = new String[numOfElements];
        Arrays.fill(result, 0, numOfElements - 1, "Corrige praetertum, praesens rege, cerne futurum.");
        printObjInfo("String", numOfElements);
        return result;
    }

    public void printObjInfo(String objName, int numOfElements){
        System.out.println("Internals and footprint of " + objName + "[" + numOfElements + "]:");
    }


    public void generateObjects() throws InterruptedException {
        setGeneratedObject((T) new Object());
        Thread.sleep(1000);
        setGeneratedObject((T) new A());
        Thread.sleep(1000);
        setGeneratedObject((T) new BigDecimal("999999999999999.999"));
        Thread.sleep(1000);
        setGeneratedObject((T) new ArrayList<String>());
        Thread.sleep(1000);
        setGeneratedObject((T) new Integer[100]);
        Thread.sleep(1000);
        setGeneratedObject((T) Calendar.getInstance());
        Thread.sleep(1000);
        setGeneratedObject((T) generateArrayOfIntegers(100));
        Thread.sleep(1000);
        setGeneratedObject((T) generateArrayOfIntegers(1000));
        Thread.sleep(1000);
        setGeneratedObject((T) generateArrayOfIntegers(10000));
        Thread.sleep(1000);
        setGeneratedObject((T) generateArrayOfIntegers(100000));
        Thread.sleep(1000);
        setGeneratedObject((T) generateArrayOfIntegers(1000000));
        Thread.sleep(1000);
        setGeneratedObject((T) generateArrayOfLongs(100));
        Thread.sleep(1000);
        setGeneratedObject((T) generateArrayOfLongs(1000));
        Thread.sleep(1000);
        setGeneratedObject((T) generateArrayOfLongs(10000));
        Thread.sleep(1000);
        setGeneratedObject((T) generateArrayOfLongs(100000));
        Thread.sleep(1000);
        setGeneratedObject((T) generateArrayOfLongs(1000000));
        Thread.sleep(1000);
        setGeneratedObject((T) generateArrayOfStrings(100));
        Thread.sleep(1000);
        setGeneratedObject((T) generateArrayOfStrings(1000));
        Thread.sleep(1000);
        setGeneratedObject((T) generateArrayOfStrings(10000));
        Thread.sleep(1000);
        setGeneratedObject((T) generateArrayOfStrings(100000));
        Thread.sleep(1000);
        setGeneratedObject((T) generateArrayOfStrings(1000000));
    }
}
