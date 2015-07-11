package me.curlpipesh.pipe.event.events;

import lombok.Getter;
import me.curlpipesh.pipe.util.Cancellable;

/**
 * Event that represents a chat message being sent by the client
 *
 * @author c
 * @since 5/27/15
 */
public class ChatSend extends Cancellable {
    /**
     * The message being sent
     */
    @Getter
    private String message;

    public ChatSend(String message) {
        this.message = message;
    }
}
