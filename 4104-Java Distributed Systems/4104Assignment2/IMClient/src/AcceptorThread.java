import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * This helper class accepts new connections then adds them to the Acceptors
 * socket list. It's intended to run in its own thread. 
 *
 */
public class AcceptorThread implements Runnable
{
	ServerSocket server;
	Acceptor acceptor;
	
	public AcceptorThread(ServerSocket ss, Acceptor a)
	{
		server = ss;
		acceptor = a;
	}
	
	public void run() 
	{
		try {
			while(true)
			{
				Socket s = server.accept();
				acceptor.addConnection(s);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
