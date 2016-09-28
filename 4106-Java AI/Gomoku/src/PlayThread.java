
public class PlayThread implements Runnable
{
	Game game;
	public PlayThread(Game g)
	{
		game = g;
	}
	@Override
	public void run() 
	{
		game.playGame();
	}
}
