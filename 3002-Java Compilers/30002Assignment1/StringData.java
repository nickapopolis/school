
import java.lang.Double;

/**
 * This class represents a text string
 */
public class StringData extends Data {
    /** the value of this string */
    public String value;

    /**
     * Initialize a new number with value val
     */
    public StringData(String val) {
	value = val;
    }

    /**
     * Give a string representation of this StringData
     */
    public String toString() {
	return value;
    }
}
