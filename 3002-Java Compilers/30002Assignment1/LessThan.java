
// import Operator;

/**
 * This operator tests if its first argument is less than its second
 * argument.
 */
public class LessThan extends Operator {
    /**
     * Return the number of arguments this operator takes
     */
    public int nArguments() {
	return 2;
    }

    /**
     * Evaluate this operator on the given expression
     */
    public Data evaluate(Data args[]) throws TypeException {
	if (!(args[0] instanceof NumberData)) {
	    throw new TypeException(getClass().getName()
				+ ": first argument is not a number");
	}
	if (!(args[1] instanceof NumberData)) {
	    throw new TypeException(getClass().getName()
				+ ": second argument is not a number");
	}
	return new BooleanData(((NumberData)args[0]).value 
			       < ((NumberData)args[1]).value);
    }

    /**
     * Return a string representation of this operator
     */
    public String toString() {
	return "<";
    }
}
