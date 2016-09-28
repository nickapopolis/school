/**
 * This class defines the default event handler the server uses for  
 * messages
 */
public class ServerMessageHandler extends EventHandler {

	public ServerMessageHandler(){
		eventType = "DocumentMessage";
	}
	
	/**
	 * Prints a message that a message has been receieved
	 * @param evt a message Event
	 */
	@Override
	public void handleEvent(Event evt) {
		System.out.println("ClientMessage Event recieved");
	}


	/**
	 * This method is inherited from EventHandler and is not implemented
	 * as this class is specific to Servers
	 * @param evt a message Event
	 * @param c a Client
	 */
	@Override
	public void handleEvent(Event evt, Client c) {
		// not implemented
	}

	/**
	 * Notifies the Server that a message has been recieved from a client
	 * @param evt a message Event
	 * @param s The server that gets notified
	 */
	@Override
	public void handleEvent(Event evt, Server s) {
		DocumentMessage DM;
		
		try{
			DM = (DocumentMessage) evt;
		}catch(Exception e){
			System.out.println("Error occured while handling Message; event is not of type DocumentMessage");
			return;
		}
		
		if(s != null){
			s.sendMessage(DM);
		}
		
	}

}
