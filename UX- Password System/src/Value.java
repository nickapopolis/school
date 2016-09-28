import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;


public class Value 
{
	String valueName;
	ImageIcon buttonIcon;
	BufferedImage drawImage;
	BufferedImage mirroredDrawImage;
	
	public Value(String name, String imgFileName)
	{
		valueName = name;
		try {
		    drawImage = ImageIO.read(new File(imgFileName));
		}catch (IOException e) {
			try {
				drawImage = ImageIO.read(PasswordFrame.class.getResource(imgFileName));
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		
		buttonIcon = loadButtonImage(drawImage);
	}
	public Value(String name, String imgFileName, String mirrorImgFileName)
	{
		valueName = name;
		try {
			drawImage = ImageIO.read(new File(imgFileName));
			mirroredDrawImage  = ImageIO.read(new File(mirrorImgFileName));
		} catch (IOException e) {
			try {

				drawImage = ImageIO.read(PasswordFrame.class.getResource(imgFileName));
				mirroredDrawImage  = ImageIO.read(PasswordFrame.class.getResource(mirrorImgFileName));
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		buttonIcon = loadButtonImage(drawImage);
	}

	private ImageIcon loadButtonImage(Image origImg) 
	{
		//loads image from file and scales it down to the correct size
		Image scaledImg = origImg.getScaledInstance(32, 32,  java.awt.Image.SCALE_SMOOTH);  
		
	    ImageIcon buttonIcon = new ImageIcon(scaledImg);  
	    
	    return buttonIcon;
	}

	public String getValueName() {
		return valueName;
	}

	public ImageIcon getButtonIcon() {
		return buttonIcon;
	}

	public BufferedImage getDrawImage() {
		return drawImage;
	}
	public BufferedImage getMirroredDrawImage()
	{
		return mirroredDrawImage;
	}
	
}
