package lgbt.audrey.pipe.bytecode;

/**
 * @author audrey
 * @since 12/18/15.
 */
public interface Generator {
    byte[] generate();

    String getClassName();
}
