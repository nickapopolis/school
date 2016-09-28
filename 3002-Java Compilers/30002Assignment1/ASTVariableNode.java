

/**
 * This class represents a variable
 */
public class ASTVariableNode extends ASTNode {
    String _name;

    public ASTVariableNode(String name) {
	_name = name;
    }

    public String toString() {
	return _name;
    }

    public String getName() {
	return _name;
    }

    public Data evaluate(Environment env) {
	return env.getValue(_name);
    }

}
