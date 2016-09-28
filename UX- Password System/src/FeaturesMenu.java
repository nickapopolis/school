import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;


public class FeaturesMenu extends JPanel implements ActionListener
{
	//sets length of password (number of attributes)
	private int passwordLength = 7;
	private int numValuesPerAttribute;
		
	private JButton[][] attributeButtons;
	private JLabel selectedAttributeLabel;
	
	private Border selectedBorder;
	private Border defaultBorder;
	private Border hintBorder;
	private boolean passwordVisible;
	private Border outlineBorder;
	private Image hair1img;
	
	
	private CharacterPanel characterPanel;
	private ArrayList<Feature> features;
	private int[] selectedButtons;
	
	private Password currentPassword;
	
	
	public FeaturesMenu(ArrayList<Feature> features, CharacterPanel panel)
	{
		this.features = features;
		characterPanel = panel;
		//determine how many values we need per attribute
		numValuesPerAttribute = Password.getNumberOfValuesForLength(passwordLength);
		
		//set border colours for selected buttons
		defaultBorder = new JButton().getBorder();
		selectedBorder =  BorderFactory.createLineBorder(Color.green, 3);
		hintBorder = BorderFactory.createLineBorder(Color.blue, 3);
		passwordVisible = false;
		
		
		
		//initialize array to track which button in each row is selected, default value none selected
		selectedButtons = new int[7];
		Arrays.fill(selectedButtons, -1);
		
		initialize();
	}
	
	/**
	 * initializes components
	 */
	private void initialize()
	{
		
		//set layout of menu to gridbag
		setLayout(new GridBagLayout());
		GridBagConstraints constraints= new GridBagConstraints();
		
		//default arguments for buttons
		constraints.insets = new Insets(14, 4, 8, 6);
		constraints.gridwidth = 1;
		constraints.gridheight = 1;
		constraints.weightx = 1;
		constraints.weighty = 1;
		constraints.fill = GridBagConstraints.BOTH;
		
		attributeButtons = new JButton[passwordLength][numValuesPerAttribute];
		
		
		for(int i=0;i<passwordLength;i++)
		{
			for(int j=0;j<numValuesPerAttribute;j++)
			{
				attributeButtons[i][j] = new JButton();
				constraints.gridx = j;
				constraints.gridy = i;
				constraints.fill = GridBagConstraints.BOTH;
				
				//set image of button
				ImageIcon icon = null;
				Value v = features.get(i).getValue(j);
				if(v!= null )
					icon = v.getButtonIcon();
				
				attributeButtons[i][j].setIcon(icon);
				attributeButtons[i][j].setSize(new Dimension(40,40));
				attributeButtons[i][j].setPreferredSize(new Dimension(40,40));
				
				//add listener
				attributeButtons[i][j].addActionListener(this);
				
				//add button to menu
				add(attributeButtons[i][j], constraints);
			}
		}
		for(int i=0;i<passwordLength;i++)
		{
			constraints.insets = new Insets(-1, 0, -1, 0);
			JPanel panel = new JPanel();
			outlineBorder = BorderFactory.createTitledBorder(features.get(i).getName());
			panel.setBorder(outlineBorder);
			constraints.gridwidth = numValuesPerAttribute;
			constraints.gridheight = 1;
			constraints.fill = GridBagConstraints.BOTH;
			constraints.weightx = 1;
			constraints.weighty = 1;
			constraints.gridx = 0;
			constraints.gridy = i;
			add(panel, constraints);
		}
		
		//add a label at bottom to give the name of the currently selected item
		selectedAttributeLabel = new JLabel("Selected element.");
		selectedAttributeLabel.setBackground(new Color(255,246,143));
		selectedAttributeLabel.setFont(new Font("Arial", Font.PLAIN, 20));
		selectedAttributeLabel.setOpaque(true);
		constraints.gridwidth = GridBagConstraints.REMAINDER;
		constraints.weighty = 0;
		constraints.gridx = 0;
		constraints.ipady = 15;
		constraints.gridy = passwordLength;
		add(selectedAttributeLabel, constraints);
		
	}
	
	/**
	 * Returns the combination of buttons that are selected as a password
	 * @return
	 */
	public Password getEnteredPassword()
	{
		return new Password(selectedButtons);
	}
	
	/**
	 * Resets the current password so that no buttons are selected
	 */
	public void resetMenu()
	{
		
		for(int i=0; i<passwordLength; i++)
		{
			if(selectedButtons[i]!= -1)
			{
				attributeButtons[i][selectedButtons[i]].setBorder(defaultBorder);
				selectedButtons[i] = -1;
			}
		}
		setPasswordVisible(passwordVisible);
		characterPanel.updateValues(selectedButtons);
	}
	public void setPassword(Password password)
	{
		if(passwordVisible)
		{
			setPasswordVisible(false);
			passwordVisible = true;
		}
		currentPassword = password;
		if(passwordVisible)
			setPasswordVisible(true);
			
	}
	
	public void setPasswordVisible(boolean visible)
	{

		if(visible)
			passwordVisible = true;
		else 
			passwordVisible = false;
			
		//no password selected, cannot highlight
		if(currentPassword == null)
		{
			return;
		}
		
		if (visible)
		{
			for(int i=0;i<passwordLength;i++)
			{
				attributeButtons[i][currentPassword.getPasswordValueForAttribute(i)].setBorder(hintBorder);
			}
		}
		else
		{
			for(int i=0;i<passwordLength;i++)
			{
				attributeButtons[i][currentPassword.getPasswordValueForAttribute(i)].setBorder(defaultBorder);
			}
		}
	}
	public boolean isPasswordVisible()
	{
		return passwordVisible;
	}
	public void setDisplayText(String text)
	{
		selectedAttributeLabel.setText(text);
	}
	public void actionPerformed(ActionEvent event) 
	{
		for(int i=0;i<passwordLength;i++)
		{
			for(int j=0;j<numValuesPerAttribute;j++)
			{
				if (event.getSource() == attributeButtons[i][j])
				{
					//disable highlight on previously selected button
					if(selectedButtons[i] != -1)
					{
						if(currentPassword != null && currentPassword.getPasswordValueForAttribute(i)== selectedButtons[i] && passwordVisible)
							attributeButtons[i][selectedButtons[i]].setBorder(hintBorder);
						else
							attributeButtons[i][selectedButtons[i]].setBorder(defaultBorder);
					}
					
					//enable highlight on currently selected button
					selectedButtons[i] = j;
					attributeButtons[i][j].setBorder(selectedBorder);
					Feature f = features.get(i);
					if(f!= null)
					{
						Value v = f.getValue(j);
						if(v != null)
							selectedAttributeLabel.setText(v.getValueName());
					}
					characterPanel.updateValues(selectedButtons);
					break;
				}
			}
		}
	}
	
	
}
