package me.curlpipesh.pipe.util.helpers;

import me.curlpipesh.pipe.Pipe;
import me.curlpipesh.pipe.event.events.ChatMessage;
import me.curlpipesh.pipe.event.events.ChatMessage.ChatMode;

import java.util.Arrays;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

@SuppressWarnings({"unused", "ConfusingOctalEscapeSequence"})
public final class ChatHelper {
    /**
     * The {@link Logger} implementation for this class. Uses a custom
     * {@link Handler} to log messages to the in-game chat.
     */
    private static final Logger logger = Logger.getLogger("PipeChat");

    private ChatHelper() {
    }

    /**
     * Handles the "redirection" of chat messages. Method is "unused" because
     * the code that uses it is generated at runtime.
     *
     * @param message The message to tinker with.
     */
    public static void handle(final String message) {
        if(!Pipe.getInstance().getEventBus().push(new ChatMessage(message, ChatMode.SEND)).isCancelled()) {
            Helper._sendChatMessage(message);
            // TODO: Add to sent chat messages
        }
    }

    /**
     * Logs a set of "INFO" messages to the chat
     *
     * @param messages The messages to log
     */
    public static void log(final String... messages) {
        Arrays.stream(messages).forEach(logger::info);
    }

    /**
     * Logs a set of "DEBUG" messages to the chat
     *
     * @param messages The messages to log
     */
    public static void debug(final String... messages) {
        if(Pipe.getInstance().isInDebugMode()) {
            Arrays.stream(messages).forEach(m -> logger.info("\2478[\247cDebug\2478]\247r " + m));
        }
    }

    /**
     * Logs a set of "WARNING" messages to the chat
     *
     * @param messages The messages to log
     */
    public static void warn(final String... messages) {
        Arrays.stream(messages).forEach(logger::warning);
    }

    static {
        //noinspection AnonymousInnerClassWithTooManyMethods
        logger.addHandler(new Handler() {
            @Override
            public void publish(final LogRecord logRecord) {
                final String levelColor =
                        logRecord.getLevel().equals(Level.SEVERE) ? "\2474" :
                                logRecord.getLevel().equals(Level.WARNING) ? "\247c" :
                                        logRecord.getLevel().equals(Level.INFO) ? "\247e" : "\247b";

                Helper.addChatMessage(String.format("\2478[\247dPipe\2478]\247r \2477[%s%s\2477]\247r %s", levelColor, logRecord.getLevel(),
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
