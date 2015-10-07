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
        String name = result.get("name").getAsString();
        String author = result.get("author").getAsString();
        String description = result.get("description").getAsString();
        String mainClass = result.get("main").getAsString();
        pluginManifest.setName(name);
        pluginManifest.setAuthor(author);
        pluginManifest.setDescription(description);
        pluginManifest.setMainClass(mainClass);
        return pluginManifest;
    }
}
