
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.*;

@SuppressWarnings("serial")
public class IMClient 
{
		Client clientUI;
		MessageChannel outgoingRequests;
		MessageChannel incomingRequests;
		Connector connector;
		Reactor reactor;
		
		public IMClient(String userName, String fileName)
		{
			
			outgoingRequests = new MessageChannel(10);
			incomingRequests = new MessageChannel(10);
			
			clientUI = new Client(userName, outgoingRequests);
			connector = new Connector(incomingRequests, outgoingRequests);
			reactor = new Reactor(fileName, clientUI, incomingRequests);

			clientUI.initialize();
					
			Thread reactorThread = new Thread(reactor);
			reactorThread.start();
			
			Thread connectorThread = new Thread(connector);
			connectorThread.start();
					
	    	clientUI.setVisible(true);
	    	
		}
		public static void main(String[] args)
		{
			
			//process command line input
			
			int i= 0;
			String userName = "othernick";
			String fileName = "bin/client.cfg";
				
			while(i+1<args.length)
			{
				if(args[i].equals("-name"))
				{
					userName = args[i+1];
				}
				else if(args[i].equals("-c"))
				{
					fileName = args[i+1];
				}
				i+=2;
			}
			
			IMClient c = new IMClient(userName, fileName);
			
		}

}
