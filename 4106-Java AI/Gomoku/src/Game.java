import java.util.*;
import java.awt.*;

import javax.swing.*;
import java.awt.event.*; //needed for event listeners

@SuppressWarnings("serial")
public class Game extends JFrame
{
	GamePlayer players[];
	GameBoard board;
	GridBagLayout layout;
	GridBagConstraints constraints;
	JButton startButton;
	JTextArea gameStatus;
	JSlider gameSpeed;
	Game frame;
    public Game(String name)
    {
    	super(name);
    	setSize(850,800);
    	setResizable(false);
    	setDefaultCloseOperation(EXIT_ON_CLOSE);
    	initialize();
    }
	public void initialize()
	{
		players    = new GamePlayer[2];
		players[0] = new GamePlayer(0,0, Color.red);
		players[1] = new GamePlayer(0,0, Color.blue);
		
        //add all the individual spaces to the board
		layout = new GridBagLayout();
        constraints = new GridBagConstraints();
        setLayout(layout);
        
        //set constraints of spaces
	    constraints.gridwidth = 1;
	    constraints.gridheight = 5;
	    constraints.weightx = 1;
	    constraints.weighty = 1;
	    constraints.ipadx = 0;
	    constraints.ipady = 0;
	    constraints.gridx = 0;
   	 	constraints.gridy = 0;
	    constraints.fill = GridBagConstraints.BOTH;
	    constraints.anchor = GridBagConstraints.NORTHWEST;
	    

		board = new GameBoard(10,10);
		board.setSize(new Dimension(800,800));
   	 	layout.setConstraints(board, constraints);
   	 	add(board);	
   	 	
   	 	startButton = new JButton("Start");
	    constraints.insets = new Insets(0, 0, 0, 10);
	    constraints.gridwidth = 1;
	    constraints.gridheight = 1;
	    constraints.ipadx = 50;
	    constraints.weightx = 0;
	    constraints.gridx = 1;
	    constraints.gridy = 0;
	    constraints.fill = GridBagConstraints.NONE;
	    constraints.anchor = GridBagConstraints.SOUTH;
	    layout.setConstraints(startButton, constraints);
	    add(startButton);
	    
	    frame = this;
	    startButton.addActionListener(new ActionListener() {
 
            public void actionPerformed(ActionEvent e)
            {
            	Thread t = new Thread(new PlayThread(frame));
            	t.start();
            }
        });
	    
	   
	    
	    gameSpeed = new JSlider(0,1000);
	    constraints.insets = new Insets(0, 0, 0, 0);
	    constraints.ipadx = 50;
	    constraints.weightx = 0;
	    constraints.gridx = 1;
	    constraints.gridy = 1;
	    constraints.fill = GridBagConstraints.BOTH;
	    constraints.anchor = GridBagConstraints.CENTER;
	    layout.setConstraints(gameSpeed, constraints);
	    add(gameSpeed );
	    
	    
	    gameStatus = new JTextArea();
	    constraints.insets = new Insets(10, 0, 0, 0);
	    constraints.ipadx = 50;
	    constraints.weightx = 0;
	    constraints.gridx = 1;
	    constraints.gridy = 2;
	    constraints.fill = GridBagConstraints.NONE;
	    constraints.anchor = GridBagConstraints.NORTH;
	    layout.setConstraints(gameStatus , constraints);
	    add(gameStatus );
	    gameStatus.setText("Press Start");
   	 	
   	 	
	}
	/**
	 * Performs min max search on the given starting state. Returns the tree calculated by the search.
	 * @param startingState
	 * @return
	 */
	public Node minMaxSearch(GameState startingState)
	{
		
	    
		System.out.println("Starting search");
		
		
		Node start = new Node(0, startingState, null, 0, true);
		
		alphaBeta(start, 3, -9999999, 99999999);
		
		System.out.println("Search Finished");
		return start;
	}
	/**
	 * Performs minmax search with alpha beta pruning.
	 * @param n
	 * @param depth
	 * @param alpha
	 * @param beta
	 * @return
	 */
	public int alphaBeta(Node n, int depth, int alpha, int beta)
	{
		if  (depth == 0 || isTerminal(n))
		{
			int cost = heuristic(n);
			n.setCost(cost);
			return cost;
		}
		int a = alpha;
		int b = beta;
		
		n.setChildren(expand(n));
		if  (n.isMin())
		{
			
			for (int i=0;i<n.getChildren().size();i++)
			{
				b = Math.min(b, alphaBeta(n.getChildren().get(i), depth-1, a, b )) ;   
				if(b <= a)
					break; 
			}
			n.setCost(b);
			return b; 
		}
		else
		{
			for (int i=0;i<n.getChildren().size();i++)
			{
				a = Math.max(a, alphaBeta(n.getChildren().get(i), depth-1, a, b )) ;   
				if ( b <= a)
					break;  
			}
			n.setCost(a);
			return a;
		}

	}
	/**
	 * Expands the node to get children
	 * @param n
	 * @return
	 */
	public ArrayList<Node> expand(Node n)
	{
		ArrayList<Point> unoccupied = getUnoccupiedSpaces(n.getState().getMinPieces(), n.getState().getMaxPieces());
		ArrayList<Node> children = new ArrayList<Node>();
		
		if(n.isMin())
		{
			ArrayList<Point> p1 = n.getState().getMinPieces();
			
			for(int i=0; i<unoccupied.size();i++)
			{
				ArrayList<Point> p2 = (ArrayList<Point>)n.getState().getMaxPieces().clone();
				p2.add(unoccupied.get(i));
				
				GameState state = new GameState(p1,p2);
				Node child = new Node(n.getLevel()+1,state ,n,0, false);
				children.add(child);
			}
		}
		else
		{
			ArrayList<Point> p2 = n.getState().getMaxPieces();
			
			for(int i=0; i<unoccupied.size();i++)
			{
				ArrayList<Point> p1 = (ArrayList<Point>)n.getState().getMinPieces().clone();
				p1.add(unoccupied.get(i));
				
				GameState state = new GameState(p1,p2);
				Node child = new Node(n.getLevel()+1,state ,n,0, true);
				children.add(child);
			}
		}
		return children;
	}
	
	public boolean isTerminal(Node n)
	{
		return isTerminal(n.getState());
	}
	public boolean isTerminal(GameState st)
	{
		if(st.getMinPieces().size()+st.getMaxPieces().size() ==board.getWidth()*board.getHeight())
		{
			return true;
		}
		ArrayList<Line> lines = getLines(st);
		for(int i=0;i<lines.size();i++)
		{
			if(lines.get(i).getLength()>=5)
			{
				return true;
			}
		}
		return false;
	}
	public int heuristic(Node n)
	{
		return heuristic(n.getState());
	}
	public int heuristic(GameState st)
	{
		ArrayList<Line> lines = getLines(st);
		int cost = 0;
		
		for(int i=0;i<lines.size();i++)
		{
			int numValid = 0;
			ArrayList<Point> points= lines.get(i).getEndPoints();
			if(board.validMove(points.get(0)))
				numValid++;
			if(board.validMove(points.get(1)))
				numValid++;
			
			if(lines.get(i).isMin())
			{
				cost -= numValid * lines.get(i).getLength();
			}
			else
			{
				cost += numValid * lines.get(i).getLength();
			}
		}
		return cost;
	}
	public ArrayList<Line> getLines(Node n)
	{
		return getLines(n.getState());
	}
	public ArrayList<Line> getLines(GameState st)
	{
		ArrayList<Point> minspaces = st.getMinPieces();
		ArrayList<Point> maxspaces = st.getMaxPieces();
		
		int gameboard[][] = new int[board.getBoardWidth()][board.getBoardHeight()];
		int x,y;
		for(int i=0;i<board.getBoardWidth();i++)
		{
			for(int j=0;j<board.getBoardHeight();j++)
			{
				gameboard[i][j] = 0;
			}
		}
		//search through p1 spaces occupied and check off spaces in board as occupied
		for(int i=0;i<minspaces.size();i++)
		{
			x = minspaces.get(i).getX();
			y = minspaces.get(i).getY();
			gameboard[x][y] = 1;
		}
		//search through p2 spaces occupied and check off spaces in board as occupied
		for(int i=0;i<maxspaces.size();i++)
		{
			x = maxspaces.get(i).getX();
			y = maxspaces.get(i).getY();
			gameboard[x][y] = 2;
		}
		ArrayList<Line> lines = new ArrayList<Line>();
		
		//check for horizontal line
		for(int i=0;i<board.getBoardHeight(); i++)
		{
			int spaceType = 0;
			Line line = null;
			for(int j=0;j<board.getBoardWidth(); j++)
			{
				switch(spaceType)
				{
					case 0:
						switch(gameboard[j][i])
						{
							case 1:
								line = new Line(0, new Point(j,i),true);
								spaceType = 1;
								break;
							case 2:
								line = new Line(0, new Point(j,i), false);
								spaceType = 2;
								break;
						}
						break;
					case 1:
						switch(gameboard[j][i])
						{
							case 0:
								lines.add(line);
								spaceType = 0;
								line = null;
								break;
							case 1:
								line.extend();
								break;
							case 2:
								lines.add(line);
								spaceType = 2;
								line = new Line(0,new Point(j,i), false);
								break;
						}
						break;
					case 2:
						switch(gameboard[j][i])
						{
							case 0:
								lines.add(line);
								spaceType = 0;
								line = null;
								break;
							case 1:
								lines.add(line);
								spaceType = 1;
								line = new Line(0, new Point(j,i), true);
								break;
							case 2:
								line.extend();
								break;
						}
						break;
				}
			}
			if(line !=null)
			{
				lines.add(line);
			}
		}
		//check for vertical line
		for(int i=0;i<board.getBoardWidth(); i++)
		{
			int spaceType = 0;
			Line line = null;
			for(int j=0;j<board.getBoardHeight(); j++)
			{
				switch(spaceType)
				{
					case 0:
						switch(gameboard[i][j])
						{
							case 1:
								spaceType = 1;
								line = new Line(1,new Point(i,j), true);
								break;
							case 2:
								spaceType = 2;
								line = new Line(1,new Point(i,j), false);
								break;
						}
						break;
					case 1:
						switch(gameboard[i][j])
						{
							case 0:
								lines.add(line);
								spaceType = 0;
								line = null;
								break;
							case 1:
								line.extend();
								break;
							case 2:
								lines.add(line);
								spaceType = 2;
								line = new Line(1,new Point(i,j), false);
								break;
						}
						break;
					case 2:
						switch(gameboard[i][j])
						{
							case 0:
								lines.add(line);
								spaceType = 0;
								line = null;
								break;
							case 1:
								lines.add(line);
								spaceType = 1;
								line = new Line(1,new Point(i,j), true);
								break;
							case 2:
								line.extend();
								break;
						}
						break;
				}
			}
			if (line != null)
			{
				lines.add(line);
			}
		}
		
		//check for forward diagonal line
		for(int i=0 ;i<board.getBoardWidth();i++)
		{
			int spaceType = 0;
			Line line = null;
			for(int j=0;j<board.getBoardHeight()&& i-j>=0;j++)
			{
				switch(spaceType)
				{
					case 0:
						switch(gameboard[j][i-j])
						{
							case 1:
								spaceType = 1;
								line = new Line(3, new Point(j, i-j), true);
								break;
							case 2:
								spaceType = 2;
								line = new Line(3, new Point(j, i-j), false);
								break;
						}
						break;
					case 1:
						switch(gameboard[j][i-j])
						{
							case 0:
								lines.add(line);
								spaceType = 0;
								line = null;
								break;
							case 1:
								line.extend();
								break;
							case 2:
								lines.add(line);
								spaceType = 2;
								line = new Line(3, new Point(j, i-j), false);
								break;
						}
						break;
					case 2:
						switch(gameboard[j][i-j])
						{
							case 0:
								lines.add(line);
								spaceType = 0;
								line = null;
								break;
							case 1:
								lines.add(line);
								spaceType = 1;
								line = new Line(3,new Point(j, i-j), true);
								break;
							case 2:
								line.extend();
								break;
						}
						break;
				}
			}
			if(line != null)
			{
				lines.add(line);
			}
		}
		for(int i=1 ;i<board.getBoardWidth();i++)
		{
			int spaceType = 0;
			Line line = null;
			
			for(int j=board.getBoardHeight()-1;j>=0 && i+board.getBoardHeight()-1-j<board.getBoardWidth();j--)
			{
				switch(spaceType)
				{
					case 0:
						switch(gameboard[i+board.getBoardHeight()-1-j][j])
						{
							case 1:
								spaceType = 1;
								line = new Line(3, new Point(i+board.getBoardHeight()-1-j, j),true);
								break;
							case 2:
								spaceType = 2;
								line = new Line(3, new Point(i+board.getBoardHeight()-1-j, j), false);
								break;
						}
						break;
					case 1:
						switch(gameboard[i+board.getBoardHeight()-1-j][j])
						{
							case 0:
								lines.add(line);
								spaceType = 0;
								line = null;;
								break;
							case 1:
								line.extend();
								break;
							case 2:
								lines.add(line);
								spaceType = 2;
								line = new Line(3,new Point(i+board.getBoardHeight()-1-j,j), false);
								break;
						}
						break;
					case 2:
						switch(gameboard[i+board.getBoardHeight()-1-j][j])
						{
							case 0:
								lines.add(line);
								spaceType = 0;
								line = null;
								break;
							case 1:
								lines.add(line);
								spaceType = 1;
								line = new Line(3,new Point(i+board.getBoardHeight()-1-j,j), true);
								break;
							case 2:
								line.extend();
								break;
						}
						break;
				}
			}
			if(line != null)
			{
				lines.add(line);
			}
		}
		//check for backward diagonal line
		for(int i=board.getBoardHeight()-1 ;i>=0;i--)
		{
			int spaceType = 0;
			Line line = null;
			for(int j=0;j<board.getBoardWidth()&& i+j<board.getBoardHeight();j++)
			{
				switch(spaceType)
				{
					case 0:
						switch(gameboard[j][i+j])
						{
							case 1:
								spaceType = 1;
								line = new Line(2,new Point(j,i+j), true);
								break;
							case 2:
								spaceType = 2;
								line = new Line(2,new Point(j,i+j), false);
								break;
						}
						break;
					case 1:
						switch(gameboard[j][i+j])
						{
							case 0:
								lines.add(line);
								spaceType = 0;
								line = null;
								break;
							case 1:
								line.extend();
								break;
							case 2:
								lines.add(line);
								spaceType = 2;
								line = new Line(2,new Point(j,i+j), false);
								break;
						}
						break;
					case 2:
						switch(gameboard[j][i+j])
						{
							case 0:
								lines.add(line);
								spaceType = 0;
								line = null;
								break;
							case 1:
								lines.add(line);
								spaceType = 1;
								line = new Line(2,new Point(j,i+j), true);
								break;
							case 2:
								line.extend();
								break;
						}
						break;
				}
			}
			if (line != null)
			{
				lines.add(line);
			}
		}
		for(int i=1 ;i<board.getBoardWidth();i++)
		{
			int spaceType = 0;
			Line line = null;
			
			for(int j=0;j<board.getHeight()&&i+j<board.getBoardWidth();j++)
			{
				switch(spaceType)
				{
					case 0:
						switch(gameboard[i+j][j])
						{
							case 1:
								spaceType = 1;
								line = new Line(2,new Point(i+j,j), true);
								break;
							case 2:
								spaceType = 2;
								line = new Line(2,new Point(i+j,j), true);
								break;
						}
						break;
					case 1:
						switch(gameboard[i+j][j])
						{
							case 0:
								lines.add(line);
								spaceType = 0;
								line = null;
								break;
							case 1:
								line.extend();
								break;
							case 2:
								lines.add(line);
								spaceType = 2;
								line = new Line(2,new Point(i+j,j), false);
								break;
						}
						break;
					case 2:
						switch(gameboard[i+j][j])
						{
							case 0:
								lines.add(line);
								spaceType = 0;
								line = null;
								break;
							case 1:
								lines.add(line);
								spaceType = 1;
								line = new Line(2,new Point(i+j,j), true);
								break;
							case 2:
								line.extend();
								break;
						}
						break;
				}
			}
			if(line !=null)
			{
				lines.add(line);
			}
		}
		return lines;
	}
	public void begin()
	{
		
		
	}
	public Point getRandomPoint(GameState st)
	{
		ArrayList<Point> spacesLeft = getUnoccupiedSpaces(st.getMinPieces(), st.getMaxPieces());
		Random r = new Random();
		return spacesLeft.get(r.nextInt(spacesLeft.size()));
	}
	/**
	 * When given lists of both players spaces they occupy, returns the unoccupied spaces on the board.
	 * @param p1spaces
	 * @param p2spaces
	 * @return
	 */
	public ArrayList<Point> getUnoccupiedSpaces(ArrayList<Point> p1spaces, ArrayList<Point> p2spaces)
	{
		ArrayList<Point> unoccupied = new ArrayList<Point>();
		boolean[][] gameboard = new boolean[board.getBoardWidth()][board.getBoardHeight()];
		
		int x,y;
		//initialize board spaces to false for unoccupied
		for(int i=0;i<board.getBoardWidth();i++)
		{ 
			for(int j=0;j<board.getBoardHeight();j++)
			{
				gameboard[i][j] = false;
			}
		}
		//search through p1 spaces occupied and check off spaces in board as occupied
		for(int i=0;i<p1spaces.size();i++)
		{
			x = p1spaces.get(i).getX();
			y = p1spaces.get(i).getY();
			gameboard[x][y] = true;
		}
		//search through p2 spaces occupied and check off spaces in board as occupied
		for(int i=0;i<p2spaces.size();i++)
		{
			x = p2spaces.get(i).getX();
			y = p2spaces.get(i).getY();
			gameboard[x][y] = true;
		}
		//add unoccupied spaces to a list for return
		for(int i=0;i<board.getBoardWidth();i++)
		{
			for(int j=0;j<board.getBoardHeight();j++)
			{
				if(gameboard[i][j] == false)
				{
					unoccupied.add(new Point(i,j));
				}
			}
		}
		return unoccupied;
	}
	public Node advance(Node tree, GameState currentState)
	{
		Node ret = tree;
		if(ret == null)
		{
			ret = minMaxSearch(currentState);
			return advance(ret, currentState);
		}
		else if(ret.isMin())
		{
			ret = getChildWithBestCost(ret.getChildren(), currentState);
			if (ret == null)
			{
				return advance(ret, currentState);
			}
			else return ret;
		}
		else
		{
			ret = getChildWithState(ret.getChildren(), currentState);
			return advance(ret, currentState);
		}
	}
	public Node getChildWithState(ArrayList<Node> children, GameState currentState)
	{
		for(int i=0;i<children.size();i++)
		{
			if(currentState.equals(children.get(i).getState()))
			{
				return children.get(i); 
			}
		}
		return null;
	}
	public Node getChildWithBestCost(ArrayList<Node> children, GameState currentState)
	{
		if(children.size() == 0)
		{
			return null;
		}
		Node best = children.get(0);
		for(int i=1; i<children.size();i++)
		{
			if(children.get(i).getCost()>best.getCost())
			{
				best = children.get(i);
			}
		}
		return best;
	}
	public synchronized void playGame()
	{
		board.resetBoard();
		GameState state = null;
		Node n = null;
		while(!isTerminal(state = board.getCurrentState(players[0], players[1])))
		{
			//min move
			Point p = getRandomPoint(state);
			board.setOccupant(p.getX(), p.getY(), players[0]);
			delay(gameSpeed.getValue());
			//max move
			state = board.getCurrentState(players[0], players[1]);
			n = advance(n, state);
			p = n.getState().getMaxPieces().get(n.getState().getMaxPieces().size()-1);
			board.setOccupant(p.getX(), p.getY(), players[1]);
			delay(gameSpeed.getValue());
		}
		//minMaxSearch(state);
	}
	public void delay(int msec)
	{
		long time = System.currentTimeMillis() + msec;
		while(System.currentTimeMillis() < time)
		{
		}
	}
	public static void main(String[] args)
	{
		Game g = new Game("Gomoku");
		g.setVisible(true);
		
	}

}
