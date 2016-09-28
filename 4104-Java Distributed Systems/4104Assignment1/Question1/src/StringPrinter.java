import java.util.ArrayList;
import java.util.Random;


public class StringPrinter
{
	String parseString;
	ArrayList<Thread> threads;
	
	public StringPrinter(String str)
	{
		threads = new ArrayList<Thread>();
		parseString = str;
	}
	public void print() throws InterruptedException
	{
		printCharacters(parseString);
	}
	public void printCharacters(String chars) 
	{
		Parser p = new Parser(chars);
		while(p.hasCharactersLeft())
		{
			char c = p.parseNext();
			switch(c)
			{
				case '[':
					p.pushBack();
					printSquare(p.parseSquare());
					break;
				case '{':
					p.pushBack();
					printParenthesis(p.parseParentheses());
					break;
				default:
					printChar(c);
			}
		}
		
	}
	public void printChar(char c)
	{
		Thread t = new Thread(new CharPrinter(c));
		t.start();
		try {
			t.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void printSquare(String str)
	{
		String printString = "";
		Parser p = new Parser(str);
		while(p.hasCharactersLeft())
		{
			char c = p.parseNext();
			switch(c)
			{
				case '[':
					p.pushBack();
					printString += p.parseSquare();
					break;
				case '{':
					p.pushBack();
					printString += getParenthesis(p.parseParentheses());
					break;
				default:
					printString += c;
			}
		}
		
		Thread[] t = new Thread[printString.length()];
		for(int i =0; i < printString.length(); i++)
		{
			t[i] = new Thread(new CharPrinter(printString.charAt(i)));
			t[i].start();
		}
		for(int i =0; i < printString.length(); i++)
		{
			try {
				t[i].join();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	public void printParenthesis(String str)
	{
		String printString = "";
		Parser p = new Parser(str);
		while(p.hasCharactersLeft())
		{
			char c = p.parseNext();
			switch(c)
			{
				case '[':
					p.pushBack();
					printString += p.parseSquare();
					break;
				case '{':
					p.pushBack();
					printString += getParenthesis(p.parseParentheses());
					break;
				default:
					printString += c;
			}
		}
		
		
		Random r = new Random();
		int i = r.nextInt(printString.length());
		Thread t = new Thread(new CharPrinter(printString.charAt(i)));
		
		try {
			t.start();
			t.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public char getParenthesis(String str)
	{
		Random r = new Random();
		int i = r.nextInt(str.length());
		return str.charAt(i);
	}
	
}
