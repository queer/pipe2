package lgbt.audrey.pipe.bytecode;

import java.lang.instrument.ClassDefinition;

/**
 * @author audrey
 * @since 5/23/15
 */
@FunctionalInterface
public interface Redefiner {
    ClassDefinition redefine();
}
