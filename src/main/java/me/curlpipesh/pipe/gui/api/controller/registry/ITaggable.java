package me.curlpipesh.pipe.gui.api.controller.registry;

public interface ITaggable {
    /**
     * Returns the value of a given tag
     *
     * @param tagName The tag whose value is being accessed.
     * @return The value of the tag, or "Unknown Tag!" if the tag does not
     *         exist.
     */
    String getTagValue(String tagName);

    /**
     * Adds a tag to the widget with the given value. If the tag already
     * exists, then its value is merely updated.
     *
     * @param tag The tag to add or edit.
     * @param value The value to be set on the tag.
     */
    void addTag(String tag, String value);

    /**
     * Replaces the value mapped to the given tag with the given value. If the
     * tag does not exist, a warning is logged to {@link System#out},
     * and nothing is done.
     *
     * @param tag The tag to edit.
     * @param value The value to be set on the tag.
     */
    void editTag(String tag, String value);
}
