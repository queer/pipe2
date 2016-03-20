package lgbt.audrey.pipe.event.events;

import lombok.Data;

/**
 * TODO: Both events as one class with differentiating field?
 *
 * @author audrey
 * @since 12/15/15.
 */
@SuppressWarnings("FieldMayBeFinal")
@Data
public class PacketReceive {
    private Object packet;
    private boolean cancelled;

    public PacketReceive(final Object packet) {
        this.packet = packet;
    }
}
