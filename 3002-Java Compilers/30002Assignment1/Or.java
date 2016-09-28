// import Operator;

/**
 * This is the plus operator, which adds its operands together
 */
public class Or extends Operator {
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
	if (!(args[0] instanceof BooleanData)) {
	    throw new TypeException(getClass().getName()
				+ ": first argument is not a boolean");
	}
	if (!(args[1] instanceof BooleanData)) {
	    throw new TypeException(getClass().getName()
				+ ": second argument is not a boolean");
	}
	return new BooleanData(((BooleanData)args[0]).value 
			      | ((BooleanData)args[1]).value);
    }

    /**
     * Return a string representation of this operator
     */
    public String toString() {
	return "|";
    }
}
