import java.io.Serializable;

/**
 * This class represents an Event that indicates that a user has logged in 
 *
 */
public class LoginMessage extends Event implements Serializable
{
	private String username;
	
	public LoginMessage(String s, String d)
	{
		super("LoginMessage", s, d);
		username = s;
	}
	public String getUsername()
	{
		return username;
	}	
	public String toString()
	{
		return username + " is logging in";
	}
}
