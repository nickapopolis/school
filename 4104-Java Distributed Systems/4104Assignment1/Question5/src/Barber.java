
public class Barber implements Runnable{
	private final String name;
	private Salon s;
	private boolean running = true;
	
	public Barber(String aName, Salon aSalon){
		name = aName;
		s = aSalon;
		new Thread(this, name).start(); //start thread
	}
	
	
	public void stop()
	{
		System.out.println(name + " thread stopped.");
		running = false;
	}
	
	@Override
	public void run() {
		while(running){
			try {
				s.cutHair();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
