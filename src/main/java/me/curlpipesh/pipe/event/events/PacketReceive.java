package me.curlpipesh.pipe.event.events;

import lombok.Data;

/**
 * TODO: Both events as one class with differentiating field?
 *
 * @author audrey
 * @since 12/15/15.
 */
@Data
public class PacketReceive {
    private Object packet;
    private boolean cancelled = false;

    public PacketReceive(Object packet) {
        this.packet = packet;
    }
}
