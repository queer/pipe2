package me.curlpipesh.pipe.bytecode;

/**
 * @author audrey
 * @since 12/18/15.
 */
@SuppressWarnings("unused")
public interface Version {
    String getVersion();

    Injector[] getInjectors();

    Generator[] getGenerators();

    Redefiner[] getRedefiners();
}
