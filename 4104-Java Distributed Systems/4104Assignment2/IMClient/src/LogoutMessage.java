import java.io.Serializable;

/**
 * This class represents an Event that indicates that a user has logged out
 *
 */
public class LogoutMessage extends Event implements Serializable
{

	private String username;
	
	public LogoutMessage(String s, String d)
	{
		super("LogoutMessage", s, d);
		username = s;
	}
	public String getUsername()
	{
		return username;
	}
	public String toString()
	{
		return username + " is logging out";
	}
}
