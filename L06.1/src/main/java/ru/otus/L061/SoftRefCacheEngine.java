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
	private ExternalStorage externalStorage;

	public void setExternalStorage(ExternalStorage externalStorage) {
		this.externalStorage = externalStorage;
	}

	public SoftRefCacheEngine() {
		storage = new LinkedHashMap();
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
		SoftReference softRef = storage.get(key);
		if (softRef != null || softRef.get() != null) {
			hits++;
			return (V) softRef.get();
		} else {
			misses++;
			V value = getValueFromExternalStorage(key);
			this.put(key, value);
			return value;
		}
	}

	private V getValueFromExternalStorage(K key) {
		return (V) externalStorage.get(key);
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

