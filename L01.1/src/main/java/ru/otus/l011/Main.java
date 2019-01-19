package ru.otus.l011;

import com.google.common.collect.Collections2;
import com.google.common.collect.Lists;
import java.util.ArrayList;


/**
 * Homework by A.Vasilyev.
 *
 * Code for L01.1
 *
 * To start the application:
 * mvn package
 * java -cp ./target/L01.1-maven.jar ru.otus.l011.Main
 * java -jar ./target/L01.1-maven.jar //java.lang.NoClassDefFoundError: com/google/common/collect/Lists
 * java -cp ./target/L01.1-maven.jar;C:\Users\Tully\.m2\repository\com\google\guava\guava\25.0-jre\guava-25.0-jre.jar ru.otus.l011.Main
 *
 * To unzip the jar:
 * 7z x -oJAR ./target/L01.1-maven.jar
 * unzip -d JAR ./target/L01.1-maven.jar
 *
 * To build:
 * mvn package
 * mvn clean compile
 * mvn assembly:single
 * mvn clean compile assembly:single
 */
public class Main {
    private static final int MEASURE_COUNT = 1;

    public static void main(String... args) {
        ArrayList<Integer> example = Lists.newArrayListWithCapacity(1_000_000);
        int min = 0;
        int max = 999_999;
        for (int i = min; i < max + 1; i++) {
            example.add(i);
        }

        ArrayList<Integer> result = Lists.newArrayListWithCapacity(1_000_000);
        Collections2.transform(example,(n) -> n*2);
        calcTime(() -> result.addAll(Lists.reverse(example)));
    }

    private static void calcTime(Runnable runnable) {
        long startTime = System.nanoTime();
        for (int i = 0; i < MEASURE_COUNT; i++)
            runnable.run();
        long finishTime = System.nanoTime();
        long timeNs = (finishTime - startTime) / MEASURE_COUNT;
        System.out.println("Time spent: " + timeNs + "ns (" + timeNs / 1_000_000 + "ms)");
    }
}