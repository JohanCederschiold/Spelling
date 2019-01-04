import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class Game {
	
	private Dictionary dictionary;
	private String currentWord; //The word currently being guessed
	private String correctSoFar; //How much of the word that is guessed. 
	private boolean isWin; //Keeping track if the word is correctly guessed. 
	private int wrongGuesses; //Number of wrong guesses on current letter. 
	private boolean withoutFaults; //No wrong guesses on current Word. 
	private String skippedFile = "skipped.txt";
	
	public Game() {
		
		dictionary = new Dictionary();
		isWin = false;
		
	}
	
	
	public void getNewWord ()  {

		
//		Reset win (i.e. the word is not correctly guessed). Also reset feedback string and get new random word. 
		isWin = false;
		withoutFaults = true;
		correctSoFar = "";
		currentWord = dictionary.getRandomWord();

		playWord();

	}
	
	public void checkLetter (String letter) {
		
		if (currentWord == null ) {
			return;
		}
		
//		Checks if letter is correct and (if so) if word is complete
		if (letter.equalsIgnoreCase(currentWord.substring(correctSoFar.length(), correctSoFar.length()+1))) {
//			Add correctly guessed letter to string with correct guess so far. 
			correctSoFar += currentWord.substring(correctSoFar.length(), correctSoFar.length()+1);
//			Reset wrongGuesses
			wrongGuesses = 0;
			if (correctSoFar.equalsIgnoreCase(currentWord)) {
				isWin = true;
			}
		} else {
			wrongGuesses++; //Add to wrong guesses
			withoutFaults = false;
		}
		
	}
	
	public void playWord () {
		Sounds word = new Sounds(currentWord);
		word.thread.start();
	}
	
//	Get the current status of the guessed word. 
	public String getWordSoFar () {
		return correctSoFar;
	}
	
//	Is the word correctly guessed. 
	public boolean getIsWin () {
		return isWin;
	}
	
	public void playApplause () {
		Sounds applause = new Sounds("applause");
		applause.thread.start();
	}
	
	
//	Are there more words
	public boolean moreWords () {
		return dictionary.isWordsLeft();
	}
		
	public int getWrongGuesses () {
		return wrongGuesses;
	}
	
	public String getCurrentWord () {
		return currentWord;
	}
	
	public boolean getWithoutFaults () {
		return withoutFaults;
	}
	
	public void markedAsSkipped () {

		/*  The file is created in the projects folder since it's not a resource per see. The intent of the textfile is to 
		 *  mark words the user wants to skip to be able so refine the chosen words. 
		 */
		
		try {
			PrintWriter write = new PrintWriter(new BufferedWriter(new FileWriter(new File(skippedFile),true)));
			write.println(currentWord);
			write.close();
		} catch (IOException e) {
			System.out.println("Could not save");
			e.printStackTrace();
		}
		
	}
	


}
