
/**
 * This class defines the default event handler the Server uses to handle
 * user logouts
 *
 */
public class ServerLogoutHandler extends EventHandler {


	public ServerLogoutHandler(){
		eventType = "LogoutMessage";
	}
	

	/**
	 * Prints a message that a logout event has been recieved
	 * @param evt a logout Event
	 */
	@Override
	public void handleEvent(Event evt) {
		System.out.println("Logout Event receieved");		
	}

	/**
	 * This method is inherited from EventHandler and is not implemented
	 * as this class is specific to Servers
	 * @param evt a logout Event
	 * @param c a Client
	 */
	@Override
	public void handleEvent(Event evt, Client c) {
		// do nothing
	}

	/**
	 * Gets the name of the user that logged out and passes it to the server
	 * @param evt a logout Event
	 * @param s The server that gets notified of a logged out user
	 */
	@Override
	public void handleEvent(Event evt, Server s) {
		System.out.println("Logout event receieved");
		if(s != null){
			s.logOut(evt.getSource());
		}
	}

}
