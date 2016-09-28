
// import Operator;

/**
 * This operator evaluates the sine of its argument
 */
public class Sin extends Operator {
    /**
     * Return the number of arguments this operator takes
     */
    public int nArguments() {
	return 1;
    }

    /**
     * Evaluate this operator on the given expression
     */
    public Data evaluate(Data args[]) throws TypeException {
	if (!(args[0] instanceof NumberData)) {
	    throw new TypeException(getClass().getName()
				+ ": first argument is not a number");
	}
	return new NumberData(Math.sin(((NumberData)args[0]).value));
    }

    /**
     * Return a string representation of this operator
     */
    public String toString() {
	return "sin";
    }
}
