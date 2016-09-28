import java.util.*;
public class CharPrinter implements Runnable
{
	char letter;
	public CharPrinter(char c)
	{
		letter = c;
	}
	public synchronized void run() 
	{
		Random r = new Random();
		long endTime = System.currentTimeMillis() +  r.nextInt(1000);
		while(System.currentTimeMillis() < endTime)
		{
			
		}
		System.out.print(letter);
	}
}
