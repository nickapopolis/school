import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import javax.imageio.ImageIO;
import javax.swing.JPanel;


public class CharacterPanel extends JPanel
{
	private boolean running = true;
	private static BufferedImage bodyImage; 
	private ArrayList<Feature> features;
	int[] selectedValues = new int[7];
	
	final int HAIR =0, FACIAL_HAIR =1, EYEBROWS=2, NOSE=3, EYES=4, GLASSES=5, MOUTH=6;
	
	
	public CharacterPanel(ArrayList<Feature> features)
	{
		this.features = features;
		Arrays.fill(selectedValues, -1);
		loadContent();
	}
	private void loadContent()
	{
		try{
		bodyImage = ImageIO.read(new File("Images/body.png"));
		}
		catch(Exception e)
		{
			try {
				bodyImage = ImageIO.read(PasswordFrame.class.getResource("Images/body.png"));
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		
	}
	public void paint(Graphics g)
	{
		Color prevColor = g.getColor();
		g.setColor(Color.lightGray);
		g.fillRect(0, 0, this.getWidth(), this.getHeight());
		g.setColor(Color.DARK_GRAY);
		g.fillRect(0, 0, this.getWidth(), (int)(this.getHeight()*0.8));
		g.setColor(Color.gray);
		g.fillOval((int)(getWidth()*0.1), (int)(getHeight()*0.9), (int)(getWidth()*0.8), (int)(getWidth()*0.8));
		g.drawImage(bodyImage,  0, (int)(getHeight()*0.1), getWidth(), (int)(getHeight()*0.9), this);
				
		//nose
		if(selectedValues[NOSE] != -1)
		{
			Image noseImg = features.get(NOSE).getValue(selectedValues[NOSE]).getDrawImage();
			if(noseImg!= null)
				g.drawImage(noseImg,(int)(getWidth()*0.35), (int)(getHeight()*0.275 ), (int)(getWidth()*0.3), (int)(getHeight()* 0.10), this);
		}
		
		//mouth
		if(selectedValues[MOUTH] != -1)
		{
			Image mouthImg = features.get(MOUTH).getValue(selectedValues[MOUTH]).getDrawImage();
			if(mouthImg!= null)
				g.drawImage(mouthImg, (int)(getWidth()*0.25), (int)(getHeight()*0.325 ), (int)(getWidth()*0.5), (int)(getHeight()* 0.2), this);
		}
		//facial hair
		if(selectedValues[FACIAL_HAIR] != -1)
		{
			Image facialHairImg = features.get(FACIAL_HAIR).getValue(selectedValues[FACIAL_HAIR]).getDrawImage();
			if(facialHairImg!= null)
				g.drawImage(facialHairImg,(int)(getWidth()*0.125), (int)(getHeight()*0.225 ), (int)(getWidth()*0.75), (int)(getHeight()* 0.35), this);
		}
		
		//eyebrows
		if(selectedValues[EYEBROWS] != -1)
		{
			Image eyebrowsImg = features.get(EYEBROWS).getValue(selectedValues[EYEBROWS]).getDrawImage();
			if(eyebrowsImg != null)
				g.drawImage(eyebrowsImg,(int)(getWidth()*0.45), (int)(getHeight()*0.125 ), (int)(getWidth()*0.4),(int)(getHeight()* 0.2), this);
					
			Image eyebrowsMirrorImg = features.get(EYEBROWS).getValue(selectedValues[EYEBROWS]).getMirroredDrawImage();
			if(eyebrowsMirrorImg != null)
				g.drawImage(eyebrowsMirrorImg,(int)(getWidth()*0.15), (int)(getHeight()*0.125 ), (int)(getWidth()*0.4), (int)(getHeight()* 0.2),this);
		}
		
		//eyes
		if(selectedValues[EYES] != -1)
		{
			Image eyesImg = features.get(EYES).getValue(selectedValues[EYES]).getDrawImage();
			if(eyesImg != null)
				g.drawImage(eyesImg, (int)(getWidth()*0.45), (int)(getHeight()*0.175 ), (int)(getWidth()*0.4),(int)(getHeight()* 0.2), this);
			
			Image eyesMirrorImg = features.get(EYES).getValue(selectedValues[EYES]).getMirroredDrawImage();
			if(eyesMirrorImg != null)
				g.drawImage(eyesMirrorImg, (int)(getWidth()*0.15), (int)(getHeight()*0.175 ), (int)(getWidth()*0.4), (int)(getHeight()* 0.2), this);
		}
		//hair
		if(selectedValues[HAIR] != -1)
		{
			BufferedImage hairImg = features.get(HAIR).getValue(selectedValues[HAIR]).getDrawImage();
			if(hairImg != null)
				g.drawImage(hairImg, (int)(0-getWidth()*0.15), (int)(getHeight()*0.01), (int)(getWidth()*1.3), (int)(getHeight()* 0.5), this);
		}	
		
		//glasses
		if(selectedValues[GLASSES] != -1)
		{
			BufferedImage glassesImg = features.get(GLASSES).getValue(selectedValues[GLASSES]).getDrawImage();
			if(glassesImg != null)
				g.drawImage(glassesImg,(int)(getWidth()*0.15), (int)(getHeight()*0.2 ), (int)(getWidth()*0.7), (int)(getHeight()* 0.15), this);
		}
		
		
				
		g.setColor(prevColor);
	}
	public void stop()
	{
		running = false;
	}
	public void updateValues(int[] selectedButtons) 
	{
		selectedValues = selectedButtons;
		repaint();
	}
}
