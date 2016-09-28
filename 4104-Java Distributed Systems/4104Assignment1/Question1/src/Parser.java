

/* Class Parser
 * 
 * Input: String
 * 
 * Purpose: Acts as a custom tokenizer and parser for the input string
 */
public class Parser 
{
	private String str = null;
	private String lastParsed = null;
	
	public Parser(String str)
	{
		this.str = str;
	}
	
	//Returns the first character in the string
	public char parseNext()
	{
		char c = str.charAt(0);
		lastParsed = Character.toString(c);
		str = str.substring(1, str.length());
		return c;
	}
	//Returns the String of characters inside a set of square brackets
	public String parseSquare()
	{	
		if(!(parseNext() == '['))
		{
			System.err.println("Missing left bracket");
		}
		String ret = "";
		while(str.length() >0 )
		{
			char c = parseNext();
			if(c == ']')
			{
				return ret;
			}
			else
			{
				ret = ret + Character.toString(c);
			}
		}
		//missing second bracket
		System.err.println("Missing right bracket");
		return null;
	}
	//Returns the String of characters inside a set of parentheses
	public String parseParentheses()
	{	
		if(!(parseNext() == '{'))
		{
			System.err.println("Missing left parenthesis");
		}
		String ret = "";
		while(str.length() >0 )
		{
			char c = parseNext();
			if(c == '}')
			{
				return ret;
			}
			else
			{
				ret = ret + Character.toString(c);
			}
		}
		//missing second parenthesis
		System.err.println("Missing right parenthesis");
		return null;
	}
	//Adds the last parsed segment back to the string
	public void pushBack()
	{
		str = lastParsed + str;
	}
	//Check if the string is empty/finished being parsed
	public boolean hasCharactersLeft()
	{
		return str==null || str.length()!=0;
	}
}
