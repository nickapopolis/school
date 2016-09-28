import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class Agent implements Runnable{
	private Control theTable;
	private boolean running = true;
	
	public Agent(Control aTable){
		theTable = aTable;
		new Thread(this, "Agent").start(); //start thread
	}

	//exits thread safely
	public void stopProducing(){
		System.out.println("Agent Thread Terminated.");
		running = false;
	}
	
	@Override
	public synchronized void run(){
		
			try {
				System.out.println("Agent Thread Started.");
				System.out.println("");
				while(running){	//try and place two items on the table
					theTable.placeItemsOnTable(randomFoods());
				}
			} catch (InterruptedException e) {
				
			}		
		
	}

	//generates a list of two random words 
	public List<String> randomFoods()
	{
		List<String> temp = new ArrayList<String>();
		List<String> returnWords = new ArrayList<String>();
	    Random random = new Random();
	    
	    temp.add("potato");
	    temp.add("water");
	    temp.add("butter");
	    
	    returnWords.add(temp.get(random.nextInt(3)));
	    
	    temp.remove(returnWords.get(0));
	    
	    returnWords.add(temp.get(random.nextInt(2)));
	    
	    return returnWords;
	}
	
	//public method to check if food can be placed on table
	public boolean canPlaceFoodOnTable()
	{
		if (theTable.IngredientsOnTable().size() == 0)
			return true;
		else
			return false;			
	}
	
	
}
