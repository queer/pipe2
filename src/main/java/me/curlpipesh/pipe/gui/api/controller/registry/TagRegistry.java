package me.curlpipesh.pipe.gui.api.controller.registry;

import java.util.*;

/**
 * A "registry" of all the metadata tags that a component can have. A tag is
 * mapped to a {@link List} of possible values that it can have; it
 * is thus not ideal to use tags to store numerical values, but to instead
 * store less-mutable values such as a render type.
 *
 * @author c
 * @since 08.17.2014
 */
@SuppressWarnings({"unchecked", "unused"})
public final class TagRegistry {
    /**
     * A {@link Map} of possible tags mapped to arrays of the all of
     * the possible values thereof.
     */
    private static final Map<String, List<String>> legalTags = new HashMap<>();

    /**
     * Registers the basic tags that are essential to the functioning of the
     * UI. It is possible to remove this and handle these yourself, but this
     * is <b>NOT</b> advised! Also, you should <b>NOT</b> add all your of your
     * custom tags here, but instead register them all immediately prior to UI
     * instantiation.
     */
    static {
        registerTag("type", new String[] {
                "container", "label", "button", "window", "slider"
        });
        registerTag("state", new String[] {
                "true", "false"
        });
        registerTag("control-type", new String[] {
                "close", "pin", "minimize"
        });
        registerTag("render-focus", new String[] {
                "true", "false"
        });
        registerTag("closeable", new String[] {
                "true", "false"
        });
        registerTag("minimizable", new String[] {
                "true", "false"
        });
    }

    /**
     * Registers a tag with the given valueset. If the tag already exists, no
     * changes are made.
     *
     * @param tag The tag to be added.
     * @param values The values that the tag can have.
     */
    public static void registerTag(final String tag, final String[] values) {
        synchronized (legalTags) {
            if (!legalTags.containsKey(tag)) {
                ArrayList<String> vals = new ArrayList<>();
                Arrays.asList(values).stream().sequential().forEach(vals::add);
                legalTags.put(tag, vals);
            }
        }
    }

    /**
     * Unregisters a tag, as well as the values that go with it. If the tag
     * does not exist, no changes are made.
     *
     * @param tag The tag to be removed.
     */
    public static void unregisterTag(final String tag) {
        synchronized (legalTags) {
            if (legalTags.containsKey(tag)) {
                legalTags.remove(tag);
            }
        }
    }

    /**
     * Replaces the valueset of the given tag with a new valueset. If the tag
     * does not exist, no changes are made.
     *
     * @param tag The tag to be edited.
     * @param newValues The new valueset for the tag being edited.
     */
    public static void editTag(final String tag, final String[] newValues) {
        synchronized (legalTags) {
            if (legalTags.containsKey(tag)) {
                legalTags.remove(tag);
                ArrayList<String> vals = new ArrayList<>();
                Arrays.asList(newValues).stream().sequential().forEach(vals::add);
                legalTags.put(tag, vals);
            }
        }
    }

    /**
     * Returns whether or not the given tag is a valid tag.
     *
     * @param tag The tag to be validated.
     * @return True if the tag is valid, false otherwise.
     */
    public static boolean validateTag(final String tag) {
        synchronized (legalTags) {
            if (legalTags.containsKey(tag)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns whether the value is a valid value for the tag provided.
     *
     * @param tag The tag whose valueset is being checked against.
     * @param value The value to validate.
     * @return True if the value is valid, false otherwise.
     */
    public static boolean validateTagValue(final String tag, final String value) {
        synchronized (legalTags) {
            if (legalTags.containsKey(tag)) {
                if (legalTags.get(tag).contains(value)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Adds a value to the valueset of the given tag.
     *
     * @param tag The tag that is having a value added to it.
     * @param value The value being added to the tag.
     */
    public static void addValueToTag(String tag, String value) {
        synchronized (legalTags) {
            if (legalTags.containsKey(tag)) {
                legalTags.get(tag).add(value);
            }
        }
    }
}
