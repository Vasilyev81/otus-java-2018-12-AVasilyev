package ru.otus.L061;
@SuppressWarnings("unchecked")
public interface ExternalStorage {
	<V, K> V get(K key);
}
