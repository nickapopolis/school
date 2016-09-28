
public class ClientMessageHandler extends EventHandler {

	public ClientMessageHandler(){
		eventType = "DocumentMessage";
	}
	
	public void handleEvent(Event e) {
		System.out.println("ClientMessage Event received");
	}

	@Override
	public void handleEvent(Event evt, Client c) {
		System.out.println("ClientMessage Event received");
		DocumentMessage DM;
		
		try
		{
			DM = (DocumentMessage)evt;	
		}
		catch(Exception e)
		{
			System.out.println("error sending message; event is not of type DocumentMessage");
			return;
		}
		
		
		if (c != null)
		{
			c.addMessage(DM); //add the incoming user to this clients list		
		}
	}

	@Override
	public void handleEvent(Event evt, Server s){
		// do nothing
	}
}
