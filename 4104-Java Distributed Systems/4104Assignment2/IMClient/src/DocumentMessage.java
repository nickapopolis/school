import java.io.Serializable;

/**
 * This class represents a message that is sent by a user.
 *
 */
public class DocumentMessage extends Event implements Serializable {

	private String message;
	
	public DocumentMessage(String s, String d, String m)
	{
		super("DocumentMessage",s, d );
		message = m;
	}
	public String getMessage()
	{
		return message;
	}
	public String toString()
	{
		return source +": "+ message;
	}
}
