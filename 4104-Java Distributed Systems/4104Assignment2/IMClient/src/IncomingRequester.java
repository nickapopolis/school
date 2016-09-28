
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;


public class IncomingRequester implements Runnable
{
	MessageChannel requests;
	Socket socket;
	boolean isObjectBased = true;
	Acceptor acceptor;
	
	public IncomingRequester(MessageChannel req, Socket s, Acceptor a)
	{
		requests = req;
		socket = s;
		acceptor = a;
	}
	public void run() 
	{
		while(true)
		{
			
			try {
				InputStream is = socket.getInputStream();
				
				//pipe and filter to get event then pass it into channel
			
				ObjectInputStream ois = new ObjectInputStream(is);
				//ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
				

				//ObjectInputStream ois = new ObjectInputStream(is);

				
				while(true)
				{
					//pipe and filter
				
					Event message = null;

					 try {
							message= (Event)ois.readObject();
							if (acceptor==null)
							{
								
							}
							if (message instanceof LoginMessage)
							{
								acceptor.addUser(((LoginMessage)message).getUsername(),new ObjectOutputStream(socket.getOutputStream()));
							}
							else if(message instanceof LogoutMessage)
							{
								acceptor.removeUser(((LogoutMessage)message).getUsername());
							}
							else
							{
							}
						} catch (ClassNotFoundException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					requests.put(message);


				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}
}
