import java.lang.Exception;

/**
 * This exception is thrown when a type-checking error occurs
 */
public class TypeException extends Exception {
    private static final long serialVersionUID = 1L;

    /**
     * Create a new parse exception
     */
    public TypeException(String msg) {
	super(msg);
    }

}
