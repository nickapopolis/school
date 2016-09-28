
public class IMServer 
{
	Server server;
	Acceptor acceptor;
	protected final int PORT;
	Reactor reactor;
	MessageChannel incoming;
	MessageChannel outgoing;
	
	public IMServer(String configFile,int port)
	{
		PORT = port;
		//server = new Server(PORT);
		//acceptor = new Acceptor(10, PORT);
		
		
		//Thread acceptorThread = new Thread(server);
		//acceptorThread.start();
		incoming = new MessageChannel(50);
		outgoing = new MessageChannel(50);
		server = new Server(outgoing, PORT);
		acceptor = new Acceptor(10, incoming, outgoing, PORT);
		reactor = new Reactor(configFile, server, incoming);
		Thread t = new Thread(acceptor);
		Thread t2 = new Thread(reactor);
		t.start();
		t2.start();
	}
	public static void main(String[] args)
	{
		//process command line input
		
		int i= 0;
		String configFile = "bin/server.cfg";
			
		while(i+1<args.length)
		{
			
			if(args[i].equals("-c"))
			{
				configFile = args[i+1];
			}
			i+=1;
		}
		
		IMServer c = new IMServer(configFile, 7111);
	}
}
