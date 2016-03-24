package lgbt.audrey.pipe.event.events;

import lombok.Getter;

/**
 * @author audrey
 * @since 3/23/16.
 */
public class RenderEntity {
    @Getter
    private final Object entity;

    public RenderEntity(final Object e) {
        entity = e;
    }
}
