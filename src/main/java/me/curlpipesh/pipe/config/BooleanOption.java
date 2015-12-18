package me.curlpipesh.pipe.config;

/**
 * Option that contains a single boolean
 *
 * @author c
 * @since 5/23/15
 */
public class BooleanOption extends BasicOption<Boolean> {
    public BooleanOption(String name, Boolean defaultValue) {
        super(name, defaultValue);
    }

    @Override
    public void set(String string) {
        set(Boolean.valueOf(string));
    }
}
