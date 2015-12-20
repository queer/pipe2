package me.curlpipesh.pipe.config;

/**
 * Simple implementation of the {@link Option} interface.
 *
 * @param <T> The type of this option
 * @author c
 * @since 5/23/15
 */
public abstract class BasicOption<T> implements Option<T> {
    private final String name;
    private T value;

    public BasicOption(String name, T defaultValue) {
        this.name = name;
        value = defaultValue;
    }

    @Override
    public String name() {
        return name;
    }

    public T get() {
        return value;
    }

    public void set(T t) {
        value = t;
    }

    @Override
    public String toString() {
        return "" + value;
    }
}