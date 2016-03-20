package lgbt.audrey.pipe.event.events;

import lombok.Data;

/**
 * @author audrey
 * @since 10/8/15.
 */
@Data
public class PacketSend {
    @SuppressWarnings("FieldMayBeFinal")
    private Object packet;
    @SuppressWarnings("FieldMayBeFinal")
    private boolean cancelled;

    public PacketSend(final Object packet) {
        this.packet = packet;
    }
}
