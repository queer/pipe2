package me.curlpipesh.pipe.event.events;

import lombok.Data;

/**
 * @author audrey
 * @since 10/8/15.
 */
@Data
public class PacketSend {
    private Object packet;
    private boolean cancelled = false;

    public PacketSend(Object packet) {
        this.packet = packet;
    }
}
