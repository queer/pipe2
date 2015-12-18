package me.curlpipesh.pipe.gui.api.util;

import java.util.Map;

/**
 * Basic little tuple class that does all that it needs to do and nothing else.
 *
 * @param <K> Key object type
 * @param <V> Value object type
 */
public final class Tuple<K, V> implements Map.Entry<K, V> {
    /**
     * Key to be stored in the tuple. This is immutable, so that using a list
     * of tuples as sort of a map can be a thing.
     */
    private final K key;

    /**
     * Value mapped to the key
     */
    private V value;

    public Tuple(K key, V value) {
        this.key = key;
        this.value = value;
    }

    @Override
    public K getKey() {
        return key;
    }

    @Override
    public V getValue() {
        return value;
    }

    @Override
    public V setValue(final V v) {
        value = v;
        return value;
    }
}
