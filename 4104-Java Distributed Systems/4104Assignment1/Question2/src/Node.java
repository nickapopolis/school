import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;


public class Node
{
	protected static int nodeCount = 0;
	protected int nodeID;
	protected Queue<Message> messages;
	protected long runTime;
	protected ArrayList<BiDirectionalNodeChannel> channels;
	protected ArrayList<Thread> putThreads;
	protected ArrayList<Thread> takeThreads;
	protected long nextMessage;
	
	public Node(int id, long time)
	{
		nodeCount++;
		nodeID = id;
		messages = new LinkedList<Message>();
		channels = new ArrayList<BiDirectionalNodeChannel>();
		putThreads = new ArrayList<Thread>();
		takeThreads = new ArrayList<Thread>();
		runTime = time;
		nextMessage = 0;
	}
	
	//takes the first message out of all channels and determines whether if it is correct target
	public synchronized void take()
	{
		for(int i=0; i<channels.size();i++)
		{
			try {
				Message m = (Message)channels.get(i).take();
				if(m!= null &&!haveMessage(m))
				{
					addMessage(m);
					//message has reached destination
					if(m.getDestination()==nodeID)
					{
						System.out.println(m.getMsg());
					}
					//message has not reached destination, send it to all connected nodes
					else
					{
						put(m);
					}
				}
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	//returns whether the selected message is already in the buffer of saved messages
	private boolean haveMessage(Message m)
	{
		Iterator<Message> i = messages.iterator();
		while(i.hasNext())
		{
			if(i.next() == m)
				return true;
		}
		return false;
	}
	
	//sends the selected message
	public synchronized void put(Message m)
	{
		//send message down all channels connected to this node
		for(int i=0;i<channels.size();i++)
		{
			try {
				channels.get(i).put(m);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	//generates a new message 
	public synchronized void put()
	{
		if(System.currentTimeMillis()>nextMessage)
		{
			Random r = new Random();
			nextMessage = System.currentTimeMillis() + r.nextInt(100);
			int destination = nodeID;
			
			//select random node target that does not include itself
			while(destination==nodeID)
			{
				destination = r.nextInt(nodeCount) +1;
			}
			//create message
			Message m = new Message(nodeID,destination);
			addMessage(m);
			//send message
			put(m);
		}
		
	}
	
	// adds a received message to the buffer
	private void addMessage(Message m)
	{
		//if buffer full make space by removing first message of queue
		if(messages.size()>=100)
		{
			messages.poll();
		}
		messages.offer(m);
	}
	
	//adds a channel to the node
	public void addChannel(BiDirectionalNodeChannel ch)
	{
		channels.add(ch);
		putThreads.add(new Thread(new PutThread(this, runTime)));
		takeThreads.add(new Thread(new TakeThread(this, runTime)));
	}
	
	//returns the put thread associated with the selected channel
	public Thread getPutThread(BiDirectionalNodeChannel ch)
	{
		for(int i=0; i<channels.size();i++)
		{
			if(channels.get(i)== ch)
			{
				return putThreads.get(i);
			}
		}
		return null;
	}
	
	//returns the take thread associated with the selected channel
	public Thread getTakeThread(BiDirectionalNodeChannel ch)
	{
		for(int i=0; i<channels.size();i++)
		{
			if(channels.get(i)== ch)
			{
				return takeThreads.get(i);
			}
		}
		return null;
	}
	
	//starts communication of Node by beginning all the put and take threads
	public void startThreads()
	{
		for(int i=0;i<putThreads.size();i++)
		{
			putThreads.get(i).start();
		}
		for(int i=0;i<takeThreads.size();i++)
		{
			takeThreads.get(i).start();
		}
	}
	public long getNextMessageTime()
	{
		return nextMessage;
	}

}
