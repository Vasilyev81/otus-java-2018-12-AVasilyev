package ru.otus.l131;

import java.util.Arrays;
import java.util.Random;

public class Main {
	private final static int ARRAY_LENGTH_S = 5_000_000;
	private final static int ARRAY_LENGTH_M = 10_000_000;
	private final static int ARRAY_LENGTH_L = 20_000_000;

	public static void main(String[] args) {
		Sorter sorter = new Sorter();

		System.out.println("Array length: " + ARRAY_LENGTH_S);
		int[] randomArray_S = createArray(ARRAY_LENGTH_S);
		int[] randomArrayCopy_S = new int[ARRAY_LENGTH_S];
		System.arraycopy(randomArray_S, 0, randomArrayCopy_S, 0, ARRAY_LENGTH_S);

		long start1 = System.currentTimeMillis();
		int[] sortedArray_S = sorter.sort(randomArray_S);
		System.out.println("Time spend by Sorter.sort: " + (System.currentTimeMillis() - start1) + "ms");

		long start2 = System.currentTimeMillis();
		Arrays.sort(randomArrayCopy_S);
		System.out.println("Time spend by JVM Array.sort: " + (System.currentTimeMillis() - start2) + "ms");



		System.out.println("Array length: " + ARRAY_LENGTH_M);
		int[] randomArray_M = createArray(ARRAY_LENGTH_M);
		int[] randomArrayCopy_M = new int[ARRAY_LENGTH_M];
		System.arraycopy(randomArray_M, 0, randomArrayCopy_M, 0, ARRAY_LENGTH_M);

		long start3 = System.currentTimeMillis();
		int[] sortedArray_M = sorter.sort(randomArray_M);
		System.out.println("Time spend by Sorter.sort: " + (System.currentTimeMillis() - start3) + "ms");

		long start4 = System.currentTimeMillis();
		Arrays.sort(randomArrayCopy_M);
		System.out.println("Time spend by JVM Array.sort: " + (System.currentTimeMillis() - start4) + "ms");


		System.out.println("Array length: " + ARRAY_LENGTH_L);
		int[] randomArray_L = createArray(ARRAY_LENGTH_L);
		int[] randomArrayCopy_L = new int[ARRAY_LENGTH_L];
		System.arraycopy(randomArray_L, 0, randomArrayCopy_L, 0, ARRAY_LENGTH_L);

		long start5 = System.currentTimeMillis();
		int[] sortedArray_L = sorter.sort(randomArray_L);
		System.out.println("Time spend by Sorter.sort: " + (System.currentTimeMillis() - start5) + "ms");

		long start6 = System.currentTimeMillis();
		Arrays.sort(randomArrayCopy_L);
		System.out.println("Time spend by JVM Array.sort: " + (System.currentTimeMillis() - start6) + "ms");
	}

	private static int[] createArray(int arrayLength) {
		int[] array = new int[arrayLength];
		Random rnd = new Random();
		for (int i = 0; i < arrayLength; i++) {
			array[i] = rnd.nextInt();
		}
		return array;
	}
}