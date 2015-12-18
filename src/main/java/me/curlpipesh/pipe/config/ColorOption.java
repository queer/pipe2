package me.curlpipesh.pipe.config;

import me.curlpipesh.pipe.util.helpers.ChatHelper;

/**
 * Option for storing a color. Colors need to be passed in using the
 * <tt>0xAARRGGBB</tt> format.
 *
 * @author c
 * @since 6/1/15
 */
public class ColorOption extends NumberOption<Integer> {
    public ColorOption(String name, Integer defaultValue) {
        super(name, defaultValue);
    }

    @Override
    public Class<Integer> getNumberType() {
        return Integer.class;
    }

    @Override
    public void set(String string) {
        try {
            set(Long.decode(string).intValue());
        } catch(Exception e) {
            ChatHelper.warn("Failed parsing '" + string + "': " + e.getLocalizedMessage(), "Falling back to hex-parse!");
            e.printStackTrace();
            try {
                set(Integer.parseInt(string.replaceFirst("0x", ""), 16));
            } catch(Exception e1) {
                ChatHelper.warn("Failed parsing '" + string + "': " + e.getLocalizedMessage(), "Falling back to dec-parse!");
                e1.printStackTrace();
                set(Integer.parseInt(string));
            }
        }
    }
}
