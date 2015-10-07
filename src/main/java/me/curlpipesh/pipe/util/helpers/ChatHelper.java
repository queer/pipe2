package me.curlpipesh.pipe.util.helpers;

import me.curlpipesh.pipe.Pipe;
import me.curlpipesh.pipe.bytecode.injectors.GuiChatInjector;
import me.curlpipesh.pipe.event.events.ChatSend;

import java.util.Arrays;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

/**
 * Used in {@link GuiChatInjector} for
 * redirecting the chat messages to our custom "handler," so that we can push
 * {@link ChatSend} events without too much pain.
 *
 * @author c
 * @since 5/27/15
 */
@SuppressWarnings("unused")
public class ChatHelper {
    /**
     * The {@link Logger} implementation for this class. Uses a custom
     * {@link Handler} to log messages to the in-game chat.
     */
    private static final Logger logger = Logger.getLogger("Pipe");

    /**
     * Handles the "redirection" of chat messages. Method is "unused" because
     * the code that uses it is generated at runtime.
     *
     * @param message The message to tinker with.
     */
    public static void handle(String message) {
        if(!Pipe.getInstance().getEventBus().push(new ChatSend(message)).isCancelled()) {
            Helper._sendChatMessage(message);
            // TODO: Add to sent chat messages
        }
    }

    /**
     * Logs a set of "INFO" messages to the chat
     *
     * @param messages The messages to log
     */
    public static void log(String... messages) {
        Arrays.stream(messages).forEach(logger::info);
    }

    /**
     * Logs a set of "DEBUG" messages to the chat
     *
     * @param messages The messages to log
     */
    public static void debug(String... messages) {
        Arrays.stream(messages).forEach(m -> logger.info("§8[§cDebug§8]§r " + m));
    }

    /**
     * Logs a set of "WARNING" messages to the chat
     *
     * @param messages The messages to log
     */
    public static void warn(String... messages) {
        Arrays.stream(messages).forEach(logger::warning);
    }

    static {
        logger.addHandler(new Handler() {
            @Override
            public void publish(LogRecord logRecord) {
                String levelColor =
                        logRecord.getLevel().equals(Level.SEVERE) ? "§4" :
                                logRecord.getLevel().equals(Level.WARNING) ? "§c" :
                                        logRecord.getLevel().equals(Level.INFO) ? "§e" : "§b";

                Helper.addChatMessage(String.format("§8[§dPipe§8]§r §7[%s%s§7]§r %s", levelColor, logRecord.getLevel(),
                        logRecord.getMessage()));
            }

            @Override
            public void flush() {
            }

            @Override
            public void close() throws SecurityException {
            }
        });
        logger.setUseParentHandlers(false);
    }
}
