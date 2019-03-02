package ru.otus.L061;

public class CacheMain {
	public static void main(String[] args) throws InterruptedException {
		/*
		 * Tested with following VM options: -Xms256m -Xmx256m
		 */
		new CacheMain().myCache();
	}

	private void myCache() throws InterruptedException {

		int size = 1000;
		int myObjInitSize = 1024;
		SoftRefCacheEngine<Integer, MyObject> cache = new SoftRefCacheEngine<>(size);
		cache.setExternalStorage(new VirtualExternalStorage());
		for (int i = 0; i < size; i++) {
			cache.put(i, new MyObject(myObjInitSize));
			if (i % 10 == 0) {
				Thread.sleep(300);
				checkCacheForConsistency(cache);
			}
		}
		cache.clear();
	}

	private void checkCacheForConsistency(SoftRefCacheEngine<Integer, MyObject> cache) {
		cache.resetStatistic();
		for (int i = 0; i < cache.getSize(); i++) {
			String result = ((cache.get(i)).arraySize() == 1024 * 1024) ? "original." : "re-initiated!";
			System.out.println("MyObject in cache with key " + i + " is " + result);
		}
		System.out.println("*****************************************************************");
		System.out.println(cache.getStatistic());
	}
}
