
public class Customer implements Runnable {
	private final String name;
	private final long maximumSleepTime;
	private boolean running = true;
	private Salon s;
	
	public Customer(String aName, Salon aSalon,long maxSleepTime){
		name = aName;
		s = aSalon;
		maximumSleepTime = maxSleepTime;
		new Thread(this, name).start(); //start thread
	}
	
	public void stop()
	{
		System.out.println(name + " thread stopped.");
		running = false;
	}
	
	public String name()
	{
		return name;
	}
	
	@Override
	public void run() {
		long growTime = 0;
		while(running){
			//grow hair
			try {
				growTime = random();
				System.out.println(name + " is growing hair for " + growTime + "ms");
				Thread.sleep(growTime);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			try {
				s.sitDown();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	public long random()
	{
		return (long) (Math.random() * maximumSleepTime);
	}

}
