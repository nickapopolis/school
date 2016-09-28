import java.awt.*;
import javax.swing.*;

@SuppressWarnings("serial")

public class GameSpace extends JPanel
{
	
 	private GamePlayer occupant;
 	private Color colour;
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
		setPreferredSize(new Dimension(100,100));
		colour = Color.white;
		xPos = xp;
		yPos = yp;
	}
	public void setOccupant(GamePlayer player)
	{
		occupant = player;
		if(occupant == null)
		{
			colour = Color.white;
			//setBackground(Color.white);
		}
		else
		{
			colour = occupant.getColour();
		}
		this.repaint();
	}
	public GamePlayer getOccupant()
	{
		return occupant;
	}
	@Override
	public void paintComponent(Graphics g) 
	{
        super.paintComponent(g);
        Color prevColor = g.getColor();
        //draw square
        g.setColor(colour);
        g.fillRect(0, 0, 100, 100);
        //draw outline
        g.setColor(Color.black);
        g.drawRect(0, 0, 100, 100);
        
        g.setColor(prevColor);
    }
	
	
}
