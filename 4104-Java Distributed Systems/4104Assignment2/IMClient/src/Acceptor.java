import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Hashtable;

/**
 * This class implements the Acceptor part of the Acceptor-Connector design pattern 
 * The purpose of this class is to decouple the network connection from the processing.
 * In this case, the class decouples incoming network requests
 */
public class Acceptor implements Runnable
{
	MessageChannel incoming;
	MessageChannel outgoing;
	
	protected final int PORT;
	Thread acceptThreads[];
	int numThreads;
	Hashtable<String, ObjectOutputStream> registeredUsers;

	/**
	 * Creates a new Acceptor
	 * @param num number of worker threads to handle the socket
	 * @param in a MessageChannel that the acceptor will use to store incoming requests
	 * @param out a MessageChannel that the acceptor will use to handle outgoing requests
	 * @param port the port number that the socket will run at
	 */
	public Acceptor(int num, MessageChannel in, MessageChannel out, int port)
	{
		PORT = port; 
		numThreads = num;
		acceptThreads = new Thread[numThreads];
		registeredUsers = new Hashtable<String,ObjectOutputStream>();
		incoming = in;
		outgoing = out;
	}
	
	/**
	 * Creates a new IncomingRequester thread for a socket connection
	 * @param socket an Socket that has been connected to a client.
	 */
	public void addConnection(Socket socket)
	{
		//will constantly check for new messages from this port
		Thread receiveMessages = new Thread(new IncomingRequester(incoming, socket, this));
		receiveMessages.start();
	}
	
	/**
	 * This method spawns multiple threads and then puts processed requests into
	 * the incoming MessageChannel
	 */

	public void addUser(String userName, ObjectOutputStream ois)
	{
		registeredUsers.put(userName, ois);
	}
	public void removeUser(String userName)
	{
		registeredUsers.remove(userName);
	}
	public void run() 
	{
		try {
			ServerSocket server = new ServerSocket(PORT);
			//will constantly check for new connections
			Thread newConnections = new Thread( new AcceptorThread(server, this));
			newConnections.start();
			

			while(true)
			{
				Event e = (Event)outgoing.take();
				ObjectOutputStream s = registeredUsers.get(e.getDestination());
				
				sendEvent(s,e);
				//package event in message according to protocol and send
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	public void sendEvent(ObjectOutputStream os, Object o)
	{
		
		try {
			//convert to message here
			os.writeObject(o);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
	

}
