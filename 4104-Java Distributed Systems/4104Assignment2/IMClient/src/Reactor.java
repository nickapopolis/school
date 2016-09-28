
import java.util.Properties;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.*;

/**
 * This class implements the Reactor design pattern for this project. The class
 * reads definitions from a configuration file and then instantiates the appropriate
 * EventHandler classes and registers them. When an event is received, the Reactor
 * dispatches it to the appropriate EventHandler automatically 
 *
 */
public class Reactor implements Runnable
{
	
	// MessageChannel holding incoming events
	private MessageChannel incoming;
	
	// one of these has to be null
	private Client client;
	private Server server;

	//Stores registered event handlers
	Vector<EventHandler> handlers;
	
    protected int _port;
    protected final int _poolSize = 3;
    protected Thread workerThreads[];
		
    /**
     * This constructor allows the Reactor to be used for a Client.
     * @param fileName a String containing the path for the configuration file
     * @param c the Client
     * @param IncomingChannel a MessageChannel holding incoming events
     */
	Reactor(String fileName, Client c, MessageChannel IncomingChannel)
	{
		incoming = IncomingChannel;
		client = c;
		server = null;
		handlers = new Vector<EventHandler>();
		initEventHandlers(fileName);
	}
	
	/**
	 * This constructor allows the Reactor to be used for a Server.
	 * @param filename a String containing the path for the configuration file
	 * @param s the Server
	 * @param IncomingChannel a MessageChannel holding incoming events
	 */
	Reactor(String filename, Server s, MessageChannel IncomingChannel)
	{
		incoming = IncomingChannel;
		server = s;
		client = null;
		handlers = new Vector<EventHandler>();
		initEventHandlers(filename);
		
	}
	/**
	 * Initializes event handlers from config file
	 * @param fileName
	 */
	private void initEventHandlers(String fileName)
	{
		try{
			Properties p = new Properties();
			
			//=====added to get absolute path for config file==//
			//gets the file
			File f1 = new File(fileName);  
			
			//gets the absolute path to the file
			//String path = f1.getAbsolutePath(); 
			
			//instantiates a fileStream Object to read file
			InputStream IS = new FileInputStream(f1); 
			//=================================================//

			p.load(IS);
			
			//removes hard coded event handlers
			Enumeration<Object> keys = p.keys();
			while (keys.hasMoreElements())
			{
				String next = keys.nextElement().toString();
				handlers.add(createEventHandler(p, next));
			}

		}catch (Exception e){
			String error = e.toString();
			System.err.println(String.format("%s \n%s", "Error initializing event handlers:", error));
		}
	}
	
	
	private EventHandler createEventHandler(Properties p, String handlerName) throws InstantiationException, IllegalAccessException, ClassNotFoundException
	{
		String className = p.getProperty(handlerName);
		Class c = Class.forName(className);

		
		return (EventHandler)c.newInstance();
	}
	
	
	/**
	 * Starts worker threads to handle incoming events, and stores them in
	 * a Thread pool
	 */
	@Override
	public void run() 
	{
		
		workerThreads = new Thread[_poolSize];
		
		for(int i=0; i<_poolSize ;i++)
		{
			workerThreads[i] = new Thread(new IncomingWorker(incoming,this));
		}
		for(int i=0; i<_poolSize ;i++)
		{
			workerThreads[i].start();
		}
		
		
				
	}
	
	/**
	 * This method is called by the worker threads to handle incoming events.
	 * @param evt an Event that will get dispatched to registered EventHandlers
	 */
	public synchronized void handleEvent(Event evt)
	{
		for(EventHandler h: handlers){
			if( evt.getType().equals(h.getType())){
				if(client != null)
					h.handleEvent(evt, client);
				if(server != null)
					h.handleEvent(evt, server);
			}
		}
	}
}
