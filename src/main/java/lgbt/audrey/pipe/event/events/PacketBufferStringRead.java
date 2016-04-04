package lgbt.audrey.pipe.event.events;

import lombok.Data;

/**
 * Event fired when a <tt>String</tt> is read from the packet buffer. This
 * event exists for any modules that happen to need access to the raw JSON
 * chat messages.
 *
 * @author audrey
 * @since 12/15/15.
 */
@Data
public class PacketBufferStringRead {
    private final String string;

    public PacketBufferStringRead(final String string) {
        this.string = string;
    }
}
