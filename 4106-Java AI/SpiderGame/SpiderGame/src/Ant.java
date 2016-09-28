import java.util.*;
@SuppressWarnings("serial")

public class Ant extends GamePlayer
{

	public int direction;
	public Ant(int xPos, int yPos, String fileName)
	{
		super(xPos, yPos, fileName);
	}
	public String toString()
	{
		return "Ant " + super.toString();
	}
	public void setDirection(int dir)
	{
		if(0<=dir && dir<3)
		{
			direction = dir;
		}
	}
	public int getDirection()
	{
		return direction;
	}
	public Point nextSpace()
	{
		return nextValidMove(xPos,yPos,direction);
	}
	public Point nextValidMove(int x, int y, int dir)
	{
		Point nextSpace = null;
		// determine which space the ant moves to next, (-1,-1) means ant has reached the end of its path
		switch(dir)
		{
			//north
			case 0:
				nextSpace = new Point(x,y+1);
				break;
			//east
			case 1:
				nextSpace = new Point(x-1,y);
				break;
			//south
			case 2:
				nextSpace = new Point(x,y-1);
				break;
			//west
			case 3:
				nextSpace = new Point(x+1,y);
				break;
		}
		return nextSpace;
	}
	public void respawn(int boardWidth, int boardHeight)
	{
			//select random side for ant to spawn on
		Random rand = new Random();
		int x = 0;
		int y = 0;
		int side = rand.nextInt(4);
		System.out.println(side);
		switch(side)
		{
			//north
			case 0:
				x = rand.nextInt(boardWidth -1);
				y = 0;
				break;
			//east
			case 1:
				x = boardWidth-1;
				y = rand.nextInt(boardHeight -1);
				break;
			//south
			case 2:
				x = rand.nextInt( boardWidth -1);
				y = boardHeight-1;
				break;
			//west
			case 3:
				x = 0;
				y = rand.nextInt( boardHeight-1);
				break;
		}
		System.out.println("Spider respawn" + super.toString());
		setPos(x,y);

		direction = side;
	}
}
