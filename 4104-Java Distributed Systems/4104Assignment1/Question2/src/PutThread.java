import java.util.Random;


public class PutThread implements Runnable
{
	Node node;
	long runTime;
	long waitTime;
	
	public PutThread(Node n, long msecs)
	{
		node = n;
		runTime = msecs;
	}
	public void run() 
	{
		Random r = new Random();
		//when to stop running the thread
		long endTime = System.currentTimeMillis() + runTime;
		//when to send the next message
		waitTime = System.currentTimeMillis() + r.nextInt(100);
		
		while(System.currentTimeMillis()< endTime)
		{
			//node create new message and send it
			node.put();
		}
	}

}
