package me.curlpipesh.pipe.bytecode;

import java.lang.instrument.ClassDefinition;

/**
 * @author audrey
 * @since 5/23/15
 */
public interface Redefiner {
    ClassDefinition redefine();
}
