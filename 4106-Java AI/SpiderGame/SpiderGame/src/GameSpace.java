import java.awt.*;
import javax.swing.*;

@SuppressWarnings("serial")

public class GameSpace extends JButton
{
 	private GamePlayer occupant;
 	private static ImageIcon blankIcon;
 	private int xPos;
 	private int yPos;
 	public int getXPos()
 	{
 		return xPos;
 	}
 	public int getYPos()
 	{
 		return yPos;
 	}
	public GameSpace(int xp, int yp)
	{
		if (blankIcon == null)
		{
			loadImage();
		}
		setIcon(blankIcon);
		//setBackground(Color.white);
		xPos = xp;
		yPos = yp;
	}
	public void setOccupant(GamePlayer player)
	{
		occupant = player;
		if(occupant == null)
		{
			setIcon(blankIcon);
			//setBackground(Color.white);
		}
		else
		{
			setIcon(occupant);
			if(occupant instanceof Ant)
			{
				setBackground(Color.green);
			}
			else if(occupant instanceof Spider)
			{
				setBackground(Color.red);
			}
		}
		this.repaint();
	}
	public static void loadImage()
	{
		blankIcon = new ImageIcon("blank-img.png");
		
	}
	
	
}
