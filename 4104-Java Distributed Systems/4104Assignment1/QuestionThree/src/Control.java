import java.util.ArrayList;
import java.util.List;

public class Control  {
	private List<String> ingredients = new ArrayList<String>();
	
	public Control(){};
	
	//synchronization method for agent to place items on the table
	public synchronized void placeItemsOnTable(List<String> theIngredients) throws InterruptedException{
		while (ingredients.size() != 0) wait();
		
		ingredients = theIngredients; //places items on the table
		System.out.println("Agent placed " + ingredients.get(0) + " and " + ingredients.get(1) + " on the table.");
		notifyAll(); //notifies the eaters that two items have been placed on the table
	}
	
	//synchronization method for eaters to make food an eat
	public synchronized void eat(long milli) throws InterruptedException
	{
		//while the food that is on the table is the same as the current eater thread; wait;
		while (ingredients.contains(Thread.currentThread().getName()) || ingredients.size() == 0) wait();
		
		//eat for a random amount of time passed in by the eater thread
		Thread.sleep(milli);

		String EaterIngredient  = Thread.currentThread().getName();
		
		System.out.println("Eater had " + EaterIngredient + ", picked up " + ingredients.get(0) + 
							" and " + ingredients.get(1) + " and ate for " + milli + "ms.");	
		
		//clear the food on the table
		ingredients.clear();
		
		//notify the agent that the food on the table has been eaten
		notifyAll();
	}
	
	//public method for seeing whats on the table
	public synchronized List<String> IngredientsOnTable()
	{
		return ingredients;
	}
}
