package me.curlpipesh.pipe.event;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Siginifies that this event can ignore whether or not a
 * {@link me.curlpipesh.pipe.plugin.module.Module} is enabled.
 *
 * @author audrey
 * @since 10/6/15.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface IgnoreEnable {
}
