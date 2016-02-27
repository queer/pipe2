package me.curlpipesh.pipe.event.events;

/**
 * @author audrey
 * @since 2/27/16.
 */
@SuppressWarnings({"unused", "Singleton"})
public final class RenderFramebuffer {
    private RenderFramebuffer() {
    }

    @SuppressWarnings("StaticVariableOfConcreteClass")
    public static final RenderFramebuffer INSTANCE = new RenderFramebuffer();
}
