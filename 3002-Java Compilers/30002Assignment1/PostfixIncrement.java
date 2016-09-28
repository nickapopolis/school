// import Operator;

/**
 * This operator subtracts its second argument from its first
 */
public class PostfixIncrement extends Operator {
    /**
     * Return the number of arguments this operator takes
     */
    public int nArguments() {
	return 1;
    }
    public PostfixIncrement()
    {
    }
    /**
     * Evaluate this operator on the given expression
     */
    public Data evaluate(Data args[]) throws TypeException {
	if (!(args[0] instanceof NumberData)) {
	    throw new TypeException(getClass().getName()
				+ ": first argument is not a number");
	}
	return new NumberData(((NumberData)args[0]).value 
			      + 1);
    }

    /**
     * Return a string representation of this operator
     */
    public String toString() {
	return "--";
    }
}
