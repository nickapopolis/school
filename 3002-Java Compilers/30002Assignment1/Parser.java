/**
 * ToDo: Separate out the tokenizer so we have one that does exactly what
 * we want
 */
import java.io.Reader;
import java.io.LineNumberReader;
import java.io.StreamTokenizer;
import java.io.IOException;
import java.util.ArrayList;

// import MyTokenizer;
// import ASTNode;

/**
 * The Parser class parses our language and builds the appropriate abstract
 * syntax tree.
 */
public class Parser {

    /** the input stream we read from */
    protected LineNumberReader _reader;

    /**
     * Create a new Calculator parser that will read from the given Reader
     */
    public Parser(Reader r) {
	_reader = new LineNumberReader(r);
    }

    /**
     * Parse the given stream to make the abstract syntax tree
     */
    public ASTNode parse() throws IOException, ParseException {
	// set up a tokenizer on our input stream
	MyTokenizer st = new MyTokenizer(_reader);

	// create the abstract syntax tree
	ASTNode ast = parseExpressionList(st);

	// check that we parsed everything
	if (st.ttype != MyTokenizer.TT_EOF) {
	    throw new ParseException("Trailing garbage",
				     _reader.getLineNumber());
	}
	return ast;
    }

    /**
     * Parse an expression list
     * <expression-list> -> <expression>
     * <expression-list> -> ';' <expression-list>
     * <expression-list> -> <block> <expression-list>
     * <expression-list> -> <expression> <expression-list>
     */
    protected ASTNode parseExpressionList(MyTokenizer st)
	throws IOException, ParseException {
	ASTBlockNode n = new ASTBlockNode();
	while (1 < 2) { // forever
	    // lookahead by one symbol
	    st.nextToken();
	    switch (st.ttype) {
	    case MyTokenizer.TT_LBRACE:
		st.pushBack();
		n.addExpression(parseBlock(st));
		break;
	    case MyTokenizer.TT_RBRACE:
		st.pushBack();
		return n;
	    case MyTokenizer.TT_EOF:
		return n;
	    case MyTokenizer.TT_SEMICOLON:
		continue;
	    default:
		st.pushBack();
		n.addExpression(parseExpression(st));
	    }
	}
    }

    /**
     * Parse a block of code
     * <block> -> '{' <expression-list>  '}'
     */
    protected ASTNode parseBlock(MyTokenizer st)
	throws IOException, ParseException {
	st.nextToken();
	if (st.ttype != MyTokenizer.TT_LBRACE) {
	    throw new ParseException("Expected '{', didn't get it",
				     _reader.getLineNumber());
	}
	ASTNode n = parseExpressionList(st);
	st.nextToken();
	if (st.ttype != MyTokenizer.TT_RBRACE) {
	    throw new ParseException("Expected '}', didn't get it",
				     _reader.getLineNumber());
	}
	return n;
    }


    /**
     * Parse an expression according to the rules:
     * <expression> -> func ( <expression> )
     * <expression> -> func ( <expression> ) <op> <expression>
     * <expression> -> ( <expression> )
     * <expression> -> ( <expression> ) <op> <expression>
     * <expression> -> <number> <op> <expression>
     * <expression> -> <number>
     */
    protected ASTNode parseExpression(MyTokenizer st)
	throws IOException, ParseException {
	ASTNode n1 = null;
	ASTOperatorNode n = null;
	// first token must be an open parenthesis, a function or a number
	st.nextToken();
	switch (st.ttype) {
	case MyTokenizer.TT_VARIABLE:
	    n1 = new ASTVariableNode(st.sval);
	    break;
	case MyTokenizer.TT_STRING:
	    n1 = new ASTConstNode(new StringData(st.sval));
	    break;
	case MyTokenizer.TT_LPAREN:
	    // this is an expression in parentheses
	    st.pushBack();
	    n1 = parseParenExpression(st);
	    break;
	case MyTokenizer.TT_SIN:
	    n = new ASTOperatorNode(new Sin());
	    n.setChild(0, parseParenExpression(st));
	    n1 = n;
	    break;
	case MyTokenizer.TT_COS:
	    n = new ASTOperatorNode(new Cos());
	    n.setChild(0, parseParenExpression(st));
	    n1 = n;
	    break;
	case MyTokenizer.TT_TAN:
	    n = new ASTOperatorNode(new Tan());
	    n.setChild(0, parseParenExpression(st));
	    n1 = n;
	    break;
	case MyTokenizer.TT_LOG:
	    n = new ASTOperatorNode(new Log());
	    n.setChild(0, parseParenExpression(st));
	    n1 = n;
	    break;
	case MyTokenizer.TT_LN:
	    n = new ASTOperatorNode(new Ln());
	    n.setChild(0, parseParenExpression(st));
	    n1 = n;
	    break;
	case MyTokenizer.TT_SQRT:
	    n = new ASTOperatorNode(new Sqrt());
	    n.setChild(0, parseParenExpression(st));
	    n1 = n;
	    break;
	case MyTokenizer.TT_PRINT:
	    n = new ASTOperatorNode(new Print());
	    n.setChild(0, parseParenExpression(st));
	    n1 = n;
	    break;
	case MyTokenizer.TT_POUND:
	    n = new ASTOperatorNode(new Pound());
	    n.setChild(0, parseExpression(st));
	    n1 = n;
	    break;
	case MyTokenizer.TT_NOT:
	    n = new ASTOperatorNode(new Not());
	    n.setChild(0, parseExpression(st));
	    n1 = n;
	    break;
	case MyTokenizer.TT_PLUS:
		if(st.nextToken()==MyTokenizer.TT_PLUS)
		{
			n = new ASTOperatorNode(new Plus());
			n.setChild(0, parseExpression(st));
			n.setChild(1, new ASTConstNode(new NumberData(1)));
			return n;
		}
		else
		{
			st.pushBack();
		}
	case MyTokenizer.TT_MINUS:
		switch(st.nextToken())
		{
			case MyTokenizer.TT_MINUS:
				n = new ASTOperatorNode(new Minus());
				n.setChild(0, parseExpression(st));
				n.setChild(1, new ASTConstNode(new NumberData(1)));
				return n;
			case MyTokenizer.TT_NUMBER:
				n = new ASTOperatorNode(new Negation());
				n.setChild(0,new ASTConstNode(new NumberData(st.nval)));
				return n;
			case MyTokenizer.TT_VARIABLE:
				n = new ASTOperatorNode(new Negation());
				n.setChild(0,new ASTConstNode(new NumberData(st.nval)));
				return n;
			default:
				st.pushBack();
		}
	case MyTokenizer.TT_E:
	    n1 = new ASTConstNode(new NumberData(Math.E));
	    break;
	case MyTokenizer.TT_PI:
	    n1 = new ASTConstNode(new NumberData(Math.PI));
	    break;
	case MyTokenizer.TT_PHI:
	    n1 = new ASTConstNode(new NumberData(1.6180339887498948482));
	    break;
	case MyTokenizer.TT_TRUE:
	    n1 = new ASTConstNode(new BooleanData(true));
	    break;
	case MyTokenizer.TT_FALSE:
	    n1 = new ASTConstNode(new BooleanData(false));
	    break;
	case MyTokenizer.TT_NUMBER:
	    n1 = new ASTConstNode(new NumberData(st.nval));
	    break;
	case MyTokenizer.TT_WHILE:
	    st.pushBack();
	    return parseWhileExpression(st);
	case MyTokenizer.TT_IF:
	    st.pushBack();
	    return parseIfExpression(st);
	default:
	    throw new ParseException("Expecting expression, "
				     + "got something else",
				     _reader.getLineNumber());
	}

	// read the operator (if any)
	st.nextToken();
	ASTOperatorNode n2 = null;
	switch (st.ttype) {
	case MyTokenizer.TT_ASSIGN:
	    if (!(n1 instanceof ASTVariableNode)) {
		throw new ParseException("Need variable on left hand side of =",
					 _reader.getLineNumber());
	    }
	    return new ASTAssignNode(((ASTVariableNode)n1).getName(),
				   parseExpression(st));
	case MyTokenizer.TT_PLUS:
	    if(st.nextToken()==MyTokenizer.TT_PLUS)
	    {
		ASTPostfixNode n3 = new ASTPostfixNode(n1, new PostfixIncrement());
		return n3;
	    }
	    else
            {
		st.pushBack();
		n2 = new ASTOperatorNode(new Plus());
	    }
	    break;
	case MyTokenizer.TT_MINUS:
	    if(st.nextToken()==MyTokenizer.TT_MINUS)
	    {
		ASTPostfixNode n3 = new ASTPostfixNode(n1, new PostfixDecrement());
		return n3;
	    }
	    else
            {
		st.pushBack();
		n2 = new ASTOperatorNode(new Minus());
	    }
	    break;
	case MyTokenizer.TT_TIMES:
	    st.pushBack();
	    return parseTimesExpression(st,n1);
	case MyTokenizer.TT_DIVIDE:
	    n2 = new ASTOperatorNode(new Divide());
	    break;
	case MyTokenizer.TT_MOD:
	    n2 = new ASTOperatorNode(new Mod());
	    break;
	case MyTokenizer.TT_POWER:
	    n2 = new ASTOperatorNode(new Power());
	    break;
	case MyTokenizer.TT_LESSTHAN:
	    n2 = new ASTOperatorNode(new LessThan());
	    break;
	case MyTokenizer.TT_GREATERTHAN:
	    n2 = new ASTOperatorNode(new GreaterThan());
	    break;
	case MyTokenizer.TT_AND:
	    n2 = new ASTOperatorNode(new And());
	    break;
	case MyTokenizer.TT_OR:
	    n2 = new ASTOperatorNode(new Or());
	    break;
	case MyTokenizer.TT_QUESTION:
	    st.pushBack();
	    return parseQuestionExpression(st, n1);
	default:
	    // this isn't a binary operator, push it back
	    st.pushBack();
	    return n1;
	}

	// parse the rest of the expression
	n2.setChild(0, n1);
	n2.setChild(1, parseExpression(st));
	return n2;
    }

    /**
     * Parse an expression enclosed in parentheses
     */
    protected ASTNode parseParenExpression(MyTokenizer st)
	throws IOException, ParseException {
	st.nextToken();
	if (st.ttype != MyTokenizer.TT_LPAREN) {  // open parenthesis '('
	    throw new ParseException("Expected '(', didn't get it",
				     _reader.getLineNumber());
	}
	ASTNode n = parseExpression(st);
	st.nextToken();
	if (st.ttype != MyTokenizer.TT_RPAREN) { // close parenthesis ')'
	    throw new ParseException("Unmatched parenthesis",
				     _reader.getLineNumber());
	}
	return n;
    }
     /**
     * Parse a function
     */
    protected ArrayList<ASTNode> parseFunctionExpression(MyTokenizer st)
	throws IOException, ParseException {
		st.nextToken();
		if (st.ttype != MyTokenizer.TT_LPAREN) {  // open parenthesis '('
		    throw new ParseException("Expected '(', didn't get it",
					     _reader.getLineNumber());
		}
		ArrayList<ASTNode> n = new ArrayList<ASTNode>();
		while(1<2)
		{
			st.nextToken();
		    if(st.ttype == MyTokenizer.TT_RPAREN)
		    	break;
		    else if(st.ttype == MyTokenizer.TT_COMMA)
		    {
		    	throw new ParseException("invalid arguments",
					     _reader.getLineNumber());
		    }
		    else
		    {
		    	st.pushBack();
		    }
			n.add(parseExpression(st));
		    st.nextToken();
		    if(st.ttype == MyTokenizer.TT_RPAREN)
		    	break;
		    else if(st.ttype == MyTokenizer.TT_COMMA)
		    {
		    	continue;
		    }
		    else
		    {
		    	throw new ParseException("invalid arguments",
					    _reader.getLineNumber());
		    }
		}
		return n;
    }
	/**
     * Parse an if expression
     * if-expression -> if ( <expression> ) <block> else <block>
       if-expression -> if ( <expression> ) <block>
     */
    protected ASTNode parseIfExpression(MyTokenizer st)
	throws IOException, ParseException {
	if (st.nextToken() != MyTokenizer.TT_IF) {
	    throw new ParseException("Expected if, didn't get it",
				 _reader.getLineNumber());
	}
	ASTNode cond = parseParenExpression(st);
	ASTNode resIf = parseBlock(st);
        ASTNode resElse = null;
	if(st.nextToken() == MyTokenizer.TT_ELSE)
	    {
		resElse = parseBlock(st);
	    }
	return new ASTIfNode(cond, resIf, resElse );
    }
    protected ASTNode parseQuestionExpression(MyTokenizer st, ASTNode cond)
	throws IOException, ParseException {
	if (st.nextToken() != MyTokenizer.TT_QUESTION) {
	    throw new ParseException("Expected Question, didn't get it",
				 _reader.getLineNumber());
	}
	ASTNode res_if = parseExpression(st);
	if(st.nextToken() != MyTokenizer.TT_COLON)
	    {
		throw new ParseException("Expected else colon, didn't get it",
				 _reader.getLineNumber());
	    }
	ASTNode res_else = parseExpression(st);
	return new ASTIfNode(cond, res_if, res_else );
    }
    /**
     * Parse a while expression
     * while-expression -> while ( <expression> ) <block>
     */
    protected ASTNode parseWhileExpression(MyTokenizer st)
	throws IOException, ParseException {
	if (st.nextToken() != MyTokenizer.TT_WHILE) {
	    throw new ParseException("Expected while, didn't get it",
				 _reader.getLineNumber());
	}
	ASTNode cond = parseParenExpression(st);
	ASTNode res = parseBlock(st);
	return new ASTWhileNode(cond, res);
    }
    protected ASTNode parseTimesExpression(MyTokenizer st, ASTNode n1)
	throws IOException, ParseException {
	if (st.nextToken() != MyTokenizer.TT_TIMES) {
	    throw new ParseException("Expected times, didn't get it",
				 _reader.getLineNumber());
	}
	int i = 1;
	ASTOperatorNode n;
	while(st.nextToken()== MyTokenizer.TT_TIMES)
	{
		i++;
	}
	st.pushBack();
	if (i==1)
	{
		n = new ASTOperatorNode(new Times());
		n.setChild(0, n1);
		n.setChild(1, parseExpression(st));
		return n;
	}
	else
	{
		n = new ASTOperatorNode(new Power());
		n.setChild(0, n1);
		n.setChild(1, new ASTConstNode(new NumberData(i)));
		return n;
	}
    }

}
