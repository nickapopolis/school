
/**
 * The operator interface is for any arithmetic operation or function
 * our language can perform. Examples include Plus, Minus, Times, Sin, Cos
 */
public abstract class Operator {
    /** Return the number of arguments required */
    public abstract int nArguments();

    /** Evaluate the operator with the given arguments */
    public abstract Data evaluate(Data args[]) throws TypeException;

    /** Return a string representation of this operator */
    public abstract String toString();
}
