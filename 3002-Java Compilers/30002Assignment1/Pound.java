// import Operator;

/**
 * This operator evaluates the boolean value of its argument
 */
public class Pound extends Operator {
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
	if ((args[0] instanceof NumberData)) {
		Boolean ret= true;
		if (((NumberData)args[0]).value == 0)
		{ 
			ret = false;
		}
		return new BooleanData(ret);
	}
	else if ((args[0] instanceof BooleanData)) {
		return new NumberData((((BooleanData)args[0]).value)==false? 0 : 1);
	}
	else 
	    throw new TypeException(getClass().getName()
				+ ": first argument is not a number or boolean");
	
    }

    /**
     * Return a string representation of this operator
     */
    public String toString() {
	return "cos";
    }
}
