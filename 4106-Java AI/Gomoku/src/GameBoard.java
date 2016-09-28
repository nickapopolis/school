import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Rectangle;
import java.util.ArrayList;

import javax.swing.JPanel;



public class GameBoard extends JPanel
{
	private GameSpace[][] spaces;


	private int boardWidth;
	private int boardHeight;
	GridBagLayout layout;
	GridBagConstraints constraints;
	
	public GameBoard(int xDim, int yDim)
	{
		setPreferredSize(new Dimension(800,800));
		layout = new GridBagLayout();
        constraints = new GridBagConstraints();
        setLayout(layout);
		boardWidth = xDim;
		boardHeight = yDim;
		spaces = new GameSpace[boardWidth][boardHeight];
		for(int i=0;i <getBoardWidth();i++)
        {
        	for(int j=0;j <getBoardHeight();j++)
        	{
        		 spaces[i][j] = new GameSpace(i,j);
            	 spaces[i][j].setOccupant(null);
            	 spaces[i][j].setSize(100, 100);
        	     constraints.weightx = 1;
        	     constraints.weighty = 1;
            	 constraints.gridx = i;
            	 constraints.gridy = j;
            	 constraints.fill = GridBagConstraints.BOTH;
            	 layout.setConstraints(spaces[i][j], constraints);
            	 add(spaces[i][j]);	
        	}
        }
		
	}
	public int getBoardHeight()
	{
		return boardHeight;
	}
	public int getBoardWidth()
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
	/**
	 * sets all of the occupants of spaces to null
	 */
	public void resetBoard()
	{
		for(int i=0;i<boardWidth;i++)
		{
			for(int j=0;j<boardHeight;j++)
			{
				spaces[i][j].setOccupant(null);
			}
		}
	}
	/**
	 * Returns the current state of the game
	 * @param min
	 * @param max
	 * @return
	 */
	public GameState getCurrentState(GamePlayer min, GamePlayer max)
	{
		ArrayList<Point> minSpaces = new ArrayList<Point>();
		ArrayList<Point> maxSpaces = new ArrayList<Point>();
		
		for(int i=0;i<boardWidth;i++)
		{
			for(int j=0;j<boardHeight;j++)
			{
				if(spaces[i][j].getOccupant()==min)
				{
					minSpaces.add(new Point(i,j));
				}
				if(spaces[i][j].getOccupant()==max)
				{
					maxSpaces.add(new Point(i,j));
				}
			}
		}
		return new GameState(minSpaces, maxSpaces);
	}
	
	
}
	