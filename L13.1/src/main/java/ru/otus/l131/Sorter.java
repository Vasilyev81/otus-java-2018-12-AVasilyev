package ru.otus.l131;

import java.util.Arrays;

public class Sorter implements Runnable {
	private static int[] array;
	private int indexFrom;
	private int indexTo;
	private int[] sortedArray;

	public Sorter() {
	}

	private Sorter(int indexFrom, int indexTo) {
		this.indexFrom = indexFrom;
		this.indexTo = indexTo;
	}

	public int[] sort(int[] array){
		Sorter.array = array;
		int arrayLength = array.length;
		Sorter sorter1 = new Sorter(0, arrayLength / 4);
		Sorter sorter2 = new Sorter(arrayLength / 4, arrayLength / 2);
		Sorter sorter3 = new Sorter(arrayLength / 2, ((arrayLength / 4) * 3));
		Sorter sorter4 = new Sorter((arrayLength / 4) * 3 , arrayLength);

		Thread sortThread1 = new Thread(sorter1);
		Thread sortThread2 = new Thread(sorter2);
		Thread sortThread3 = new Thread(sorter3);
		Thread sortThread4 = new Thread(sorter4);
		sortThread1.start();
		sortThread2.start();
		sortThread3.start();
		sortThread4.start();
		try {
			sortThread1.join();
			sortThread2.join();
			sortThread3.join();
			sortThread4.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		Merger merger1 = new Merger(sorter1.getSortedArray(), sorter2.getSortedArray());
		Merger merger2 = new Merger(sorter3.getSortedArray(), sorter4.getSortedArray());
		Merger mergerFinal = new Merger(merger1.getMergedArray(), merger2.getMergedArray());
		return mergerFinal.getMergedArray();
	}

	@Override
	public void run() {
		int[] arr = Arrays.copyOfRange(Sorter.array, indexFrom, indexTo);
		Arrays.sort(arr);
		setSortedArray(arr);
	}

	private int[] getSortedArray() {
		return sortedArray;
	}

	private void setSortedArray(int[] array) {
		this.sortedArray = array;
	}

	private class Merger {
		private final int[] firstArray;
		private final int[] secondArray;
		private int[] mergedArray;

		private Merger(int[] firstArray, int[] secondArray) {
			this.firstArray = firstArray;
			this.secondArray = secondArray;
			merge();
		}

		private void merge() {
			int[] resultArray = new int[firstArray.length + secondArray.length];
			int firstArrayIndex = 0;
			int secondArrayIndex = 0;
			int resultArrayIndex = 0;
			while (firstArrayIndex < firstArray.length & secondArrayIndex < secondArray.length){
				if (firstArray[firstArrayIndex] == secondArray[secondArrayIndex]) {
					resultArray[resultArrayIndex++] = firstArray[firstArrayIndex++];
					resultArray[resultArrayIndex++] = secondArray[secondArrayIndex++];
				} else if (firstArray[firstArrayIndex] < secondArray[secondArrayIndex]){
					resultArray[resultArrayIndex++] = firstArray[firstArrayIndex++];
				}else{
					resultArray[resultArrayIndex++] = secondArray[secondArrayIndex++];
				}
			}
			if (firstArrayIndex < firstArray.length){
				while (firstArrayIndex < firstArray.length){
					resultArray[resultArrayIndex++] = firstArray[firstArrayIndex++];
				}
			}else if (secondArrayIndex < secondArray.length){
				while(secondArrayIndex < secondArray.length){
					resultArray[resultArrayIndex++] = secondArray[secondArrayIndex++];
				}
			}
			mergedArray = resultArray;
		}

		int[] getMergedArray() {
			return mergedArray;
		}
	}
}
