import java.util.ArrayList;

public class GameState
{
	private ArrayList<Point> min;
	private ArrayList<Point> max;
	
	public GameState(ArrayList<Point> p1, ArrayList<Point> p2)
	{
		min = p1;
		max = p2;
	}
	public ArrayList<Point> getMinPieces()
	{
		return min;
	}
	public ArrayList<Point> getMaxPieces()
	{
		return max;
	}
	public boolean equals(GameState st)
	{
		if(min.size() != st.min.size() || max.size() != st.max.size())
		{
			return false;
		}
		for(int i=0;i<min.size();i++)
		{
			boolean ret = false;
			for(int j=0;j<st.min.size();j++)
			{
				if(min.get(i).equals(st.min.get(j)))
				{
					ret = true;
				}
			}
			if (!ret)
			{
				return false;
			}
		}
		for(int i=0;i<max.size();i++)
		{
			boolean ret = false;
			for(int j=0;j<st.max.size();j++)
			{
				if(max.get(i).equals(st.max.get(j)))
				{
					ret = true;
				}
			}
			if (!ret)
			{
				return false;
			}
		}
		return true;
	}
	public String toString()
	{
		String ret = "Player1\n";
		for(int i=0;i<min.size();i++)
		{
			ret+= min.get(i).toString() + "\n";
		}
		ret += "Player2\n";
		for(int i=0;i<max.size();i++)
		{
			ret+= max.get(i).toString() + "\n";
		}
		return ret;
	}
}
