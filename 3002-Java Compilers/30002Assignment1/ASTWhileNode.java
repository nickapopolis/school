
import java.util.Vector;

/**
 * An ASTWhileNode represents a while expression
 */
public class ASTWhileNode extends ASTNode {
    protected ASTNode _condition;
    protected ASTNode _body;

    /**
     * Create a new ASTWhileNode with empty expression list
     */
    public ASTWhileNode(ASTNode condition, ASTNode body) {
	_condition = condition;
	_body = body;
    }
    
    /**
     * Return a string representation of this node
     */
    public String toString() {
	String s = "while ( " + _condition.toString() + " ) { " 
	    + _body.toString() + " } ";
	return s;
    }

    /**
     * Evaluate the subexpression rooted at this node.  The return value
     * is the number of iterations performed.
     */
    public Data evaluate(Environment env) throws TypeException {
	int count = 0;
	while (1 < 2) {
	    Data continu = _condition.evaluate(env);  // continue looping?
	    if (!(continu instanceof BooleanData)) {
		System.err.println("While statement has invalid condition");
		System.exit(-1);
	    }
	    if (((BooleanData)continu).value == false) break;
	    count++;
	    _body.evaluate(env);
	}
	return new NumberData(count);
    }

}
