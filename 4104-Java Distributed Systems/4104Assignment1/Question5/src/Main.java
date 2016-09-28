
public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println("Start");
		
		Salon theSalon = new Salon(5, 5, 5, 2000, 2000);
		
		long startTime = System.currentTimeMillis();
		
		
		while (System.currentTimeMillis() - startTime < 10000)
		{
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		System.out.println("");
		System.out.println("FINISHED");
		System.exit(0);
		
		//customers, barbers, chairs, cut time, grow time
		
	}

}
