package ru.otus.l021;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;

public class ObjectFactory <T extends Object> {

    private T generatedObject;

    public ObjectFactory(){
        this.generatedObject = null;
    }

    private void setGeneratedObject(T newObject) {
        T oldObject = generatedObject;
        generatedObject = newObject;
        changeSupport.firePropertyChange("generatedObject", oldObject, newObject);
    }

    public T getGeneratedObject(){
        return generatedObject;
    }

    private PropertyChangeSupport changeSupport = new PropertyChangeSupport(this);

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        changeSupport.addPropertyChangeListener(listener);
    }

    public void removeChangeListener(PropertyChangeListener listener) {
        changeSupport.removePropertyChangeListener(listener);
    }

    public void generateObjects() throws InterruptedException{
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
    }
}
