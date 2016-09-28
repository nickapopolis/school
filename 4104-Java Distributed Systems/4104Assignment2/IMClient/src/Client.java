import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

import javax.swing.*;

@SuppressWarnings("serial")
public class Client extends JFrame {
		
		
		String userName;
		ArrayList<DocumentMessage> messages;
		ArrayList<String> contacts;
		MessageChannel outgoing;
		
		JButton sendButton;
		JTextArea messageList;
		JTextArea contactsList;
		LinePainter contactsListPainter;
		JTextArea inputText;
		JLabel userLabel;
		Action selectLine;
		
		
	    public Client(String uN, MessageChannel outgoing)
	    {
	    	super("Instant Messenger");
	    	setSize(520,350);
	    	setResizable( true );
	    	setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
	    	
	    	addWindowListener(new WindowAdapter()
	    	{
	    	      public void windowClosing(WindowEvent e)
	    	      {
	    	    	  setVisible(false);
	    	    	  signOut();
	    	          dispose();
	    	      }
	    	});

	    	this.outgoing = outgoing;
	    	userName = uN;
	    	messages = new ArrayList<DocumentMessage>();
	    	contacts = new ArrayList<String>();
	    	
	    }



		public void initialize()
		{
			
			GridBagLayout layout = new GridBagLayout();
	        GridBagConstraints constraints = new GridBagConstraints();
	        setLayout(layout);

	        //add all the individual spaces to the board

	        //set constraints of spaces
		    

			constraints.fill = GridBagConstraints.NONE;
			constraints.gridwidth = 1;
		    constraints.gridheight = 1;
		    constraints.gridy = 0;
			constraints.gridx = 0;
			constraints.weightx = 1;

	       // constraints.ipadx = 100;
			constraints.insets = new Insets(5, 15, 5, 15);
		    constraints.anchor = GridBagConstraints.WEST;
			userLabel = new JLabel("User:");
		    layout.setConstraints(userLabel, constraints);
		    add(userLabel);
		    

		    

		    constraints.weighty = 1;
		    constraints.fill = GridBagConstraints.BOTH;
	        constraints.gridwidth = 2;
		    constraints.gridheight = 1;
	        //constraints.ipadx = 100;
		    constraints.gridy = 1;
			constraints.gridx = 0;
			contactsList = new JTextArea();
			contactsList.setEditable(false);
		    layout.setConstraints(contactsList, constraints);
		    JScrollPane contactsScrollPane = new JScrollPane(contactsList);
		    layout.setConstraints(contactsScrollPane, constraints);
	        add( contactsScrollPane);
		    
		    constraints.fill = GridBagConstraints.BOTH;
	        constraints.gridwidth =2;
		    constraints.gridheight = 1;
	        //constraints.ipadx = 100;
		    constraints.gridy = 2;
			constraints.gridx = 0;
			messageList = new JTextArea(3, 19);
			messageList.setEditable(false);
		    layout.setConstraints(messageList, constraints);
		    JScrollPane scrollPane = new JScrollPane( messageList);
		    layout.setConstraints(scrollPane, constraints);
	        add( scrollPane);
		    

			constraints.insets = new Insets(5, 15, 5, 0);
		    constraints.fill = GridBagConstraints.BOTH;
	        constraints.gridwidth = 1;
		    constraints.gridheight = 1;
		    constraints.gridy = 3;
			constraints.gridx = 0;
			constraints.weightx = .7;
		    constraints.weighty = 9/10;
			inputText = new JTextArea("");
			inputText.setLineWrap(true);
			inputText.setWrapStyleWord(true);
		    layout.setConstraints(inputText, constraints);
		    JScrollPane inputScrollPane = new JScrollPane( inputText);
		    layout.setConstraints(inputScrollPane, constraints);
	        add( inputScrollPane);
		    

			constraints.insets = new Insets(5, 0, 5, 15);
		    constraints.fill = GridBagConstraints.NONE;
	        constraints.gridwidth = 1;
		    constraints.gridheight = 1;

			constraints.weightx =.3;
		    constraints.gridy = 3;
			constraints.gridx = 1;
			constraints.anchor = GridBagConstraints.EAST;
			sendButton = new JButton("Send");
		    layout.setConstraints(sendButton, constraints);
		    
		    
			
	        sendButton.addActionListener
	    	(
	       		new ActionListener()
	       		{
	                public void actionPerformed(ActionEvent theEvent)
	                {
	                	sendMessage();
	                }
	       		}
			);
			add(sendButton);
			setUser(userName);
			
			signIn();
			
		}
		public void setUser(String userName)
		{
			userLabel.setText("User: "+ userName);
		}
		public void setContacts(ArrayList<String> contacts)
		{
			contactsList.setText("");
			for(int i=0; i< contacts.size(); i++)
			{
				if(i == contacts.size()-1)
				{
					contactsList.append(contacts.get(i));
				}
				else
				{
					contactsList.append(contacts.get(i)+"\n");
				}
			}
		}
		public void setMessages(ArrayList<DocumentMessage> messages)
		{
			messageList.setText("");
			for(int i=0; i<messages.size(); i++)
			{
				messageList.append(messages.get(i).toString() + "\n");
				}
		}
		public void addMessage(DocumentMessage m)
		{
			messages.add(m);
			setMessages(messages);
		}
		public void addContact(String name)
		{
			//if (!contacts.contains(name)){
				contacts.add(name);
				setContacts(contacts);
				contactsListPainter = new LinePainter(contactsList);
			//}
		}
		public void removeContact(String name)
		{
			for(int i=0; i<contacts.size(); i++)
			{
				String contact = contacts.get(i);
				if (contact.equals(name))
				{
					contacts.remove(contact);
				}
			}
			setContacts(contacts);
			if(contacts.size() == 0)
			{
				if (contactsListPainter !=null)
				{
					contactsListPainter.disable();
					contactsListPainter = null;
				}
			}
		}
		public void sendMessage()
		{
			String selectedContact = null;
			if(contactsListPainter!=null)
			{
				selectedContact = contactsListPainter.getSelectedText();
			}
        	String messageText = inputText.getText();
        	
        	if(selectedContact == null)
        	{
        		JOptionPane.showMessageDialog(this, "Error: no message recipient selected.");
        	}
        	else
        	{
	        	DocumentMessage newMessage = new DocumentMessage(userName, selectedContact, messageText);
	    		Thread t = new Thread(new OutgoingRequester(outgoing, newMessage));
	    		t.start();
    		}
        	inputText.setText("");
		}
		public void signIn()
		{
			LoginMessage newMessage = new LoginMessage(userName, null);
			Thread t = new Thread(new OutgoingRequester(outgoing, newMessage));
			t.start();
		}
		public void signOut()
		{
			LogoutMessage newMessage = new LogoutMessage(userName, null);
			Thread t = new Thread(new OutgoingRequester(outgoing, newMessage));
			t.start();
		}
}