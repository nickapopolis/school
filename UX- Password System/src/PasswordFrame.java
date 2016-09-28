import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class PasswordFrame extends JFrame implements ActionListener
{
	private int windowWidth = 800;
	private int windowHeight = 630;
	private ArrayList<Feature> features;
	private PasswordSession passwordSession;
	
	private FeaturesMenu featuresMenu;
	private CharacterPanel characterPanel;
	private JLabel passwordSelectionLabel;
	private JLabel passwordModeLabel;
	private AttemptsRemainingPanel attemptsRemainingPanel;
	private JButton enterButton;
	private JButton nextButton;
	
	public PasswordFrame(ArrayList<Password> passwords)
	{
		super("Enter your password");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		
		features = this.loadFeaturesFromFile("PasswordAttributes.txt");
		
		
		//initialize log file with random participant id
		passwordSession = new PasswordSession(passwords);
		
		//initialize ui
		initialize();
		
		//center frame on screen
		int xPos = (int)(java.awt.Toolkit.getDefaultToolkit().getScreenSize().getWidth()/2 - getWidth()/2);
		int yPos = (int)(java.awt.Toolkit.getDefaultToolkit().getScreenSize().getHeight()/2 - getHeight()/2);
		this.setLocation(xPos, yPos);
		
		setVisible(true);
		
		
	}
	
	/**
	 * initializes components
	 */
	private void initialize()
	{
		//set size of window
		setSize(windowWidth, windowHeight);
		
		//set layout type to gridbag
		setLayout(new GridBagLayout());
		GridBagConstraints constraints = new GridBagConstraints();
		
		//default arguments for each item
		constraints.insets = new Insets(5, 5, 5, 5);
		
		//label showing what password is selected
		passwordSelectionLabel = new JLabel();
		passwordSelectionLabel.setFont(new Font("Arial", Font.PLAIN, 30));
		constraints.gridwidth = 1;
		constraints.gridheight = 1;
		constraints.weightx = 1;
		constraints.weighty = 0;
		constraints.gridx = 0;
		constraints.gridy = 0;
		constraints.fill = GridBagConstraints.HORIZONTAL;
		add(passwordSelectionLabel, constraints);
		
		//label showing what enter mode the user is in
		passwordModeLabel = new JLabel("<html><font color=darkGrey><font><font color= blue><font>");
		passwordModeLabel.setFont(new Font("Arial", Font.PLAIN, 30));
		passwordModeLabel.setForeground(Color.blue);
		constraints.gridwidth = 1;
		constraints.gridheight = 1;
		constraints.weightx = 0;
		constraints.weighty = 0;
		constraints.gridx = 1;
		constraints.gridy = 0;
		constraints.fill = GridBagConstraints.HORIZONTAL;
		add(passwordModeLabel, constraints);
		
		//panel for viewing the person with selected features
		characterPanel = new CharacterPanel(features);
		constraints.gridwidth = 1;
		constraints.gridheight = 1;
		constraints.weightx = 0.5;
		constraints.weighty = 1;
		constraints.gridx = 1;
		constraints.gridy = 1;
		constraints.fill = GridBagConstraints.BOTH;
		constraints.ipadx = 300;
		add(characterPanel, constraints);
		
		//reset padding
		constraints.ipadx = 0;
		
		//features menu for password entry
		featuresMenu = new FeaturesMenu(features, characterPanel);
		constraints.gridwidth = 1;
		constraints.gridheight = 1;
		constraints.weightx = 0.5;
		constraints.weighty = 1;
		constraints.gridx = 0;
		constraints.gridy = 1;
		constraints.fill = GridBagConstraints.BOTH;
		add(featuresMenu, constraints);
		
		//button to enter password
		enterButton = new JButton("Enter");
		enterButton.setFont(new Font("Arial", Font.PLAIN, 25));
		enterButton.addActionListener(this);
		constraints.gridwidth = 1;
		constraints.gridheight = 1;
		constraints.weightx = 1;
		constraints.weighty = 0;
		constraints.gridx = 0;
		constraints.gridy = 2;
		constraints.ipady = 20;
		constraints.fill = GridBagConstraints.BOTH;
		add(enterButton, constraints);
		
		constraints.ipady = 0;
		
		//button to enter password
		attemptsRemainingPanel = new AttemptsRemainingPanel();
		constraints.gridwidth = 1;
		constraints.gridheight = 1;
		constraints.weightx = 0;
		constraints.weighty = 0;
		constraints.gridx = 1;
		constraints.gridy = 2;
		add(attemptsRemainingPanel, constraints);
		attemptsRemainingPanel.setVisible(false);
		
		nextButton = new JButton("Next");
		nextButton.setFont(new Font("Arial", Font.PLAIN, 25));
		nextButton.addActionListener(this);
		add(nextButton, constraints);
		
	}
		
	/**
	 * checks to see if the entered password is equivalent to the current password
	 * @return
	 */
	public boolean verifyPassword()
	{
		Password enteredPassword = featuresMenu.getEnteredPassword();
		return passwordSession.verifyPassword(enteredPassword);
	}
	
	/**
	 * Sets the text in the display tooltip.
	 * @param text
	 */
	public void setDisplayText(String text)
	{
		featuresMenu.setDisplayText(text);
	}
	
	/**
	 * button listener response
	 */
	@Override
	public void actionPerformed(ActionEvent e) 
	{
		if( e.getSource() == nextButton)
		{
			featuresMenu.resetMenu();
			nextPassword();			
		}
		else if(e.getSource() == enterButton)
		{
			Password nextPassword = null;
			boolean passwordMatches = verifyPassword();
			featuresMenu.resetMenu();
			
			if(passwordMatches)
			{
				//password entered correctly
				setDisplayText("Correct Password.");
				if(passwordSession.getPasswordMode() == PasswordSession.PasswordMode.CREATE)
				{
					//entered password correctly on learning phase, get them to enter it with without hint
					if(featuresMenu.isPasswordVisible())
					{
						passwordModeLabel.setText("<html><font color=darkGrey>Step 2: </font><font color=FFCC33>Practice</font></html>");
						featuresMenu.setPasswordVisible(false);
					}
					else
					{
						nextButton.setEnabled(true);
					}
					return;
				}
			}
			else
			{
				//password entered incorrectly
				setDisplayText("Incorrect Password.");
				
				//entered password incorrectly on learning phase, give them the hint
				if(passwordSession.getPasswordMode() == PasswordSession.PasswordMode.CREATE)
				{
					passwordModeLabel.setText("<html><font color=darkGrey>Step 1: </font><font color=red>Learn</font></html>");
					featuresMenu.setPasswordVisible(true);
					return;
				}
				else if(passwordSession.getPasswordMode() == PasswordSession.PasswordMode.LOGIN &&
						passwordSession.getPasswordAttempts()<3)
				{
					attemptsRemainingPanel.setAttemptsUsed(passwordSession.getPasswordAttempts());
					return;
				}
			}
			nextPassword();
		}
	}
	private void nextPassword()
	{
		//go to the next password
		Password nextPassword = passwordSession.nextPassword();
		attemptsRemainingPanel.setAttemptsUsed(0);
		
		//if in login mode make sure user cannot see the password hint
		if (passwordSession.getPasswordMode() == PasswordSession.PasswordMode.CREATE)
		{
			featuresMenu.setPasswordVisible(true);
			nextButton.setEnabled(false);
			nextButton.setVisible(true);
			attemptsRemainingPanel.setVisible(false);
			passwordModeLabel.setText("<html><font color=darkGrey>Step 1: </font><font color=red>Learn</font></html>");
		}
		else
		{
			featuresMenu.setPasswordVisible(false);
			nextButton.setEnabled(false);
			nextButton.setVisible(false);
			attemptsRemainingPanel.setVisible(true);
			passwordModeLabel.setText("<html><font color=darkGrey>Step 3: </font><font color=lime>Log in</font></html>");
			}
		
		//test done, no passwords left so finish
		if(nextPassword == null)
		{
			endPasswordTest();
			return;
		}
		
		setPassword(nextPassword);
	}
	
	public void beginPasswordTest()
	{
		passwordSession.begin();
		nextPassword();
		
	}
	public void endPasswordTest()
	{
		passwordSession.end();
		dispose();
	}
	public void setPassword(Password password)
	{
		passwordSelectionLabel.setText("<html><font color=darkGrey>Enter your </font><font color=red>"+password.getDomain()+"</font><font color=darkGrey> password.</font></html>");
		featuresMenu.setPassword(password);
		featuresMenu.resetMenu();
		if(passwordSession.getPasswordMode() == PasswordSession.PasswordMode.CREATE)
			featuresMenu.setPasswordVisible(true);
	}
	private ArrayList<Feature> loadFeaturesFromFile(String fileName)
	{
		BufferedReader reader;
		String line;
	       
		System.out.println("[were are getting closer]: " + this.getClass().getResource(""));
		 
		System.out.println("[perhaps here (is it not null)?]: " + this.getClass().getResource("PasswordAttributes.txt"));
		System.out.println("[perhaps here (is it not null)?]: " + this.getClass().getResource("../PasswordAttributes.txt"));
		
		try {
			try{
			reader = new BufferedReader(new FileReader(fileName));
			}
			catch(Exception e)
			{
				reader = new BufferedReader(new InputStreamReader(PasswordFrame.class.getResourceAsStream(fileName)));
			}
			Feature currentFeature = null;
			ArrayList<Feature> tempFeatures = new ArrayList<Feature>();
			
			while((line = reader.readLine())!= null)
			{
				//read file line by line
				//@feature name : is a feature with specified name
				
				if(line.startsWith("@feature"))
				{
					String featureName = line.split("@feature")[1].trim();
					currentFeature = new Feature(featureName);
					tempFeatures.add(currentFeature);
				}
				else
				{
					String[] values = line.split(",");
					String valueName = values[0].trim();
					String valueImgFile = values[1].trim();
					Value value;
					
					if(values.length>2)
						value = new Value(valueName, valueImgFile, values[2].trim());
					else
						value = new Value(valueName, valueImgFile);
					
					//add value to feature
					if(currentFeature!= null)
						currentFeature.addValue(value);
				}
					
			}
			return tempFeatures;
			
		} catch (FileNotFoundException e) {
			System.err.println("Error opening file: " + fileName);
			e.printStackTrace();
		} catch (IOException e) {
			System.err.println("Error reading file: " + fileName);
			e.printStackTrace();
		}
		return null;
	}
	public static void main(String[] args)
	{
		ArrayList<Password> passwords = new ArrayList<Password>();
		passwords.add(new Password("E-mail"));
		passwords.add(new Password("Facebook"));
		passwords.add(new Password("Bank account"));
		
		PasswordFrame frame = new PasswordFrame(passwords);
		frame.beginPasswordTest();
	}

	
}

