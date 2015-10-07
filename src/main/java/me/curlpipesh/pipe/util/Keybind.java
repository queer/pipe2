package me.curlpipesh.pipe.util;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * Representation of a key binding
 *
 * @author c
 * @since 5/23/15
 */
public class Keybind {
    /**
     * The main key that is bound
     */
    @Getter
    @Setter
    private int key;

    /**
     * Modifier keys that must be held down before the keybind can be used.
     */
    private final List<Integer> modifiers = new ArrayList<>();

    /**
     * Creates a new keybind with the given key
     * @param key The main key of the key binding
     */
    public Keybind(int key) {
        this.key = key;
    }

    /**
     * Adds a modifier key
     *
     * @param mod The modifier key to add
     */
    public Keybind withModifier(int mod) {
        if(!modifiers.contains(mod)) {
            modifiers.add(mod);
        }
        return this;
    }

    /**
     * Removes a modifier key
     *
     * @param mod The modifier key to remove
     */
    public Keybind removeModifier(int mod) {
        if(modifiers.contains(mod)) {
            modifiers.remove(mod);
        }
        return this;
    }

    /**
     * Returns all the current modifier keys of this key binding
     *
     * @return An <tt>Integer[]</tt> of all current modifier keys
     */
    public Integer[] getModifiers() {
        return modifiers.stream().toArray(Integer[]::new);
    }
}
