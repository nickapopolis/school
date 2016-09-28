@SuppressWarnings("serial")
public class Spider extends GamePlayer
 {
	public Spider(int xPos, int yPos, String fileName)
	{
		super(xPos, yPos, fileName);
	}
	public String toString()
	{
		return "Spider " + super.toString();
	}
	public Boolean validMove(int x, int y)
	{
		Boolean retVal = false;
		Point[] validSpaces = getValidMoves();

		int size = validSpaces.length;
		int i = 0;
		while(retVal == false && i<size)
		{
			if( validSpaces[i].getX() == x && validSpaces[i].getY() == y)
			{
				retVal = true;
			}
			i++;
		}
		return retVal;
	}
	public Point[] getValidMoves(int x, int y)
	{
		Point[] validSpaces = new Point[8];
		validSpaces[0] = new Point(x+2, y -1);
		validSpaces[1] = new Point(x+2, y +1);
		validSpaces[2] = new Point(x-2, y +1);
		validSpaces[3] = new Point(x-2, y -1);
		validSpaces[4] = new Point(x+1, y +2);
		validSpaces[5] = new Point(x-1, y +2);
		validSpaces[6] = new Point(x+1, y -2);
		validSpaces[7] = new Point(x-1, y -2);

		return validSpaces;
	}
	public Point[] getValidMoves()
	{
		return getValidMoves(xPos,yPos);
	}
}
