
import java.util.Vector;

/**
 * An ASTBlockNode represents a (possibly empty) list of expressions.
 */
public class ASTBlockNode extends ASTNode {
    Vector<ASTNode> expressionList;

    /**
     * Create a new ASTBlockNode with empty expression list
     */
    public ASTBlockNode() {
	expressionList = new Vector<ASTNode>();
    }
    
    /**
     * Add a new expression to the expression list
     */
    public void addExpression(ASTNode n) {
	expressionList.add(n);
    }

    /**
     * Return a string representation of this node
     */
    public String toString() {
	String s = "";
	for (int i = 0; i < expressionList.size(); i++) {
	    s += (expressionList.elementAt(i)).toString() + " ; ";
	}
	return s;
    }

    /**
     * Evaluate the subexpression rooted at this node
     */
    public Data evaluate(Environment env) throws TypeException {
	Data result = null;
	for (int i = 0; i < expressionList.size(); i++) {
	    result = (expressionList.elementAt(i)).evaluate(env);
	}	
	return result;
    }

}
