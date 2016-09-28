
// import Operator;

/**
 * Instances of this class represent assignment operations
 */
public class ASTAssignNode extends ASTNode {
    protected String lhs;
    protected ASTNode rhs;

    /**
     * Construct a new node which represents the result of evaluating rhs
     * being assigned to lhs
     */
    public ASTAssignNode(String lhsi, ASTNode rhsi) {
	lhs = lhsi;
	rhs = rhsi;
    }

    /**
     * Evaluate this assignment.  The return value is the value of the 
     * right hand side
     */
    public Data evaluate(Environment env) throws TypeException {
	Data value = rhs.evaluate(env);
	env.defineBinding(lhs, value);
	return value;
    }

    /**
     * Return a string representation of this operator
     */
    public String toString() {
	return lhs + "=" + rhs.toString();
    }
}
