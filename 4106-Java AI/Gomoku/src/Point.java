
public class Point
{
	private int x;
	private int y;

	public Point(int xVal, int yVal)
	{
		x = xVal;
		y = yVal;
	}
	public int getX()
	{
		return x;
	}
	public int getY()
	{
		return y;
	}
	public boolean equals(Point p)
	{
		return x==p.x && y==p.y;
	}
	public String toString()
	{
		return "("+x+","+y+")";
	}
}
