package me.curlpipesh.pipe.bytecode.definers;

import me.curlpipesh.bytecodetools.define.Redefiner;
import me.curlpipesh.pipe.Pipe;
import me.curlpipesh.pipe.bytecode.generators.HelperGenerator;
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
        Pipe.getLogger().info("Redefining Helper!");
        return new ClassDefinition(Helper.class, HelperGenerator.generate());
    }
}
