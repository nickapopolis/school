
import java.lang.Double;
import java.lang.Math;

// import ASTNode;

/**
 * An ASTOperatorNode represents an operator, i.e., a function with
 * 0 or more arguments.  
 */
class ASTOperatorNode extends ASTNode {
    /** the operation performed at this node */
    protected Operator _op;

    /** the children of this node */
    protected ASTNode _children[];

    /**
     * Create a new ASTNode of the given type, which must be one of
     * the NT_XXX values 
     */
    public ASTOperatorNode(Operator op) {
	_op = op;
	_children = new ASTNode[_op.nArguments()];
    }

    /**
     * Set the i'th child of this node to be n
     */
    public void setChild(int i, ASTNode n) {
	if (i < 0 || i >= _children.length) {
	    System.err.println("ASTOperatorNode.setChild :" 
			       + " Invalid child index");
	    System.exit(-1);
	}
	_children[i] = n;
    }

    public String toString() {
	switch (_children.length) {
	case 0:
	    return _op.toString();
	case 1:
	    return _op.toString() + "(" + _children[0].toString() + ")";
	case 2:
	    return "(" + _children[0].toString() + _op.toString() 
		+ _children[1].toString() + ")";
	default:
	    return "too_many_arguments";
	}
    }

    /**
     * Evaluate the abstract syntax tree to obtain a single number
     */
    public Data evaluate(Environment env) throws TypeException {
	Data args[] = new Data[_children.length];
	for (int i = 0; i < args.length; i++) {
	    args[i] = _children[i].evaluate(env);
	}
	return (_op.evaluate(args));
    }
}
