import java.util.ArrayList;


class Node
{

	int level;
	GameState state;
	Node parent;
	ArrayList<Node> children;
	int cost;

	public Node(int lvl, GameState st, Node pnt, int cst)
	{
		level = lvl;
		state = st;
		cost = cst;
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

}
