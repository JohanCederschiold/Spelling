import java.util.Scanner;

public class Game {
	
	private Dictionary dictionary;
	private Scanner scanner;
	
	public Game() {
		
		scanner = new Scanner(System.in);
		
	}
	
	public void gameRound() {
		
		dictionary = new Dictionary();
		

		
	}
	
	
	public void wordRound ()  {
		
		Sounds appl = new Sounds("applause");
		
		Spellword spellword = new Spellword(dictionary.getRandomWord());
		String currentLetter = "";
		
		Sounds sayWord = new Sounds(spellword.getWord());
		sayWord.playClip();
		
		for (int i = 0 ; i < spellword.getWordLength();  i++) {
			System.out.println("Skriv en bokstav");
			do  {
				currentLetter = scanner.nextLine().substring(0, 1);
			} while (!spellword.getWord().substring(i, i+1).equals(currentLetter));
			System.out.println("Rätt");
			spelloutWordSoFar(spellword.getWord(), i);
		}
		
		System.out.println("Du klarade ordet");
		appl.playClip();
		
		
		
		

		
	}
	
	public void spelloutWordSoFar (String word, int position) {
		String wordSoFar = word.substring(0, position+1);
		System.out.println(wordSoFar);
	}
	


}
