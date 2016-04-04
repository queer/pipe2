package lgbt.audrey.commandPlugin.external;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import lgbt.audrey.pipe.command.Command;
import lgbt.audrey.pipe.command.CommandException;
import lgbt.audrey.pipe.command.CommandExecutor;
import lgbt.audrey.pipe.util.helpers.ChatHelper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayDeque;
import java.util.Date;
import java.util.Deque;

/**
 * Gets all the names of the given player
 *
 * @author audrey
 * @since 4/3/16.
 */
public class CommandNames implements CommandExecutor {
    private static final String NAME_TO_UUID_API = "https://api.mojang.com/users/profiles/minecraft/%name%?at=%time%";
    private static final String UUID_TO_NAME_HISTORY_API = "https://api.mojang.com/user/profiles/%uuid%/names";

    private final SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

    @Override
    public boolean executeCommand(final Command command, final String commandString, final String[] args) throws CommandException {
        if(args.length > 0) {
            // Ugly ;-;
            new Thread(() -> {
                try {
                    String uuid = sendGetRequest(NAME_TO_UUID_API
                            .replace("%name%", args[0]).replace("%time%", "" + System.currentTimeMillis()))
                            .replaceAll("\\{\"id\":\"", "").replaceAll("\",(.*)$", "");
                    String historyJson = sendGetRequest(UUID_TO_NAME_HISTORY_API.replace("%uuid%", uuid));
                    final JsonArray actualArray = new JsonParser().parse(historyJson).getAsJsonArray();
                    
                    final Deque<String> nameDeque = new ArrayDeque<>();
                    for(final JsonElement element : actualArray) {
                        JsonObject o = element.getAsJsonObject();
                        //noinspection ConfusingOctalEscapeSequence
                        String info = "\247c" + o.get("name").getAsString() + "\2477";
                        if(o.has("changedToAt")) {
                            info += ": " + df.format(new Date(o.get("changedToAt").getAsLong()));
                        } else {
                            info += " (ORIGINAL)";
                        }
                        nameDeque.push(info);
                    }
                    //noinspection ConfusingOctalEscapeSequence
                    nameDeque.push("\2477UUID: \247c" + uuid);
                    //noinspection ConfusingOctalEscapeSequence
                    nameDeque.push("\2477Names for: \247c" + args[0]);

                    while(!nameDeque.isEmpty()) {
                        ChatHelper.log(nameDeque.pop());
                    }
                } catch(IOException e) {
                    e.printStackTrace();
                }
            }).start();
            return true;
        }
        return false;
    }

    private String sendGetRequest(final String urlToRead) throws IOException {
        final StringBuilder result = new StringBuilder();
        final URL url = new URL(urlToRead);
        final HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        final BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String line;
        while ((line = rd.readLine()) != null) {
            result.append(line);
        }
        rd.close();
        return result.toString();
    }
}
