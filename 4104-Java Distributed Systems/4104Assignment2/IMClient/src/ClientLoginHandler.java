/**
 * This class is an event handler used by the client to handle "login" events
 *
 */

public class ClientLoginHandler extends EventHandler {

	//needs to know which client it belongs to so that it can handle its events
	Client client;
	
	public ClientLoginHandler(){
		eventType = "LoginMessage";
	}
	public ClientLoginHandler(Client c){
		eventType = "LoginMessage";
		client = c;
	}
	public void setClient(Client c)
	{
		client = c;
	}
	
	@Override
	public void handleEvent(Event e) {
		System.out.println("Login Event received");
	}
	@Override
	public void handleEvent(Event evt, Client c) {
		System.out.println("Login Event received");
		if (c != null)
		{
			c.addContact(evt.getSource()); //add the incoming user to this clients list		
		}
	}
	
	@Override
	public void handleEvent(Event evt, Server s){
		// do nothing
	}

}
