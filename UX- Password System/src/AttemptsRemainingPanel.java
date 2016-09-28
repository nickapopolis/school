import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Stroke;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;


public class AttemptsRemainingPanel extends JPanel
{
	private BufferedImage xImg;
	private int attempts;
	
	public AttemptsRemainingPanel()
	{
		try
		{
			xImg = ImageIO.read(new File("Images/x.png"));
		}
		catch(Exception e)
		{
			try {
				xImg = ImageIO.read(PasswordFrame.class.getResource("Images/x.png"));
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		attempts = 0;
	}
	public void paint(Graphics g)
	{
		super.paint(g);
		
		int rectWidth, rectHeight;
		rectWidth = rectHeight = (int)(getHeight()* 0.8);
		
		Graphics2D g2d = (Graphics2D)g;
		
		//temp variables to reset color and stroke width
		Color oldColor = g.getColor();
		Stroke oldStroke = g2d.getStroke();
		
		//draw settings
		g2d.setColor(Color.DARK_GRAY);
		g2d.setStroke(new BasicStroke(3));

		int drawPosY  = 0 + getHeight()/2 - rectHeight/2;
		int drawPosX1 = 0 + getWidth()/6 - rectWidth/2 ;
		int drawPosX2 = 0 + getWidth()/2 - rectWidth/2;
		int drawPosX3 = 0 + getWidth() * 5/6 - rectWidth/2;
		
		//draw check boxes
		g2d.drawRect(drawPosX1, drawPosY, rectWidth, rectHeight);
		g2d.drawRect(drawPosX2, drawPosY, rectWidth, rectHeight);
		g2d.drawRect(drawPosX3, drawPosY, rectWidth, rectHeight);
		
		if(attempts >=1)
			g2d.drawImage(xImg,drawPosX1, drawPosY, rectWidth, rectHeight, this);
		if(attempts >=2)
			g2d.drawImage(xImg,drawPosX2, drawPosY, rectWidth, rectHeight, this);
		if(attempts >=3)
			g2d.drawImage(xImg,drawPosX3, drawPosY, rectWidth, rectHeight, this);
		
		//reset draw settings
		g2d.setColor(oldColor);
		g2d.setStroke(oldStroke);
	}
	public void setAttemptsUsed(int num)
	{
		attempts = num;
		repaint();
	}
	
}
