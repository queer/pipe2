package me.curlpipesh.pipe.config;

/**
 * Generic option. Options require a name and a value
 *
 * @param <T> The type of value that this option will store
 * @author c
 * @since 5/23/15
 */

public interface Option<T> {
    /**
     * Returns the value stored in this option
     *
     * @return The currently stored value
     */
    T get();

    /**
     * Sets the value of this option
     *
     * @param t The new value for the option to store
     */
    void set(T t);

    /**
     * Sets the value of this option to the value contained in the given
     * string. This must be implemented by the "final" implementations of this
     * class, as the parsing of a given String is highly
     * "implementation-dependant," so to speak.
     *
     * @param string The String containing a value to set to.
     */
    void set(String string);

    /**
     * Returns the name of this option
     *
     * @return The name of this option
     */
    String name();
}
