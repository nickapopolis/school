import java.io.Serializable;



public class Message implements Serializable
{

	private String header;
	private Object body;
	
	public Message(String h, Object b)
	{
		header = h;
		body = b;
	}
	
	public String getHeader()
	{
		return header;
	}
	public Object getBody()
	{
		return body;
	}
}
