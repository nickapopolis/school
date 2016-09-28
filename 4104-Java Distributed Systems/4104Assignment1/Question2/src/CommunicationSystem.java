import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.StringTokenizer;
import java.io.*;

public class CommunicationSystem 
{
	private long totalRuntime;
	private int channelSize;
	private String inputFile;
	private Node[] nodes;
	
	public CommunicationSystem(String fileName, long time, int size) 
	{
		inputFile = fileName;
		totalRuntime = time*1000; //convert from seconds to milliseconds
		channelSize = size;
		try {
			parseFile();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void parseFile()throws IOException, FileNotFoundException 
	{
		File fil = new File(inputFile);
		FileReader inputFil = new FileReader(fil);
		BufferedReader in = new BufferedReader(inputFil);
		
		//read number of nodes to create
		int numNodes = Integer.parseInt(in.readLine().trim());
		
		initializeNodes(numNodes);
		
		String s = in.readLine();
		while(s!=null)
		{
			s.trim();
			//read two consecutive integers in which a connection is to be made
			StringTokenizer st = new StringTokenizer(s);
			int n1 = Integer.parseInt(st.nextToken().trim());
			int n2 = Integer.parseInt(st.nextToken().trim());
			connect(n1-1,n2-1);
		    s = in.readLine();
		}
		in.close();
	}
	
	//initializes and creates nodes in communication system
	public void initializeNodes(int num)
	{
		nodes = new Node[num];
		
		for(int i=0;i<num;i++)
		{
			nodes[i] = new Node(i+1, totalRuntime);
		}
	}
	
	//creates a connection between two nodes
	public void connect(int left, int right)
	{
		if(left != right && nodes[left]!= null && nodes[right]!= null)
		{
			BiDirectionalNodeChannel ch = new BiDirectionalNodeChannel(channelSize);
			nodes[left].addChannel(ch);
			nodes[right].addChannel(ch);
			ch.connect(nodes[left].getPutThread(ch),nodes[left].getTakeThread(ch) , nodes[right].getPutThread(ch) ,nodes[right].getTakeThread(ch) );
		}
	}
	//starts the messaging between all of the nodes
	public void beginCommunication()
	{
		for(int i=0;i<nodes.length;i++)
		{
			nodes[i].startThreads();
		}
	}
	
}


   

