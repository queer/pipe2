package me.curlpipesh.pipe.gui.api.controller.registry;

import lombok.Getter;
import me.curlpipesh.pipe.Pipe;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * {@code Taggable} is exactly that - it is a class that makes application of
 * GUI tags using
 * {@link TagRegistry}
 * simple. Classes that extend this will be able to store, query, and
 * manipulate arbitrary metadata tags that have been attached to them, allowing
 * for information to be stored and transmitted in an easier way.
 *
 * @author c
 * @since 08.19.2014
 */
public class Taggable implements ITaggable {
    /**
     * A list of the tags that this widget has. Tags are essentially a metadata
     * system, although their main usage is for differentiating between the
     * different types of components so that sorting and rendering becomes
     * easier. Outside of basic render sorting, tags can be used to let other
     * widgets know something about this widget, they can be used for special
     * render effects, and probably other things as well.
     *
     * TODO Map to a String[] or a List or something?
     */
    @Getter
    private final Map<String, String> tags = new ConcurrentHashMap<>();

    @Override
    public final String getTagValue(String tagName) {
        return tags.getOrDefault(tagName, "Unknown tag!");
    }

    @Override
    public final void addTag(String tag, String value) {
        if(!TagRegistry.validateTag(tag) || !TagRegistry.validateTagValue(tag, value)) {
            throw new IllegalArgumentException(String.format("Illegal tag/value pair: (%s, %s)", tag, value));
        }
        if(!tags.containsKey(tag)) {
            tags.put(tag, value);
        } else {
            tags.replace(tag, value);
        }
    }

    @Override
    public final void editTag(String tag, String value) {
        if(!TagRegistry.validateTag(tag) || !TagRegistry.validateTagValue(tag, value)) {
            throw new IllegalArgumentException(String.format("Illegal tag/value pair: (%s, %s)", tag, value));
        }
        if(!tags.containsKey(tag)) {
            Pipe.getLogger().warning(String.format("%s: Tag %s is not applicable in this context (%s)",
                    toString(), tag, toString()));
        } else {
            if(tags.replace(tag, value) == null) {
                throw new NullPointerException(String.format("%s: Unable to replace tag! (%s => %s)", toString(), tag, value));
            }
        }
    }
}
