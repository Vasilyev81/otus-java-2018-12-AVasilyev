package ru.otus.L061;

import java.lang.ref.SoftReference;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

@SuppressWarnings("unchecked")
public class SoftRefCacheEngine<K, V> {
	private Map<K, SoftReference<V>> storage;
	private final Integer DEFAULT_MAX_CAPACITY = 1000;
	private Integer maxCapacity;
	private Integer hits;
	private Integer misses;
	private Map externalStorage;

	public void setExternalStorage(Map externalStorage) {
		this.externalStorage = externalStorage;
	}

	public SoftRefCacheEngine() {
		this.storage = new LinkedHashMap();
		maxCapacity = DEFAULT_MAX_CAPACITY;
		hits = 0;
		misses = 0;
	}

	public SoftRefCacheEngine(Integer maxCapacity) {
		storage = new LinkedHashMap();
		this.maxCapacity = maxCapacity;
		hits = 0;
		misses = 0;
	}

	public void put(K key, V value) {
		if (storage.size() > maxCapacity) {
			Iterator iter = storage.keySet().iterator();
			storage.remove(iter.next());
		}
		SoftReference val = new SoftReference<>(value);
		storage.put(key, val);
	}

	public V get(K key) {
		V value = (V) ((SoftReference) storage.get(key)).get();
		if (value == null) {
			misses++;
			value = getValueFromExternalStorage(key);
			SoftReference val = new SoftReference<>(value);
			storage.put(key, val);
		} else hits++;
		return value;
	}

	private V getValueFromExternalStorage(K key) {
		//V value = (V) externalStorage.get(key);
		V value = (V) (new MyObject(1000));
		return value;
	}

	public int getHitCount() {
		return hits;
	}

	public int getMissCount() {
		return misses;
	}

	public void clear() {
		storage.clear();
	}

	int getSize() {
		return storage.size();
	}

	public void resetStatistic() {
		hits = 0;
		misses = 0;
	}

	public String getStatistic() {
		StringBuilder sb = new StringBuilder();
		return sb.append("Cache hits: ").append(getHitCount())
				.append("\n")
				.append("Cache misses: ").append(getMissCount())
				.toString();
	}
}

