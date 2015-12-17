package me.curlpipesh.pipe.event.events;

import lombok.Getter;
import me.curlpipesh.pipe.util.Cancellable;

/**
 * Event that represents a chat message being sent by the client
 *
 * @author c
 * @since 5/27/15
 */
public class ChatMessage extends Cancellable {
    /**
     * The message being sent
     */
    @Getter
    private final String message;

    @Getter
    private final ChatMode mode;

    public ChatMessage(String message, ChatMode mode) {
        this.message = message;
        this.mode = mode;
    }

    public enum ChatMode {
        SEND, RECEIVE
    }
}
