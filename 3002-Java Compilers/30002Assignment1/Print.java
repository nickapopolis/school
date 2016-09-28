// import Operator;

/**
 * This is the print operator, which prints its argument
 */
public class Print extends Operator {
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
	System.out.print(args[0].toString());
	return args[0];  // evaluate to the argument
    }

    /**
     * Return a string representation of this operator
     */
    public String toString() {
	return "print";
    }
}
