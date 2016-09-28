 /**
 * This class is an EventHandler used by the client to handle "logouts" 
 *
 */

public class ClientLogoutHandler extends EventHandler {
	
	public ClientLogoutHandler(){
		eventType = "LogoutMessage";
	}

	@Override
	public void handleEvent(Event e) {
		System.out.println("Logout Event received");

	}

	@Override
	public void handleEvent(Event evt, Client c) {
		System.out.println("Logout Event received");
		if (c != null)
		{
			c.removeContact(evt.getSource()); //add the incoming user to this clients list		
		}
	}

	@Override
	public void handleEvent(Event evt, Server s){
		// do nothing
	}
}
