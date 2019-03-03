package ru.otus.L061;

public class DummyExternalStorage implements ExternalStorage {

	@Override
	public <V, K> V get(K key) {
		return (V) (new MyObject(1000));
	}
}
