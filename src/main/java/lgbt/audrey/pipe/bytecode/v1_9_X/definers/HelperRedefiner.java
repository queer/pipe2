package lgbt.audrey.pipe.bytecode.v1_9_X.definers;

import lgbt.audrey.pipe.bytecode.Redefiner;
import lgbt.audrey.pipe.bytecode.v1_9_X.generators.HelperGenerator;
import lgbt.audrey.pipe.util.helpers.Helper;

import java.lang.instrument.ClassDefinition;

/**
 * Redefines the Helper class when the JAR is being instrumented
 *
 * @author c
 * @since 5/23/15
 */
public class HelperRedefiner implements Redefiner {
    @Override
    public ClassDefinition redefine() {
        return new ClassDefinition(Helper.class, new HelperGenerator().generate());
    }
}
