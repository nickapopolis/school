

public class GameBoard
{
	private GameSpace[][] spaces;


	private int boardWidth;
	private int boardHeight;

	public GameBoard(int xDim, int yDim)
	{
		System.out.println("creating a "+xDim+ " by " + yDim+ " board.");
		boardWidth = xDim;
		boardHeight = yDim;
		spaces = new GameSpace[boardWidth][boardHeight];
	}
	public int getHeight()
	{
		return boardHeight;
	}
	public int getWidth()
	{
		return boardWidth;
	}
	public GameSpace getSpace(int x, int y)
	{
		return spaces[x][y];
	}
	public void setSpace(int x, int y, GameSpace space)
	{
		spaces[x][y] = space;
	}
	public void update()
	{

	}
	public Boolean validMove(Point p)
	{
		return validMove(p.getX(), p.getY());
	}
	public Boolean validMove(int x, int y)
	{
		return x>=0 && y>=0 && x<boardWidth && y<boardHeight;
	}
	public void setOccupant(int x, int y, GamePlayer p)
	{
		if (validMove(x,y))
		{
			getSpace(x,y).setOccupant(p);
		}
	}
	public void resetBoard()
	{
		for(int i=0;i<boardWidth;i++)
		{
			for(int j=0;j<boardHeight;j++)
			{
				spaces[i][j].setBackground(null);
			}
		}
	}

}
