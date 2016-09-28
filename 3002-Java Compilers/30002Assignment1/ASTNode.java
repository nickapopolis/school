
/**
 * An ASTNode represents a node in an abstract syntax tree. At the
 * same time, it is the root of an abstract syntax tree, so it
 * represent an expression 
 */
public abstract class ASTNode {
    /**
     * Return a string representation of this node
     */
    public abstract String toString();

    /**
     * Evaluate the subexpression rooted at this node
     */
    public abstract Data evaluate(Environment env) throws TypeException;
}

