import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class Message 
{
	int source;
	int destination;
	String msg;
	String time;
	int messageID;
	static int messageCount = 0;
	public Message(int source, int destination)
	{
		messageID = messageCount++;
		this.source = source;
		this.destination = destination;
		SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");
		Date date = Calendar.getInstance().getTime();
		time = df.format(date);
		msg = "message number " + messageID +" source: " + source+" destination: "+destination;
		//System.out.println("created new "+ msg);
	}
	public int getSource()
	{
		return source;
	}
	public int getDestination()
	{
		return destination;
	}
	public String getTime()
	{
		return time;
	}
	public String getMsg()
	{
		return msg;
	}
}
