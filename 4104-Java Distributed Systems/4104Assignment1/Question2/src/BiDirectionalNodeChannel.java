
public class BiDirectionalNodeChannel implements Channel
{
	protected NodeChannel leftIn, rightIn;
	protected Thread leftThreadPut, leftThreadTake;
	protected Thread rightThreadPut, rightThreadTake;
	
	//associates a thread with the rightful side of the channel it is to be interacting with
	public void connect(Thread leftPut, Thread leftTake, Thread rightPut, Thread rightTake)
	{
		leftThreadPut = leftPut;
		leftThreadTake = leftTake;
		rightThreadPut = rightPut;
		rightThreadTake = rightTake;
	}
	public BiDirectionalNodeChannel(int size)
	{
		leftIn = new NodeChannel(size);
		rightIn = new NodeChannel(size);
	}
	// puts object in channel if space exists
	public synchronized void put(Object o) throws InterruptedException 
	{
		//determines which node is trying to take object by getting Thread
		if(Thread.currentThread() == leftThreadPut)
		{
			leftIn.put(o);
		}
		else if(Thread.currentThread() == rightThreadPut)
		{
			rightIn.put(o);
		}
	}

	// Takes object from channel if object exists
	public synchronized Object take() throws InterruptedException 
	{
		Object o = null;
		//determines which node is trying to take object by getting Thread
		if(Thread.currentThread() == leftThreadTake)
		{
			o = rightIn.take();
		}
		else if(Thread.currentThread() == rightThreadTake)
		{
			o = leftIn.take();
		}
		return o;
	}
	
	
}
