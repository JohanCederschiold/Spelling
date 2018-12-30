import javax.sound.sampled.LineUnavailableException;

public class Applause extends Sounds implements Runnable {
	
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
		long sleeptime = super.clip.getMicrosecondLength();
//		System.out.println(sleeptime / 1000);
		try {
			thread.sleep(sleeptime / 1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		super.clip.stop();
	}
	
	
	
	

}
