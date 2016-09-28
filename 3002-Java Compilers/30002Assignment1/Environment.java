
import java.util.Hashtable;

/**
 * A binding is just a name/value pair
 */
class Binding {
    public String name;
    public Data value;
}

/**
 * An environment is used for maintaining a list of name/value pairs
 * during the interpretation of a program
 */
public class Environment {
    protected Hashtable<String,Data> bindings;
    
    /**
     * Create a new empty environment
     */
    public Environment() {
	bindings = new Hashtable<String,Data>();
    }

    /**
     * Create a new binding
     */
    public void defineBinding(String name, Data value) {
	bindings.put(name, value);
    }

    /**
     * Lookup new binding, by default unbound variables are equal to 
     * the number 0.0
     */
    public Data getValue(String name) {
	Data result = bindings.get(name);
	if (result == null) {
	    result = new NumberData(0.0);
	}
	return result;
    }
}
