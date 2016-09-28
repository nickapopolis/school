public class GameState
{
	private Point spiderPos;
	private Point antPos;
	private int antDirection;

	public GameState(Point sPos, Point aPos, int aDir)
	{
		spiderPos = sPos;
		antPos = aPos;
		antDirection = aDir;
	}
	public Point getSpiderPos()
	{
		return spiderPos;
	}
	public Point getAntPos()
	{
		return antPos;
	}
	public int getAntDir()
	{
		return antDirection;
	}
	public boolean equals(GameState st)
	{
		return spiderPos.equals(st.spiderPos) && antPos.equals(st.antPos) && antDirection==st.antDirection;
	}
}
