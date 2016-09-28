import java.util.LinkedList;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;


public class Salon {
	private int numberOfChairs;
	private int numberOfBarbers;
	private final long maximumCutTime;
	private final BlockingQueue<Thread> queue;
	
	public Salon(int numberOfCustomers, int aNumberOfBarbers, int aNumberOfChairs, long maxCutTime, long aMaxGrowTime)
	{
		numberOfChairs = aNumberOfChairs;
		maximumCutTime = maxCutTime;
		numberOfBarbers = aNumberOfBarbers;
		queue = new ArrayBlockingQueue<Thread>(100);
		
        for (int i=0; i<numberOfBarbers; i++) {
        	new Barber("Barber" + i, this);
        }
        
        for (int i=0; i<numberOfCustomers; i++) {
        	new Customer("Customer" + i, this, aMaxGrowTime);
        }
	}

	public synchronized void cutHair() throws InterruptedException
	{
		String barberName = Thread.currentThread().getName();
		System.out.println(barberName + " free, waiting for customer");
		
		while(queue.isEmpty()) wait();
		
		Thread customerNeedsHairCut = queue.remove();
		
		numberOfChairs++;
		
		System.out.println(barberName + " has " + customerNeedsHairCut.getName() +", waiting " + queue.size());	
		
		long cutTime = random();
		
		System.out.println(barberName + " cutting hair");
		System.out.println(customerNeedsHairCut.getName() + " getting haircut for " + cutTime + "ms");
		
		Thread.sleep(cutTime);
		
		//customerNeedsHairCut.start();
		
		notify();
	}
	
	public synchronized long random()
	{
		return (long) (Math.random() * maximumCutTime);
	}
	
	public synchronized void sitDown() throws InterruptedException
	{
		while (numberOfChairs == 0) wait();
		
		queue.add(Thread.currentThread());
		
		numberOfChairs--;
		
		String customerName = Thread.currentThread().getName();
		
		System.out.println(customerName + " in room, waiting " + queue.size());
		
		notifyAll();

		wait();
	}
	
	public synchronized BlockingQueue<Thread> getQueue()
	{
		return queue;
	}
		
}
