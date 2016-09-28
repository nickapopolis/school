
public class Question1 
{
	public static void main(String[] args)
	{
		if(args.length !=2)
		{
			System.out.println("Invalid arguments for Question1 input");
		}
		else
		{
			int i = 0;
			String inputText = "";
			
			// get command line input
			while(i+1<args.length)
			{
				if(args[i].equals("-i"))
				{
					inputText = args[i+1];
				}
				else
				{
					System.out.println("Invalid input");
					System.exit(0);
				}
				i += 2;
			}
			//if valid input parse and print the string
			StringPrinter tc = new StringPrinter(inputText);
			try {
				tc.print();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
