import java.util.ArrayList;
// import Operator;

/**
 * This operator subtracts its second argument from its first
 */
public class Min extends Operator {
    /**
     * Return the number of arguments this operator takes
     */
    int size = 0;
    public int nArguments() {
	return 2;
    }
    
    /**
     * Evaluate this operator on the given expression
     */
    public Data evaluate(Data args[]) throws TypeException {
	int i = 0;
	
	if (!(args[0] instanceof NumberData)) {
	    throw new TypeException(getClass().getName()
				+ ": first argument is not a number");
	}
	if (!(args[1] instanceof NumberData)) {
	    throw new TypeException(getClass().getName()
				+ ": second argument is not a number");
	}
	return new NumberData(((NumberData)args[0]).value 
			      - ((NumberData)args[1]).value);
    }

    /**
     * Return a string representation of this operator
     */
    public String toString() {
	return "-";
    }
}
