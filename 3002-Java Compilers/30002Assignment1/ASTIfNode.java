import java.util.Vector;

/**
 * An ASTWhileNode represents a while expression
 */
public class ASTIfNode extends ASTNode {
    protected ASTNode _condition;
    protected ASTNode _if_body;
    protected ASTNode _else_body;

    /**
     * Create a new ASTWhileNode with empty expression list
     */
    public ASTIfNode(ASTNode condition, ASTNode ifBody,ASTNode elseBody) {
	_condition = condition;
	_if_body = ifBody;
	_else_body = elseBody;
    }
    
    /**
     * Return a string representation of this node
     */
    public String toString() {
	String s = "if ( " + _condition.toString() + " ) { " 
	    + _if_body.toString() + " } "+ _else_body!=null? ("else {" +_else_body.toString() + "}"):"";
	return s;
    }

    /**
     * Evaluate the subexpression rooted at this node.  The return value
     * is the number of iterations performed.
     */
    public Data evaluate(Environment env) throws TypeException {
	Data res = _condition.evaluate(env);
	if(!(res instanceof BooleanData)){
		System.err.println("If statement has invalid condition");
		System.exit(-1);
	}
	if(((BooleanData)res).value == true)
	{
		_if_body.evaluate(env);
	}
	else
	{
		if(_else_body !=null)
		{
			_else_body.evaluate(env);
		}
	}
	  
	return new BooleanData(((BooleanData)res).value);
    }

}
