
public class Eater implements Runnable{
	private String ingredient;
	private Control theTable;
	private long maximumEatTime;
	private boolean running = true;
	
	public Eater(String aIngredient, Control aTable, long maxEatTime)
	{
		ingredient = aIngredient;
		theTable = aTable;
		maximumEatTime = maxEatTime;
		new Thread(this, aIngredient).start(); //start thread
	}
	
	//stops thread safely
	public void stopEating(){
		System.out.println(this.ingredient + "Eater Thread Terminated.");	
		running = false;
	}

	@Override
	public synchronized void run(){
		try {
			System.out.println(Thread.currentThread().getName() + "Eater Thread Started.");
			while(running){
				theTable.eat(random());	//try and make food and eat it		
			}
		} catch (InterruptedException e) {
			
		}
	}
	
	//generates a random time in milliseconds between 0 and the maximum eat time
	public long random()
	{
		return (long) (Math.random() * maximumEatTime);
	}
	
	//public method for checking if food can be made and eaten by this thread
	public boolean canEat()
	{
		if ((!theTable.IngredientsOnTable().contains(ingredient)) && 
			 (theTable.IngredientsOnTable().size() > 0))
			return true;
		else
			return false;			
	}
}
