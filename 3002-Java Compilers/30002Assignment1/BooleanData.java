
/**
 * Objects in this class represent boolean values in our interpreter
 */
public class BooleanData extends Data {
    /** the value of this boolean */
    public boolean value;

    /**
     * Initialize a new boolean with value val
     */
    public BooleanData(boolean val) {
	value = val;
    }

    /**
     * Give a string representation of this StringData
     */
    public String toString() {
	return value ? "true" : "false";
    }
}
