package me.curlpipesh.pipe.plugin.serialization;

import com.google.gson.*;
import me.curlpipesh.pipe.plugin.PluginManifest;

import java.lang.reflect.Type;

/**
 * @author audrey
 * @since 10/6/15.
 */
public class ManifestDeserializer implements JsonDeserializer<PluginManifest> {
    @Override
    public PluginManifest deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        PluginManifest pluginManifest = new PluginManifest();
        JsonObject result = json.getAsJsonObject();
        if(!result.has("name")) {
            throw new IllegalArgumentException("No name present in manifest.");
        }
        if(!result.has("main")) {
            throw new IllegalArgumentException("No main present in manifest.");
        }
        if(result.has("disabled")) {
            boolean disabled = result.get("disabled").getAsBoolean();
            pluginManifest.setDisabled(disabled);
        }
        if(result.has("author")) {
            String author = result.get("author").getAsString();
            pluginManifest.setAuthor(author);
        }
        if(result.has("description")) {
            String description = result.get("description").getAsString();
            pluginManifest.setDescription(description);
        }
        String name = result.get("name").getAsString();
        String mainClass = result.get("main").getAsString();
        pluginManifest.setName(name);
        pluginManifest.setMainClass(mainClass);
        return pluginManifest;
    }
}
