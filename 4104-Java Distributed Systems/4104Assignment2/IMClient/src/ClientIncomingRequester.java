import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;


public class ClientIncomingRequester implements Runnable
{
	MessageChannel incoming;
	InputStream input;
	public ClientIncomingRequester(MessageChannel in, InputStream is)
	{
			incoming = in;
			input = is;
	}
	public void run()
	{
		try {

			//pipe and filter to get event then pass it into channel
			ObjectInputStream ois = new ObjectInputStream(input);

			while(true)
			{
				//pipe and filter
				Event message = null;
				try {
					message= (Event)ois.readObject();
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				incoming.put(message);
				

			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}

