package ru.otus.L061;

class MyObject {
	private byte[] array;

	MyObject(int sqrSize) {
		this.array = new byte[sqrSize * sqrSize];
	}

	int arraySize() {
		return array.length;
	}
}
