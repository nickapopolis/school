import javax.swing.*;

@SuppressWarnings("serial")
class GamePlayer extends ImageIcon
{
	protected int xPos;
	protected int yPos;
	protected int lastXPos;
	protected int lastYPos;
	public GamePlayer(int x, int y, String fileName)
	{
		super(fileName);
		xPos = x;
		yPos = y;
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
	public Point getPos()
	{
		return new Point(xPos,yPos);
	}
	public Point getLastPos()
	{
		return new Point(lastXPos,lastYPos);
	}
	public void setPos(int x, int y)
	{
		lastXPos = xPos;
		lastYPos = yPos;
		xPos = x;
		yPos = y;
	}
	public void setPos(Point p)
	{
		setPos(p.getX(),p.getY());
	}
	public Boolean intersects(GamePlayer gp)
	{
		return (xPos == gp.xPos && yPos == gp.yPos);
	}
	public String toString()
	{
		return "Position x: " + xPos + " y: " + yPos;
	}

}
