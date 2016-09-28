
import java.io.*;
import java.net.*;
/**
 * This class implements the Connector part of the Acceptor-Connector design pattern.
 * The purpose of this class is to decouple the network connection from the processing.
 * In this case, the class decouples outgoing network requests
 */
public class Connector implements Runnable{

	int PORT = 7111;
	String host = "localhost";
	MessageChannel outgoing;
	MessageChannel incoming;
	
	/**
	 * Creates a new connector
	 * @param in a MessageChannel storing incoming Events
	 * @param out a MessageChannel storing outgoing Events
	 */
	public Connector(MessageChannel in, MessageChannel out)
	{
		incoming = in;
		outgoing = out;
	}
	
	public void run() 
	{
		try {
			Socket socket = new Socket(host, PORT);
			InputStream is = socket.getInputStream();
			OutputStream os = socket.getOutputStream();
			Thread incomingThread = new Thread(new ClientIncomingRequester(incoming,is));
			incomingThread.start();
			sendEvent(os);
			//getEvent(is);
			
			
						
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} /*catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
	}
	/**
	 * Takes stream from server connection and performs operations on them.
	 * @param is
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public void getEvent(InputStream is) throws IOException, ClassNotFoundException
	{
		ObjectInputStream ois = new ObjectInputStream(is);
		
		Event e = (Event)ois.readObject();	
		incoming.put(e);
	}
	/**
	 * Takes events from channel, converts them to messages and sends them to server
	 * @param os
	 * @throws IOException
	 */
	public void sendEvent(OutputStream os) throws IOException
	{
		ObjectOutputStream oos = new ObjectOutputStream(os);
		
		while(true)
		{
			Object o = outgoing.take();
			//turn into message depending on protocol
			oos.writeObject(o);
		}
	}
	

}
