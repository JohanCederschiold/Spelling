import javax.sound.sampled.LineUnavailableException;

public class Applause extends Sounds implements Runnable {
	
	/*	The class extends Sounds and makes the applause a separate thread to avoid
	 * 	"pauses" in the program. 
	 */
	
	Thread thread = new Thread(this);

	public Applause(String word) {
		super(word);
		
	}
	
	public Applause () {
		this("applause");
	}

	@Override
	public void run() {
		
		super.clip.start();
//		Checks the length of the clip to set the correct sleeptime. 
		long sleeptime = super.clip.getMicrosecondLength();
		try {
			Thread.sleep(sleeptime / 1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		super.clip.stop();
	}
	
	
	
	

}
