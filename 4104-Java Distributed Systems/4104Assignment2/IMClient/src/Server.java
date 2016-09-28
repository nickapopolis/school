import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class Server 
{
	protected ArrayList<String> users;
	String logFileName = "server-log.txt";
	PrintWriter logFile;
	protected final int PORT;
	MessageChannel outgoing;
	
	public Server(int aPort)
	{
		initializeLogFile();
		PORT = aPort;
	}
	
	
	public Server(MessageChannel out, int aPort)
	{
		PORT = aPort;
		outgoing = out;
		users = new ArrayList<String>();
		initializeLogFile();
	}
	/**
	 * Opens the server's log file to be ready for writing.
	 */
	private void initializeLogFile() 
	{
		try {
			logFile = new PrintWriter(new FileWriter(logFileName, true));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public String getTime()
	{
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yy HH:mm:ss");
		Date date = new Date();
		return dateFormat.format(date);
		
	}
	/**
	 * User has signed in. Updates all other users that this user has come online.
	 * @param userName
	 */
	public synchronized void logIn(String userName)
	{
		//log time and action
		logFile.println(getTime()+ " - " + userName + " Signed in.");
		logFile.flush();
		
		for(int i=0; i<users.size(); i++)
		{
			LoginMessage e = new LoginMessage(userName, users.get(i));
			//tell all users that this contact has signed in
			Thread t = new Thread(new OutgoingRequester(outgoing,e));
			//tell signing in user which contacts are signed in already
			Thread t1 = new Thread(new OutgoingRequester(outgoing,new LoginMessage(users.get(i),userName)));
			t.start();
			t1.start();
		}
		users.add(userName);
		//send message to all users notifying them
	}
	/**
	 * User has signed out. Updates all other users that this user has gone offline
	 * @param userName
	 */
	public synchronized void logOut(String userName)
	{
		//log time and action
		logFile.println(getTime()+ " - " + userName + " Signed out.");
		logFile.flush();
		String user = getUser(userName);
		if (user!=null)
		{
			users.remove(userName);
			for(int i=0; i<users.size(); i++)
			{
				LogoutMessage e = new LogoutMessage(userName, users.get(i));
				Thread t = new Thread(new OutgoingRequester(outgoing,e));
				t.start();
			}
		}
		//send message to all users notifying them
	}
	/**
	 * A message has been sent from a user. Checks if the destination is valid and if so sends the message
	 * @param message
	 */
	public synchronized void sendMessage(DocumentMessage message)
	{
		//log time and action
		logFile.println(getTime()+ " - Message from:" + message.getSource() +" to "+ message.toString());
		logFile.flush();
		String user = getUser(message.getDestination());
		if(user!=null)
		{
			//send message to user
			Thread t = new Thread(new OutgoingRequester(outgoing,message));
			t.start();
		}
	}
	private String getUser(String userName)
	{
		for(int i=0; i <users.size(); i++)
		{
			if(users.get(i).equals(userName))
			{
				return users.get(i);
			}
		}
		return null;
	}
	
	
	
	
}
