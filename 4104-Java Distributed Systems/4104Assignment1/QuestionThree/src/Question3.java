

public class Question3 {


    	public static void main(String[] args) {
		//default eat time and running time
		long MaxEatTime = 2000;
		long RunningTime = 10000;

		//check if there are any parameters passed
		if (args.length > 0){
			//incorrect amount of parameters
			if(args.length != 4 && args.length != 0){
				System.out.println("Invalid Arguments.");
				System.out.println("Valid arguments are,");
				System.out.println("-t <m>	m represents the simulation time in seconds");
				System.out.println("-r <n>	n represents the maximum time taken to eat food. Values are assigned randomly from a uniform distribution in the range [1, n] seconds.");
				return;
			}
			else
			{
				try{
					//try and parse out the arguments
					String arg1 = args[0];
					String arg2 = args[1];
					String arg3 = args[2];
					String arg4 = args[3];

					if (arg1.equals("-t")){
						try{
							RunningTime = (long) Double.parseDouble( arg2 ) * 1000; //running time
						}
					   catch( Exception e)
					   {
							System.out.println("Invalid Arguments.");
							System.out.println("Valid arguments are,");
							System.out.println("-t <m>			m represents the simulation time in seconds");
							System.out.println("-r <n>			n represents the maximum time taken to eat food. Values are assigned randomly from a uniform distribution in the range [1, n] seconds.");
							return;
					   }
					}

					if (arg3.equals("-r")){
						try{
							MaxEatTime = (long) Double.parseDouble( arg4 ) * 1000;	//eat time
						}
					   catch( Exception e)
					   {
							System.out.println("Invalid Arguments.");
							System.out.println("Valid arguments are,");
							System.out.println("-t <m>			m represents the simulation time in seconds");
							System.out.println("-r <n>			n represents the maximum time taken to eat food. Values are assigned randomly from a uniform distribution in the range [1, n] seconds.");
							return;
					   }
					}
				}
				catch(Exception e)
				{
					System.out.println("Invalid Arguments.");
					System.out.println("Valid arguments are,");
					System.out.println("-t <m>			m represents the simulation time in seconds");
					System.out.println("-r <n>			n represents the maximum time taken to eat food. Values are assigned randomly from a uniform distribution in the range [1, n] seconds.");
					return;
				}
			}

		}

		System.out.println("=====================================================");
		System.out.println("Question 3");
		System.out.println("		Running Time = " + RunningTime / 1000 + " seconds");
		System.out.println("		Max eat time = " + MaxEatTime / 1000 + " seconds");
		System.out.println("=====================================================");
		System.out.println("START");
		System.out.println("");

		//=======================================================================//
		//1)Instantiate the "table" control object
		//2)Instantiate the 3 eater threads and agent thread
		//3)check if max running time has occurred
		//4)stop threads safely and exit program
		//======================================================================//

		//1)
		Control theTable = new Control();

		//2)
		Eater potatoEater = new Eater("potato", theTable, MaxEatTime);
		Eater waterEater = new Eater("water", theTable, MaxEatTime);
		Eater butterEater =new Eater("butter", theTable, MaxEatTime);

		Agent AgentThread = new Agent(theTable);


		//3)
		long startTime = System.currentTimeMillis();

		while (System.currentTimeMillis() - startTime < RunningTime)
		{
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		System.out.println("");

		//4)
		potatoEater.stopEating();
		waterEater.stopEating();
		butterEater.stopEating();

		AgentThread.stopProducing();

		System.out.println("FINISHED");
		System.exit(0);

	}

}