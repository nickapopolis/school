
import java.lang.Double;

/**
 * Objects of this class represent numbers in our program
 */
public class NumberData extends Data {
    /** the value of this number */
    public double value;

    /**
     * Initialize a new number with value val
     */
    public NumberData(double val) {
	value = val;
    }

    /**
     * Give a string representation of this NumberData
     */
    public String toString() {
	return new Double(value).toString();
    }
}
