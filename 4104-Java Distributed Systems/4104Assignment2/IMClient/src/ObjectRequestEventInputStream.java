import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;


public class ObjectRequestEventInputStream{
	ObjectRequestInputStream previous;
	
	public ObjectRequestEventInputStream(ObjectRequestInputStream in) throws IOException {
		previous = in;
	}
	
	
	public Event readRequest() 
	{
		Event e = null;
		
		try{
			e = (Event) previous.readRequest();
		}
		catch(Exception e1)
		{
			System.out.println(e1.getCause());
		}
		return e;
	}
}
