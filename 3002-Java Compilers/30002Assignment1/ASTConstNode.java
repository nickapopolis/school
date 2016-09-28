
import java.lang.Double;

/**
 * An ASTConstNode represents a constant (string, boolean, number)
 */
public class ASTConstNode extends ASTNode {
    protected Data _val;

    public ASTConstNode(Data val) {
	_val = val;
    }

    public String toString() {
	return _val.toString();
    }

    public Data evaluate(Environment env) {
	return _val;
    }
    public Data getData()
    {
	return _val;
    }
}

