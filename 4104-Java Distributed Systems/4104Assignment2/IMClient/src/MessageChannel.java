import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * This is an implementation of the Channel design pattern. Its purpose is to 
 * hold Events. This implementation uses an Array based queue.
 *
 */
public class MessageChannel implements Channel
{
	protected ArrayBlockingQueue<Object> buf;
	protected int size;
	
	/**
	 * Creates a new MessageChannel
	 * @param size the size of the array used for the queuing
	 */
	public MessageChannel(int size)
	{
		this.size = size;
		buf = new ArrayBlockingQueue<Object>(size);
	}
	
	
	/**
	 * tries to add an object into the channel 
	 * @param the object to be placed into the channel
	 */
	public synchronized void put(Object o) 
	{
		while(!buf.offer(o))
		{
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		notifyAll();
	}
	
	
	/**
	 * tries to take an object from the channel
	 * @return the next object
	 */
	public synchronized Object take() 
	{
		Object o = null;
		
		while ((o = buf.poll())== null)
		{
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		notifyAll();
		
		return o;
	}

}
