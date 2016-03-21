package lgbt.audrey.pipe.event.events;

import lgbt.audrey.pipe.util.Cancellable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author audrey
 * @since 10/8/15.
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class PacketSend extends Cancellable {
    @SuppressWarnings("FieldMayBeFinal")
    private Object packet;

    public PacketSend(final Object packet) {
        this.packet = packet;
    }
}
