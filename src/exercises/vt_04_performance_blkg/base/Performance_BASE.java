package exercises.vt_04_performance_blkg.base;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/*
 * Hier sieht man zwei Herangenesweisen, um mit Platform Threads  
 * blockiernde OS-Aufrufe parallel abzuarbeiten.
 * 
 * Ansatz 1 nutzt einen Thread-Pool fester Groesse. Dadurch ist die Performance limitiert.
 * 
 * Ansatz 2 nutzt einen Thread-Pool variabler Groesse. Dies kann bei zu vielen Auftraegen
 * zum Crash fuehren.
 * 
 * Zeige, dass durch die Nutzung von virtual Threads eine Loesung gefunden werden kann, 
 * die stabiler als Ansatz 2 ist und schneller als Ansatz 1.
 */
public class Performance_BASE {
	
	public static void main(String[] args) {
		
		Performance_BASE instance = new Performance_BASE();
		
		instance.platformThreadsSlow();
		
	}
	
	void platformThreadsSlow(){
		
		// Ansatz 1: 10 Sekunden (Langsam)
		ExecutorService pool = Executors.newFixedThreadPool(1000); // Pool mit 100 Platform Threads
		
		
		
		// Ansatz 2:  Crash ab 4100 Jobs auf meinem Rechner (schneller, aber nicht stabil), wenn der Heap 
		//ExecutorService pool = Executors.newCachedThreadPool(); // Wachsender Pool mit  Platform Threads
		
		long start = System.currentTimeMillis();
		
		for(int i = 0 ; i <= 10_000; ++i) {
			final int loop_cnt = i;
			
			pool.submit(() -> {
				
				try {
					System.out.println("Job " + loop_cnt + " started");
		
					// Blockierender Aufruf
					Thread.sleep(1000);
					
					System.out.println("Job " + loop_cnt + " woke up " + (System.currentTimeMillis() - start) / 1000 + " Seconds ");
				
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			});
		}
		
		// Wartet bis alle Jobs abgeareitet sind
		pool.close();
		
	}
	
	/////////////// HELPER ///////////////////
	private static void sleep(int millis) {
		try {
			Thread.sleep(millis);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
