package ru.otus.l021;

import org.openjdk.jol.info.ClassLayout;
import org.openjdk.jol.info.GraphLayout;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;


public class MemoryCounter<T extends Object> implements PropertyChangeListener {

    ObjectFactory objectFactory;

    public MemoryCounter(ObjectFactory objectFactory){
        this.objectFactory = objectFactory;
        objectFactory.addPropertyChangeListener(this);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        printObjectSize(objectFactory.getGeneratedObject());
    }


    public static <T extends Object> void printObjectSize(T t){
        System.out.println(ClassLayout.parseClass(t.getClass()).toPrintable());
        System.out.println(GraphLayout.parseInstance(t).toFootprint());
    }
}
