
public class OutgoingRequester implements Runnable
{
		MessageChannel requests;
		Object message;
		
		public OutgoingRequester(MessageChannel req,Object  m)
		{
			requests = req;
			message = m;
		}
		public void run() 
		{
			requests.put(message);
		}

}
