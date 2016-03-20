package lgbt.audrey.pipe.plugin.serialization;

import com.google.gson.*;
import lgbt.audrey.pipe.plugin.PluginManifest;

import java.lang.reflect.Type;

/**
 * @author audrey
 * @since 10/6/15.
 */
public class ManifestDeserializer implements JsonDeserializer<PluginManifest> {
    @Override
    public PluginManifest deserialize(final JsonElement json, final Type typeOfT, final JsonDeserializationContext context) throws JsonParseException {
        final PluginManifest pluginManifest = new PluginManifest();
        final JsonObject result = json.getAsJsonObject();
        if(!result.has("name")) {
            throw new IllegalArgumentException("No name present in manifest.");
        }
        if(!result.has("main")) {
            throw new IllegalArgumentException("No main present in manifest.");
        }
        if(result.has("disabled")) {
            final boolean disabled = result.get("disabled").getAsBoolean();
            pluginManifest.setDisabled(disabled);
        }
        if(result.has("author")) {
            final String author = result.get("author").getAsString();
            pluginManifest.setAuthor(author);
        }
        if(result.has("description")) {
            final String description = result.get("description").getAsString();
            pluginManifest.setDescription(description);
        }
        final String name = result.get("name").getAsString();
        final String mainClass = result.get("main").getAsString();
        pluginManifest.setName(name);
        pluginManifest.setMainClass(mainClass);
        return pluginManifest;
    }
}
