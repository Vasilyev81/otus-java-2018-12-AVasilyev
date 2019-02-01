package ru.otus.L031;

import java.util.List;
import java.util.Random;

public class MyArrListUtil {

    public static <T> List<T> random(List<T> source) {
        Object swapObject;
        Random random = new Random();
        final int sourceSize = source.size();
        for (int i = 1; i <= source.size(); i++) {
            int index0 = random.nextInt(sourceSize);
            int index1 = random.nextInt(sourceSize);
            swapObject = source.get(index0);
            source.set(index0, source.get(index1));
            source.set(index1, (T) swapObject);
        }
        return source;
    }
}
