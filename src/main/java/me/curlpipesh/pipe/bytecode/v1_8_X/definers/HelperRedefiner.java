package me.curlpipesh.pipe.bytecode.v1_8_X.definers;

import me.curlpipesh.pipe.bytecode.Redefiner;
import me.curlpipesh.pipe.bytecode.v1_8_X.generators.HelperGenerator;
import me.curlpipesh.pipe.util.helpers.Helper;

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
        return new ClassDefinition(Helper.class, HelperGenerator.generate());
    }
}
