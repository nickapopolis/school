
/**
 * This class defines the default event handler the server uses for  
 * user logins
 */
public class ServerLoginHandler extends EventHandler {
	
	/**
	 * Constructs a ServerLoginHandler
	 */
	public ServerLoginHandler(){
		eventType = "LoginMessage";
	}

	/**
	 * Prints a message that a login event has been recieved
	 * @param evt a login Event
	 */
	@Override
	public void handleEvent(Event evt) {
	}

	/**
	 * This method is inherited from EventHandler and is not implemented
	 * as this class is specific to Servers
	 * @param evt a login Event
	 * @param c a Client
	 */
	@Override
	public void handleEvent(Event evt, Client c) {
		// do nothing
	}

	/**
	 * Gets the name of the user that logged in and passes it to the server
	 * @param evt a login Event
	 * @param s The server that gets notified of a logged in user
	 */
	@Override
	public void handleEvent(Event evt, Server s) {
		if(s != null){
			s.logIn(evt.getSource());
		}
	}

}
