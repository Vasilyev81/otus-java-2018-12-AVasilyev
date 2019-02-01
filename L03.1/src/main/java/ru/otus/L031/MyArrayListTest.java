package ru.otus.L031;

import java.util.*;
import java.util.stream.IntStream;

public class MyArrayListTest {

    public static void main(String[] args){

        List<String> myList = new MyArrayList<>();

        List<String> stdArrList = Arrays.asList("ABC", "DEF", "GHI", "JKL", "MNO", "PQR", "STW", "XYZ");
        System.out.println("Standard ArrayList:");
        System.out.println(stdArrList.toString());

        myList.addAll(stdArrList);

        System.out.println("myArrList: " + myList.toString());

        for (int i = 0; i < 5; i++) {
            for (String element : stdArrList) {
                myList.add(0, element);
                System.out.println(myList.toString());
                //Thread.sleep(700);
            }
        }

        myList.clear();

        for (int i = 0; i < 5; i++) {
            for (String element : stdArrList) {
                myList.add(element);
                System.out.println(myList.toString());
            }
        }

        List<Integer> myIntList = new MyArrayList<>();
        IntStream.range(1, 81).forEach(myIntList::add);
        System.out.println(myIntList.toString());

        myList.addAll(stdArrList);
        myList.add(1, "100");
        myList.add(3, "100");

        System.out.println("MyArrayList after adding stdArrList and 100 in positions 1 and 3, access by Iterator:");
        Iterator itr = myList.iterator();
        while (itr.hasNext()) {
            System.out.print(itr.next() + " | ");
        }

        while (itr.hasNext()) {
            if (itr.next().equals("100")) itr.remove();
        }
        System.out.println("MyArrayList after removing all 100, access by Iterator:");
        while (itr.hasNext()) {
            System.out.print(itr.next() + " | ");
        }

        System.out.println("MyArrayList random:");
        myList = MyArrListUtil.random(myList);
        System.out.println(myList.toString());

        System.out.println("Collections.copy()");
        Collections.copy(myList, stdArrList);
        System.out.println(myList.toString());


        System.out.println("MyArrayList sort:");
        myList.sort(String::compareTo);
        System.out.println(myList.toString());

        System.out.println("MyArrayList add single object without index:");
        myList.add("FOO");
        System.out.println(myList.toString());

        System.out.println("Collections.addAll()");
        Collections.addAll(myList, "ABC", "DEF", "GHI", "JKL", "MNO", "PQR", "STW", "XYZ");
        System.out.println(myList.toString());

        System.out.println("myArrList.clear()");
        myList.clear();
        System.out.println(myList.isEmpty());
        System.out.println(myList.toString());

        System.out.println("Collections.sort()");
        Collections.sort(myList);
        System.out.println(myList.toString());

        System.out.println("Collections.copy()");
        try {
            Collections.copy(myList, stdArrList); //Must throw IndexOutOfBoundException
        } catch (IndexOutOfBoundsException e) {
            System.out.println(e);
        }
    }
}