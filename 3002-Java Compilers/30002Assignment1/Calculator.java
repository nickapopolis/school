
import java.io.Reader;
import java.io.InputStreamReader;
import java.io.StringReader;

/**
 * This class encapsulates a simple interpretor.  Running the main
 * function will read input from System.in, or the command line and 
 * interpret it.
 */
public class Calculator {
    /** 
     * Run the calculator program, which reads one expression, either
     * from the command line or from System.in, evaluates it, and
     * outputs the result 
     */
    public static void main(String args[]) {
	Reader r;
	if (args.length == 0) {
	    // evaluate input from stdin
	    r = new InputStreamReader(System.in);
	} else {
	    // evaluate input from the command line
	    String input = "";
	    for (int i = 0; i < args.length; i++) {
		input += args[i] + " ";
	    }
	    r = new StringReader(input);
	}

	// invoke the parser (front-end) to get the abstract syntax tree
	Parser parser = new Parser(r);
	ASTNode ast = null;
	try {
	    ast = parser.parse();
	} catch (java.io.IOException e) {
	    System.err.println(e.getMessage());
	    System.exit(-1);
	} catch (ParseException e) {
	    System.err.println(e.getMessage());
	    System.exit(-1);
	}

	// invoke the printer (back-end) to view the expression 
	// and evaluate the result
	try {
	    System.out.println("Program output:");
	    // System.out.println("\n\nProgram text:\n" + ast.toString());
	    Data result = ast.evaluate(new Environment());
	    System.out.println("\nReturn code: " + result.toString());
	} catch (TypeException e) {
	    System.err.println(e.getMessage());
	    System.exit(-1);
	}
    }
}
