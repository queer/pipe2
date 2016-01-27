package me.curlpipesh.pipe.event.events;

import lombok.Data;

/**
 * Event for 3D renders
 *
 * @author c
 * @since 5/21/15
 */
@Data
public class Render3D {
    private final float partialTickTime;

    public Render3D(final float partialTickTime) {
        this.partialTickTime = partialTickTime;
    }
}
