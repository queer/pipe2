package me.curlpipesh.pipe.util;

import lombok.Data;

/**
 * Something that can be cancelled.
 *
 * @author c
 * @since 7/10/15
 */
@Data
public abstract class Cancellable {
    /**
     * Whether or not the thing has been cancelled
     */
    private boolean cancelled;
}
