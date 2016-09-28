
public class Testing {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		final int PORT = 7111;
		
		//creates and starts the server and acceptor for that server
		IMServer server = new IMServer("bin/server.cfg", 7111);
				
		IMClient Jim = new IMClient("Jim", "bin/client.cfg");		
		
		IMClient Dale = new IMClient("Dale", "bin/client.cfg");	

		IMClient Alan = new IMClient("Alan", "bin/client.cfg");	
		

		IMClient Bob = new IMClient("Bob", "bin/client.cfg");	
		

		IMClient Tom = new IMClient("Tom", "bin/client.cfg");	
		
		
	}

}
