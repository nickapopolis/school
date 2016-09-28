import java.util.ArrayList;


class Node
{

	private int level;
	private GameState state;
	private Node parent;
	private ArrayList<Node> children;
	private int cost;
	private boolean isMin;
	
	public Node(int lvl, GameState st, Node pnt, int cst, boolean min)
	{
		level = lvl;
		state = st;
		cost = cst;
		isMin = min;
		children = new ArrayList<Node>();
		setParent(pnt);
	}
	public Node getParent()
	{
		return parent;
	}
	public int getLevel()
	{
		return level;
	}
	public GameState getState()
	{
		return state;
	}
	public int getCost()
	{
		return cost;
	}
	public void setCost(int c)
	{
		cost = c;
	}
	public boolean equals(Node n)
	{
		return 	state.equals(n.state);
	}
	public void setParent(Node n)
	{
		parent = n;
		if(parent!=null)
			parent.addChild(this);
	}
	public void addChild(Node n)
	{
		children.add(n);
	}
	public ArrayList<Node> getChildren()
	{
		return children;
	}
	public void setChildren(ArrayList<Node> ch)
	{
		children = ch;
	}
	public boolean isMin()
	{
		return isMin;
	}
	public void setMin()
	{
		isMin = true;
	}
	public void setMax()
	{
		isMin = false;
	}
	public String toString()
	{
		return state.toString();
	}

}
