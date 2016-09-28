import java.util.*;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*; //needed for event listeners

@SuppressWarnings("serial")
public class Game extends JFrame
{
	private GameBoard board;

	private Spider spider;
	private Ant ant;
	
	JRadioButton[] radioButtons;
	ButtonGroup radioSettings;
	JTextArea textArea;
	// 0= manual, 1= breadthFirst, 2= depthFirst
	private int searchAlgorithm = 0;
    public Game(String name)
    {
    	super(name);
    	setSize(900,800);
    	setResizable( true );
    	setDefaultCloseOperation(EXIT_ON_CLOSE);
    }



	public void initialize()
	{
		

		Random rand = new Random();
		board = new GameBoard(20,20);

		ant = new Ant(0, 0, "ant-img.png");
		ant.respawn(board.getWidth(), board.getHeight());
		spider = new Spider(rand.nextInt(board.getWidth()), rand.nextInt(board.getHeight()), "spider-img.png");


		GridBagLayout layout = new GridBagLayout();
        GridBagConstraints constraints = new GridBagConstraints();
        setLayout(layout);

        //add all the individual spaces to the board

        //set constraints of spaces
	    constraints.gridwidth = 1;
	    constraints.gridheight = 1;
	    constraints.weightx = 1;
	    constraints.weighty = 1;
	    constraints.fill = GridBagConstraints.BOTH;
	    constraints.insets = new Insets(0, 0, 0, 0);

	    //add spaces to board
        for(int i=0;i < board.getWidth();i++)
        {
        	GameSpace space;
        	for(int j=0;j < board.getHeight();j++)
        	{
        		constraints.gridx = i;
	    		constraints.gridy = j;

	        	space = new GameSpace(i, j);

	        	
	        	space.addActionListener
	        	(
	           		new ActionListener()
	           		{
		                public void actionPerformed(ActionEvent theEvent)
		                {
		                	GameSpace s = (GameSpace)theEvent.getSource();
		    	        	update(s.getXPos(),s.getYPos()); //call update to re-synchronized the GUI with the model
		                }
	           		}
        		);

        		board.setSpace(i,j,space);
	        	layout.setConstraints(board.getSpace(i,j), constraints);
	        	add(board.getSpace(i,j));

        	}
        }

		constraints.fill = GridBagConstraints.NONE;
        constraints.gridwidth = 3;
        constraints.ipadx = 100;
	    constraints.gridy = 0;
		constraints.gridx = board.getWidth()+1;
		JLabel settings = new JLabel("Search Settings");
	    layout.setConstraints(settings, constraints);
	    add(settings);

		radioButtons = new JRadioButton[6];
		radioSettings = new ButtonGroup();
		constraints.gridwidth = 2;
 		constraints.gridy = 1;
        radioButtons[0] = new JRadioButton("Manual");
	    layout.setConstraints(radioButtons[0], constraints);
        radioSettings.add(radioButtons[0]);
        add(radioButtons[0]);


		constraints.gridy = 2;
        radioButtons[1] = new JRadioButton("Breadth-first");
        layout.setConstraints(radioButtons[1], constraints);
        radioSettings.add(radioButtons[1]);
		add(radioButtons[1]);

		constraints.gridy = 3;
        radioButtons[2] = new JRadioButton("Depth-first");
        layout.setConstraints(radioButtons[2], constraints);
        radioSettings.add(radioButtons[2]);
		add(radioButtons[2]);
		
		constraints.gridy = 4;
        radioButtons[3] = new JRadioButton("A* Heuristic 1");
        layout.setConstraints(radioButtons[3], constraints);
        radioSettings.add(radioButtons[3]);
		add(radioButtons[3]);
		
		constraints.gridy = 5;
        radioButtons[4] = new JRadioButton("A* Heuristic 2");
        layout.setConstraints(radioButtons[4], constraints);
        radioSettings.add(radioButtons[4]);
		add(radioButtons[4]);
		
		constraints.gridy = 6;
        radioButtons[5] = new JRadioButton("A* Heuristic 3");
        layout.setConstraints(radioButtons[5], constraints);
        radioSettings.add(radioButtons[5]);
		add(radioButtons[5]);
		
		constraints.gridy = 7;
        JButton startButton = new JButton("Start");
        layout.setConstraints(startButton, constraints);
        startButton.addActionListener
    	(
       		new ActionListener()
       		{
                public void actionPerformed(ActionEvent theEvent)
                {
                	board.resetBoard();
                	drawGame();
                	beginGame();
                }
       		}
		);
		add(startButton);
		
		constraints.gridy = 8;
		textArea = new JTextArea("");
		layout.setConstraints(textArea, constraints);
		add(textArea);
		
		radioButtons[searchAlgorithm].setSelected(true);

        board.getSpace(spider.getX(), spider.getY()).setOccupant(spider);
		board.getSpace(ant.getX(), ant.getY()).setOccupant(ant);
		
	}
	public static JRadioButton getSelection(ButtonGroup group) {
	    for (Enumeration e=group.getElements(); e.hasMoreElements(); ) {
	        JRadioButton b = (JRadioButton)e.nextElement();
	        if (b.getModel() == group.getSelection()) {
	            return b;
	        }
	    }
	    return null;
	}
	private void beginGame()
	{
		JRadioButton button =getSelection(radioSettings);
		System.out.println(spider.toString());
		if(button==radioButtons[0])
    	{
			System.out.println("Manual");
    	}
		else if(button==radioButtons[1])
    	{
			System.out.println("Breadth First Search");
    		GameState state = new GameState(spider.getPos(), ant.getPos(), ant.getDirection());
    		ArrayList<Node> searchResult = breadthFirstSearch(state);
    		playGameWithPath(searchResult);
    	}
		else if(button==radioButtons[2])
    	{
			System.out.println("Depth First Search");
    		GameState state = new GameState(spider.getPos(), ant.getPos(), ant.getDirection());
    		ArrayList<Node> searchResult = depthFirstSearch(state);
    		playGameWithPath(searchResult);
    	}
		else if(button==radioButtons[3])
    	{
			System.out.println("A* - Heuristic 1");
    		GameState state = new GameState(spider.getPos(), ant.getPos(), ant.getDirection());
    		ArrayList<Node> searchResult = aStarSearch(state,1);
    		playGameWithPath(searchResult);
    	}
		else if(button==radioButtons[4])
    	{
			System.out.println("A* - Heuristic 2");
    		GameState state = new GameState(spider.getPos(), ant.getPos(), ant.getDirection());
    		ArrayList<Node> searchResult = aStarSearch(state,2);
    		playGameWithPath(searchResult);
    	}
		else if(button==radioButtons[5])
    	{
			System.out.println("A* - Heuristic 3");
    		GameState state = new GameState(spider.getPos(), ant.getPos(), ant.getDirection());
    		ArrayList<Node> searchResult = aStarSearch(state,3);
    		playGameWithPath(searchResult);
    	}
	}
	private void update(int xPos, int yPos)
	{
		System.out.println(new Point(xPos,yPos).toString());
		if(moveSpider(xPos,yPos))
		{
			moveAnt();
			detectCollision();
			drawGame();
		}
		else
		{
			System.out.println("invalid move");
		}
	}
	private boolean moveSpider(int xPos, int yPos)
	{

		if (board.validMove(xPos, yPos) && spider.validMove(xPos, yPos))
		{
			spider.setPos(xPos, yPos);
			return true;
		}
		return false;
	}
	private void moveAnt()
	{
		Point nextAntSpace = ant.nextSpace();
		if(board.validMove(nextAntSpace))
		{
			ant.setPos(nextAntSpace);
		}
		else
		{
			ant.respawn(board.getWidth(), board.getHeight());
		}
	}
	private void detectCollision()
	{
		if(spider.intersects(ant))
		{
			handleCollision();
			ant.respawn(board.getWidth(), board.getHeight());
		}
	}
	private void handleCollision()
	{
		drawGame();
		System.out.println("Spider caught an Ant");
		//ant.respawn(board.getWidth(), board.getHeight());
	}
	private void drawGame()
	{
		
		board.setOccupant(ant.getLastX(), ant.getLastY(),null);
		board.setOccupant(spider.getLastX(), spider.getLastY(),null);
		board.setOccupant(ant.getX(), ant.getY(),ant);
		board.setOccupant(spider.getX(), spider.getY(),spider);
		
	}
	public ArrayList<Node> breadthFirstSearch(GameState startState)
	{
		//create a node from the starting state of the game and add to the queue
		Node start = new Node(0, startState, null, 0);
		Queue<Node> nodeList = new LinkedList<Node>();
		ArrayList<Node> deadNodes = new ArrayList<Node>();

		nodeList.offer(start);

		while(nodeList.size()>0 )
		{
			Node curr = nodeList.poll();
			deadNodes.add(curr);
			if(isEndState(curr))
			{
				int num = deadNodes!=null?deadNodes.size():0;
				num += nodeList!=null?nodeList.size():0;
				String ret = "Created " +num +" nodes";
				textArea.setText(ret);
				return getFullPath(curr);
			}
			ArrayList<Node> newNodes = expand(curr,0);
		    if(newNodes == null)
		    {
		    	continue;
		    }
			ArrayList<Node>children;
			children = removeDuplicateNodes(nodeList, newNodes);
			children = removeDuplicateNodes(deadNodes,children);
			nodeList.addAll(children);
		}

		textArea.setText("No Solution");
		return null;
	}
	public ArrayList<Node> depthFirstSearch(GameState startState)
	{
		//create a node from the starting state of the game and add to the queue
		Node start = new Node(0, startState, null, 0);
		Stack<Node> nodeList = new Stack<Node>();
		ArrayList<Node> deadNodes = new ArrayList<Node>();

		nodeList.push(start);

		while(nodeList.size()>0 )
		{
			Node curr = nodeList.pop();
			if(isEndState(curr))
			{
				int num = deadNodes!=null?deadNodes.size():0;
				num += nodeList!=null?nodeList.size():0;
				String ret = "Created " +num +" nodes";
				textArea.setText(ret);
				return getFullPath(curr);
			}
			deadNodes.add(curr);
			ArrayList<Node> newNodes = expand(curr,0) ;


			ArrayList<Node>children;
			children = removeDuplicateNodes(nodeList, newNodes);
			children = removeDuplicateNodes(deadNodes,children);
			nodeList.addAll(children);
		}

		textArea.setText("No Solution");
		return null;

	}
	public ArrayList<Node> aStarSearch(GameState startState, int heuristicNum)
	{
		//create a node from the starting state of the game and add to the queue
		Node start = new Node(0, startState, null, 0);
		ArrayList<Node> deadNodes = new ArrayList<Node>();
		PriorityQueue<Node> nodeList = new PriorityQueue<Node>(10,
                new Comparator<Node>() {
            @Override
            public int compare(Node n1, Node n2){
                if (n1.getCost() > n2.getCost()){
                    return +1;
                }
                else if (n1.getCost() < n2.getCost()){
                    return -1;
                }
                else {  // equal
                    return 0;
                }
            }
        });

		nodeList.add(start);

		while(nodeList.size()>0 )
		{
			Node curr = nodeList.poll();
			if(isEndState(curr))
			{
				int num = deadNodes!=null?deadNodes.size():0;
				num += nodeList!=null?nodeList.size():0;
				String ret = "Created " +num +" nodes";
				textArea.setText(ret);
				return getFullPath(curr);
			}
			deadNodes.add(curr);
			ArrayList<Node> newNodes = expand(curr,heuristicNum) ;
			/*
			for(int i=0;i<newNodes.size();i++)
			{
				Node n =getNode(deadNodes,newNodes.get(i));
				if(n == null)
				{
					//node found in open nodes
					n = getNode(nodeList, newNodes.get(i));
					if(n !=null)
					{
						if(n.getCost()> newNodes.get(i).getCost())
						{
							nodeList.remove(n);
							ArrayList<Node> ch = n.getChildren();
							for(int j=0;j<ch.size();j++)
							{
								n.setParent(newNodes.get(i));
								reAssign(nodeList, ch.get(j),heuristicNum);
							}
							nodeList.add(newNodes.get(i));
								
						}
					}
				}
				else
				{
					//node found in closed nodes
					if(n.getCost()> newNodes.get(i).getCost())
					{
						deadNodes.remove(n);
						ArrayList<Node> ch = n.getChildren();
						for(int j=0;j<ch.size();j++)
						{
							n.setParent(newNodes.get(i));
							reAssign(deadNodes, ch.get(j),heuristicNum);
						}
						nodeList.add(newNodes.get(i));
					}			
				}
			}
			*/
			ArrayList<Node> children;
			children = removeDuplicateNodes(nodeList, newNodes);
			children = removeDuplicateNodes(deadNodes,children);
			
			nodeList.addAll(children);
			
		}

		textArea.setText("No Path Found");
		return null;

	}
	public void reAssign(Collection<Node> list, Node n, int heuristicNum)
	{
		if(list instanceof Queue)
		{
			((PriorityQueue<Node>)list).remove(n);
			int cost = n.getParent()==null?0:n.getParent().getLevel()+1;
			n.setCost(cost+getHeuristic(n.getState(), heuristicNum));
			ArrayList<Node> children = n.getChildren();
			((PriorityQueue<Node>)list).add(n);
			
			for(int i=0;i<children.size();i++)
			{
				reAssign(list, children.get(i), heuristicNum);
			}
		}
		else
		{
			int cost = n.getParent()==null?0:n.getParent().getLevel()+1;
			n.setCost(cost+getHeuristic(n.getState(), heuristicNum));
			ArrayList<Node> children = n.getChildren();
			
			for(int i=0;i<children.size();i++)
			{
				reAssign(list, children.get(i), heuristicNum);
			}
		}
	}
	public ArrayList<Node> expand(Node n, int heuristicNum)
	{
		Point antPos = n.getState().getAntPos();
		Point nextAntPos = ant.nextValidMove(antPos.getX(),antPos.getY(), n.getState().getAntDir() );
		//fails if ants next position will take it off the board
		if(!board.validMove(nextAntPos))
		{
			return null;
		}

		//get list of spiders next valid moves and create node with valid moves
		Point spiderPos = n.getState().getSpiderPos();
		Point[] nextSpiderPos = spider.getValidMoves(spiderPos.getX(),spiderPos.getY() );
		ArrayList<Node> children = new ArrayList<Node>();

		for(int i=0;i<nextSpiderPos.length;i++)
		{
			if (board.validMove(nextSpiderPos[i]))
			{
				GameState state = new GameState(nextSpiderPos[i], nextAntPos, n.getState().getAntDir());
				int level = n.getLevel()+1;
				int cost = 0;
				switch(heuristicNum)
				{
					case 1:
						cost = heuristicOne(state) + level;
						break;
					case 2:
						cost = heuristicTwo(state) + level;
						break;
					case 3:
						cost = heuristicThree(state)+ level;
						break;
					default:
						cost = 0;	
						break;
				}
				Node newNode = new Node(level, state, n,cost );
				children.add(newNode);
			}
		}
		return children;
	}

	//returns path from starting position to the selected Node
	public ArrayList<Node> getFullPath(Node n)
	{
		ArrayList<Node> reversePath = new ArrayList<Node>();
		ArrayList<Node> path = new ArrayList<Node>();
		Node curr = n;

		while(curr != null)
		{
			reversePath.add(curr);
			curr = curr.getParent();
		}
		for(int i=reversePath.size()-1;i>=0;i--)
		{
			path.add(reversePath.get(i));
		}
		return path;
	}
	public ArrayList<Node> removeDuplicateNodes(ArrayList<Node> deadNodes, Collection<Node> fringeNodes, ArrayList<Node> newNodes)
	{
		ArrayList<Node> validNodes = new ArrayList<Node>();
		if (newNodes ==null)
		{
			return validNodes;
		}
		
		for(int i=0;i<newNodes.size();i++)
		{
			if(!(contains(deadNodes, newNodes.get(i)) || contains(fringeNodes, newNodes.get(i))))
			{
				validNodes.add(newNodes.get(i));
			}
		}
		return validNodes;
	}
	public ArrayList<Node> removeDuplicateNodes(Collection<Node> existingNodes, ArrayList<Node> newNodes)
	{
		ArrayList<Node> validNodes = new ArrayList<Node>();
		if (newNodes ==null)
		{
			return validNodes;
		}
		
		for(int i=0;i<newNodes.size();i++)
		{
			if(!( contains(existingNodes, newNodes.get(i))))
			{
				validNodes.add(newNodes.get(i));
			}
		}
		return validNodes;
	}
	public boolean contains(Collection<Node> list, Node n)
	{
		if (list == null ||list.size()==0||n ==null)
		{
			return false;
		}
		Iterator<Node> iterator = list.iterator();
		while(iterator.hasNext())
		{
			Node next = iterator.next();
			if(next.equals(n))
			{
				return true;
			}
		}
		return false;
	}
	public Node getNode(Collection<Node> list, Node n)
	{
		if (list == null ||list.size()==0||n ==null)
		{
			return null;
		}
		Iterator<Node> iterator = list.iterator();
		while(iterator.hasNext())
		{
			Node next = iterator.next();
			if(next.equals(n))
			{
				return next;
			}
		}
		return null;
	}
	public boolean isEndState(Node n)
	{
		return(n.getState().getSpiderPos().equals(n.getState().getAntPos()));
	}
	public void playGameWithPath(ArrayList<Node> path)
	{
		if(path !=null)
		{
			System.out.println(path.size());
			for(int i =0 ; i<path.size();i++)
			{
				update(path.get(i).getState().getSpiderPos().getX(),path.get(i).getState().getSpiderPos().getY());
			}
		}
		else
		{
			System.out.println("No path found");
			ant.respawn(board.getWidth(), board.getHeight());
			board.resetBoard();
			drawGame();
		}
	}
	public int heuristicOne( GameState state)
	{
		//distance between two points 
		
		Point antPos = state.getAntPos();
		Point spiderPos = state.getSpiderPos();
		
		double xVal =  Math.pow(antPos.getX()-spiderPos.getX(),2);
		double yVal =  Math.pow(antPos.getY()-spiderPos.getY(),2);
		int distance = (int)Math.sqrt(xVal+yVal);
		
		return distance;
	}
	public int heuristicTwo(GameState state)
	{
		//manhattan distance between two points (total number of x spaces + total number of y spaces)
		Point antPos = state.getAntPos();
		Point spiderPos = state.getSpiderPos();
		
		int xVal =  antPos.getX()-spiderPos.getX();
		int yVal =  antPos.getY()-spiderPos.getY();
		int distance = xVal + yVal;
		
		return distance;
	}
	public int heuristicThree( GameState state)
	{
		return (heuristicOne(state)+heuristicTwo(state))/2;
	}
	public int getHeuristic(GameState state, int heuristic)
	{
		switch(heuristic)
		{
		case 1:
			return heuristicOne( state);
		case 2:
			return heuristicTwo( state);
		case 3:
			return heuristicThree( state);
		default:
			return 0;
		}
	}



}
