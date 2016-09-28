/**
 * This helper class is used by the Reactor to handle incoming requests in a separate
 * thread.
 */
public class IncomingWorker implements Runnable
{
	MessageChannel requests;
	Reactor reactor;	
	String name; //for debugging purposes
	static int numberOfWorkers = 0; //keeps track of how many workers are made
	
	public IncomingWorker(MessageChannel req, Reactor r)
	{
		requests = req;
		reactor = r;
		numberOfWorkers++;
		name = "Incoming worker " + numberOfWorkers;
	}

	@Override
	public void run() 
	{
		while(true)
		{
			Event next = (Event)requests.take();
			reactor.handleEvent(next);
		}
		//process next
	}
	

}
