import java.util.Scanner;

public class Game {
	
	private Dictionary dictionary;
	private String currentWord;
	private String correctSoFar;
	private Sounds appl;
	private Sounds sayWord;
	
	public Game() {
		
		dictionary = new Dictionary();
		appl = new Sounds("applause");
		getNewWord();
		
	}

	
	
	public void getNewWord ()  {
		
		correctSoFar = "";
		currentWord = dictionary.getRandomWord();
		
		sayWord = new Sounds(currentWord);
		sayWord.playClip();

	}
	
	public void checkLetter (String letter) {
		
		if (letter.equalsIgnoreCase(currentWord.substring(correctSoFar.length(), correctSoFar.length()+1))) {
			correctSoFar += currentWord.substring(correctSoFar.length(), correctSoFar.length()+1);
			if (correctSoFar.equalsIgnoreCase(currentWord)) {
				appl.playClip();
			}
			
		}
		
	}
	
	public void playWord () {
		sayWord.playClip();		
	}


}
