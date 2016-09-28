import java.util.Vector;

/**
 * An ASTWhileNode represents a while expression
 */
public class ASTPostfixNode extends ASTNode {
    protected ASTNode number;
    protected Operator operator;

    /**
     * Create a new ASTWhileNode with empty expression list
     */
    public ASTPostfixNode(ASTNode num, Operator op) {
	number = num;
	operator = op;
    }
    
    /**
     * Return a string representation of this node
     */
    public String toString() 
    {
	return "";
    }

    /**
     * Evaluate the subexpression rooted at this node.  The return value
     * is the number of iterations performed.
     */
    public Data evaluate(Environment env) throws TypeException 
    {
	Data arg = number.evaluate(env);
	Data[] args = new Data[1];
	args[0] = arg;
	if(number instanceof ASTVariableNode)
	{
	Data val = operator.evaluate(args);
		env.defineBinding(  ((ASTVariableNode)number).getName(),val);
	}
	return arg;
    }

}
