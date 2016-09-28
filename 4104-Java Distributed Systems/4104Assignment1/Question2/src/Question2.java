
public class Question2 
{
	public static void main(String[] args)
	{
		if(args.length != 6)
		{
			System.out.println("Invalid arguments for Question2 input");
		}
		else
		{
			int i = 0;
			String inputFile = null;
			long runTime = 0;
			int chSize= 0;
			
			//parse command line input
			while(i+1<args.length)
			{
				if(args[i].equals("-c"))
				{
					inputFile = args[i+1];
				}
				else if(args[i].equals( "-t"))
				{
					runTime = Long.parseLong(args[i+1]);
				}
				else if(args[i].equals("-s"))
				{
					chSize = Integer.parseInt(args[i+1]);
				}
				else
				{
					System.out.println("Invalid input");
					System.exit(0);
				}
				i += 2;
			}
			//if valid input start the communication system
			CommunicationSystem s = new CommunicationSystem(inputFile, runTime, chSize);
			s.beginCommunication();
		}
	}
}
