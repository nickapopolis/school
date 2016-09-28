import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.TimeUnit;

public class NodeChannel implements Channel
{
	protected ArrayBlockingQueue<Object> buf;
	protected int size;
	
	public NodeChannel(int size)
	{
		this.size = size;
		buf = new ArrayBlockingQueue<Object>(size);
	}
	
	//tries to add an object into the channel for 50ms
	public synchronized void put(Object o) throws InterruptedException 
	{
		if(buf.offer(o,50,TimeUnit.MILLISECONDS ))
		{
			notifyAll();
		}
	}
	
	//tries to take an object from the channel for 50ms
	public synchronized Object take() throws InterruptedException 
	{
		Object o = null;
		
		if(!((o=buf.poll(50, TimeUnit.MILLISECONDS)) == null))
		{
			notifyAll();
		}
		
		return o;
	}

}
