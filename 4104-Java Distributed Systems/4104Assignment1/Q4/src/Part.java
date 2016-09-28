import java.util.Random;
import java.util.Vector;

/**
 * This class acts as a real part that is based upon a spec. 
 * It has two states, built and not built. A "build" command can be sent to it
 * which makes it "build itself"
 * @author 9a3eedi
 *
 */
public class Part {
	
	private PartSpec spec;
	
	private Vector<Part> childParts;
	private boolean built = false;
	private Thread buildThread = null;
	
	/**
	 * Makes a new Part based off a specification
	 * @param aSpec the specification to base this part on
	 */
	public Part(PartSpec aSpec){
		if(aSpec == null){
			throw new NullPointerException();
		}
		
		spec = aSpec;
		built = false;
		childParts = new Vector<Part>();
		for(PartSpec s: aSpec.getDependenciesList()){
			childParts.add(new Part(s));
		}
	}
	
	/**
	 * Builds this part. This function is blocking, but it starts its own thread for the build process
	 * The thread then starts other threads, making it recursive.
	 */
	public synchronized void build(){
		if(built || buildThread != null)
			return;
		
		buildThread = new Thread(){			
			public synchronized void run(){
				System.out.println("Building " + spec.partId);
				for(Part p: childParts){
					p.build();
					
				}
				int waitTime = 10 + (new Random().nextInt(990)); // "building" time
				try{
					for(Part p: childParts)
						p.buildThread.join();
					sleep(waitTime);
				}catch(InterruptedException e){}
				
				built = true;
				System.out.println("Done building " + spec.partId);
				//theParent.notify(); // this part is done
			}
		};
		buildThread.start();

	}

	/**
	 * Checks if the children are built
	 * Not really fast, but whatever
	 * 
	 * @return true if all of this part's children are built, false otherwise
	 */
	public synchronized boolean childrenAreBuilt(){
		
		for(Part p: childParts){
			if(!p.built)
				return false;
		}
		return true;
		
	}
	
	/**
	 * @return a boolean indicating whether this part is built or not
	 */
	public synchronized boolean isBuilt(){
		return built;
	}

	/**
	 * @return a string representation of this part. This is just the id of the spec that defines this part
	 */
	public String toString(){
		return spec.getId();
	}
	
	/**
	 * Blocks until the build thread finishes (if this part is building)
	 */
	public void waitForBuild(){
		try{
			if(buildThread != null)
				buildThread.join();
		}catch(InterruptedException e){
	
		}
	}
}
