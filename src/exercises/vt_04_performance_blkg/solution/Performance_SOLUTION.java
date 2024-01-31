package exercises.vt_04_performance_blkg.solution;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/*
 * Diese Loesung zeigt, wie mit virtual Threads sehr viele parallele blockierende 
 * Aufrufe stabil und schnell ausgefuehrt werden koennen.
 * 
 * Im Beispiel sieht man, dass 10.000 Calls von sleep() innerhalb von einer Sekunde ausgefuehrt werden.
 */
public class Performance_SOLUTION {

	
	
	public static void main(String[] args) {
		
		Performance_SOLUTION instance = new Performance_SOLUTION();
		
		instance.virtualThreadsFast();
		
	}
		
	void virtualThreadsFast(){
		
		// 1 Sekunde bei bis zu 10.000 Auftraegen
		ExecutorService pool = Executors.newVirtualThreadPerTaskExecutor(); //
				
		
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
		
		// wartet bis alle Jobs abgeareitet sind
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
