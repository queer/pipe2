package me.curlpipesh.pipe.event.events;

import lombok.Data;

/**
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
