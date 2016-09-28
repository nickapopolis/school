// import Operator;

/**
 * This is the plus operator, which adds its operands together
 */
public class Not extends Operator {
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
	if (!(args[0] instanceof BooleanData)) {
	    throw new TypeException(getClass().getName()
				+ ": first argument is not a boolean");
	}
	return new BooleanData(!((BooleanData)args[0]).value);
    }

    /**
     * Return a string representation of this operator
     */
    public String toString() {
	return "~";
    }
}
