import java.util.ArrayList;


public class Line 
{
	private static final int HORIZONTAL = 0, VERTICAL = 1, BACKWARD =2, FORWARD = 3 ;
	
	private int type;
	private int length;
	private Point start;
	private boolean isMin;
	
	public Line(int t, Point p, boolean min)
	{
		type = t;
		start = p;
		isMin = min;
		length = 1;
	}
	public void extend()
	{
		length+=1;
	}
	public ArrayList<Point> getEndPoints()
	{
		ArrayList<Point> points = new ArrayList<Point>();
		switch(type)
		{
			case(HORIZONTAL):
				points.add(new Point(start.getX()-1, start.getY()));
				points.add(new Point(start.getX()+ length, start.getY()));
				break;
			case(VERTICAL):
				points.add(new Point(start.getX(),start.getY()-1));
				points.add(new Point(start.getX(),start.getY()+length));
				break;
			case(FORWARD):
				points.add(new Point(start.getX()-1, start.getY()+1));
				points.add(new Point(start.getX()+length, start.getY()-length));
				break;
			case(BACKWARD):
				points.add(new Point(start.getX()-1, start.getY()-1));
				points.add(new Point(start.getX()+length, start.getY()+length));
				break;
		}
		return points;
	}
	public String toString()
	{
		ArrayList<Point> endpoints = getEndPoints();
		return endpoints.get(0).toString() + ","+endpoints.get(1).toString() +", "+ getType()+" length= "+ length ;
		
	}
	public int getLength()
	{
		return length;
	}
	public boolean isMin()
	{
		return isMin;
	}
	public String getType()
	{
		switch(type)
		{
			case HORIZONTAL:
				return "horizontal";
			case VERTICAL:
				return "vertical";
			case FORWARD:
				return "forward";
			case BACKWARD:
				return "backward";
				
		}
		return null;
	}
}
