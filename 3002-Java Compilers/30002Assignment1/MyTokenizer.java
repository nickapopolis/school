
import java.io.Reader;
import java.io.IOException;
import java.io.StreamTokenizer;

/**
 * This subclass of StreamTokenizer does exactly what we want for our
 * expression calculator and includes many extra token types not found
 * in StreamTokenizer
 */
public class MyTokenizer extends StreamTokenizer {
    public static final int TT_PLUS = '+';
    public static final int TT_MINUS = '-';
    public static final int TT_TIMES = '*';
    public static final int TT_DIVIDE = '/';
    public static final int TT_POWER = '^';
    public static final int TT_MOD = '%';
    public static final int TT_STRING = '\"';
    public static final int TT_POUND = '#';
    public static final int TT_QUESTION = '?';
    public static final int TT_COLON = ':';
	
    

    
    public static final int TT_COS = -60;
    public static final int TT_SIN = -61;
    public static final int TT_TAN = -62;

    public static final int TT_SQRT = -63;
    public static final int TT_LOG = -64;
    public static final int TT_LN = -65;
    public static final int TT_MAX = -66;
    public static final int TT_MIN = -67;

    public static final int TT_PRINT = -69;

    public static final int TT_E = -70;
    public static final int TT_PI = -71;
    public static final int TT_PHI = -72;

    public static final int TT_WHILE = -80;
    public static final int TT_IF = -81;
    public static final int TT_ELSE = -82;

    public static final int TT_VARIABLE = -90;

    public static final int TT_LPAREN = '(';
    public static final int TT_RPAREN = ')';

    public static final int TT_SEMICOLON = ';';
    public static final int TT_COMMA = ',';
    

    public static final int TT_ASSIGN = '=';

    public static final int TT_LESSTHAN = '<';
    public static final int TT_GREATERTHAN = '>';

    public static final int TT_LBRACE = '{';
    public static final int TT_RBRACE = '}';

    public static final int TT_TRUE = -54;
    public static final int TT_FALSE = -55;

    public static final int TT_OR = '|';
    public static final int TT_AND = '&';
    public static final int TT_NOT = '~';



    /**
     * Initialize this tokenizer to reader from the given input stream
     */
    MyTokenizer(Reader reader) {
	super(reader);
	parseNumbers();
	eolIsSignificant(false);
	slashStarComments(false);
	slashSlashComments(false);
	ordinaryChar('+');
	ordinaryChar('-');
	ordinaryChar('*');
	ordinaryChar('/');
	ordinaryChar('^');
	ordinaryChar('{');
	ordinaryChar('}');
	ordinaryChar('(');
	ordinaryChar(')');
	ordinaryChar('<');
	ordinaryChar('>');
	ordinaryChar('=');
	ordinaryChar(':');
	ordinaryChar('?');
	quoteChar('\"');
    }

    /**
     * Get the next input token from the stream 
     */
    public int nextToken() throws IOException {
	super.nextToken();
	if (ttype == TT_WORD) {
	    ttype = TT_VARIABLE;
	    checkReservedWords();
	}
	return ttype;
    }

    /**
     * Check if the current sval is any of the reserved words and, if so,
     * adjust the ttype
     */
    protected int checkReservedWords() {
	if (sval.compareTo("sin") == 0) {
	    ttype = TT_SIN;
	}  else if (sval.compareTo("cos") == 0) {
	    ttype = TT_COS;
	} else if (sval.compareTo("tan") == 0) {
	    ttype = TT_TAN;
	} else if (sval.compareTo("log") == 0) {
	    ttype = TT_LOG;
	} else if (sval.compareTo("ln") == 0) {
	    ttype = TT_LN;
	} else if (sval.compareTo("sqrt") == 0) {
	    ttype = TT_SQRT;
	} else if (sval.compareTo("print") == 0) {
	    ttype = TT_PRINT;
	} else if (sval.compareTo("pi") == 0) {
	    ttype = TT_PI;
	} else if (sval.compareTo("e") == 0) {
	    ttype = TT_E;
	} else if (sval.compareTo("while") == 0) {
	    ttype = TT_WHILE;
	} else if (sval.compareTo("true") == 0) {
	    ttype = TT_TRUE;
	} else if (sval.compareTo("false") == 0) {
	    ttype = TT_FALSE;
	} else if (sval.compareTo("phi") == 0) {
	    ttype = TT_PHI;
	} else if (sval.compareTo("max") == 0) {
	    ttype = TT_MAX;
	} else if (sval.compareTo("min") == 0) {
	    ttype = TT_MIN;
	}else if (sval.compareTo("if") == 0) {
	    ttype = TT_IF;
	}else if (sval.compareTo("else") == 0) {
	    ttype = TT_ELSE;
	}
	
	
	return ttype;
    }
    protected Boolean checkPower()
    {
System.out.println("checking if statement is power");
	Boolean isPower = true;
	int i = 0;
	while ((isPower == true) && (i<sval.length()))
	{
	     if (sval.charAt(i) != '*')
		{
		     isPower = false;
		}	
	     i++;
	}
	System.out.println(isPower);
	return isPower;
    }
    
}
