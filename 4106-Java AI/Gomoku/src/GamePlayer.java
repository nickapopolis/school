import java.awt.Color;

import javax.swing.*;

@SuppressWarnings("serial")
class GamePlayer 
{
	protected int xPos;
	protected int yPos;
	protected int lastXPos;
	protected int lastYPos;
	protected Color colour;
	
	
	public GamePlayer(int x, int y, Color c)
	{
		super();
		xPos = x;
		yPos = y;
		colour = c;
	}
	public void makeMove()
	{

	}
	public int getX()
	{
		return xPos;
	}
	public int getY()
	{
		return yPos;
	}
	public int getLastX()
	{
		return lastXPos;
	}
	public int getLastY()
	{
		return lastYPos;
	}
	public void setPos(int x, int y)
	{
		lastXPos = xPos;
		lastYPos = yPos;
		xPos = x;
		yPos = y;
	}
	public void setColour(Color c)
	{
		colour = c;
	}
	public Boolean intersects(GamePlayer gp)
	{
		return (xPos == gp.xPos && yPos == gp.yPos);
	}
	public Color getColour()
	{
		return colour;
	}
	public String toString()
	{
		return "Position x: " + xPos + " y: " + yPos;
	}

}
