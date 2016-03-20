package lgbt.audrey.pipe.event.events;

import lgbt.audrey.pipe.util.Cancellable;
import lombok.Getter;
import lgbt.audrey.pipe.util.Cancellable;

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

    public ChatMessage(final String message, final ChatMode mode) {
        this.message = message;
        this.mode = mode;
    }

    @SuppressWarnings("unused")
    public enum ChatMode {
        SEND, RECEIVE
    }
}
