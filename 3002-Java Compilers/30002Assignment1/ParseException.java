import java.lang.Exception;

/**
 * A ParseException is thrown whenever the Parser class can't parse its
 * input.
 */
public class ParseException extends Exception {
    private static final long serialVersionUID = 1L;

    /**
     * Create a new parse exception
     */
    public ParseException(String msg, int line) {
	super("Line " + line + ": " + msg);
    }

}
