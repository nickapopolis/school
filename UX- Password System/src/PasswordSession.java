import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

public class PasswordSession 
{
	public static enum PasswordEvent{START_SESSION, END_SESSION, START,END, SUCCESS,FAILURE, ENTER};
	public static enum PasswordMode{CREATE, LOGIN};
	
	//log data
	String 	userName;
	ArrayList<String> eventLog;
	
	//active data
	PasswordMode currentMode;
	int passwordAttempts;
	int currentPasswordIndex;
	ArrayList<Password> passwords;
	private String logFileName = "logfile.csv";
	
	
	public PasswordSession(ArrayList<Password> passwords)
	{
		userName = new Integer(new Random().nextInt(10000)).toString();
		this.passwords = passwords;
		eventLog = new ArrayList<String>();
		currentMode = PasswordMode.CREATE;
	}
	public void begin()
	{
		currentMode = PasswordMode.CREATE;
		currentPasswordIndex = -2;
		passwordAttempts = 0;
		
		recordEvent("", userName, PasswordMode.CREATE, PasswordEvent.START_SESSION, "");
	}
	public void end()
	{
		recordEvent("", userName, PasswordMode.LOGIN, PasswordEvent.END_SESSION, "");
		appendToFile(logFileName);
	}
	public boolean verifyPassword(Password password)
	{
		passwordAttempts++;
		Password currentPassword = getPassword();
		recordEvent(currentPassword.getDomain(), userName, currentMode, PasswordEvent.ENTER, password.toString());
		
		boolean success = password.equals(currentPassword);
		recordEvent(currentPassword.getDomain(), userName, currentMode, success?PasswordEvent.SUCCESS:PasswordEvent.FAILURE, "");		
		
		return success;
	}
	public Password nextPassword()
	{
		//record end of previous password
		Password prevPassword = getPassword();
		if(prevPassword != null)
			recordEvent(prevPassword.getDomain(), userName, currentMode, PasswordEvent.END, prevPassword.toString());
		
		passwordAttempts = 0;
	
		if(currentPasswordIndex == -2)
		{
			currentPasswordIndex = 0;
			currentMode = PasswordMode.CREATE;
		}
		else if(currentPasswordIndex+1 >= passwords.size() && currentMode == PasswordMode.LOGIN)
			currentPasswordIndex = -1;
		else if(currentPasswordIndex+1 < passwords.size())
			currentPasswordIndex++;
		else 
		{
			currentPasswordIndex = 0;
			currentMode = PasswordMode.LOGIN;
		}
		
		//record start of current password
		Password currentPassword = getPassword();
		if(currentPassword!= null)
			recordEvent(currentPassword.getDomain(), userName, currentMode, PasswordEvent.START, currentPassword.toString());
		
		return currentPassword;
	}
	public Password getPassword()
	{
		if(currentPasswordIndex <0)
			return null;
		
		return passwords.get(currentPasswordIndex);
	}
	public PasswordMode getPasswordMode()
	{
		return currentMode;
	}
	public int getPasswordAttempts()
	{
		return passwordAttempts;
	}
	private void recordEvent(String passwordDomain, String userName, PasswordMode mode, PasswordEvent event, String data)
	{
		String eventStr = getTimestamp() + " , " + (passwordDomain==null?"":passwordDomain) + " , " + userName + " , " + (mode==null?"":mode.toString()) + " , " +(event==null?"":event.toString()) +" , "+ data +"\n"; 
		eventLog.add(eventStr);
	}
	public String getTimestamp()
	{
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date();
		return dateFormat.format(date);
	}
	public void appendToFile(String fileName)
	{
		try
		{
			FileWriter fstream = new FileWriter(fileName ,true);
			BufferedWriter out = new BufferedWriter(fstream);
			
			for(String event:eventLog)
			{
				out.write(event);
				out.flush();
			}
			
		} catch (IOException e)
		{
			System.err.println("Error opening file: "+ fileName);
			e.printStackTrace();
		}
	}
}
