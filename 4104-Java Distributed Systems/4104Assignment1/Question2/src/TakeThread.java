
public class TakeThread implements Runnable{

	
	Node node;
	long runTime;
	
	public TakeThread(Node n, long msecs)
	{
		node = n;
		runTime = msecs;
	}
	
	public void run() 
	{
		//when to stop running the thread
		long endTime = System.currentTimeMillis() + runTime;
		
		while(System.currentTimeMillis()< endTime)
		{
			//node take message from channels
			node.take();
		}
	}


}
