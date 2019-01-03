import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Dictionary {
	
//	Instancevariables
	private List<String> words;
	private URL path = getClass().getClassLoader().getResource("words.txt");
	private File file;
	
	
//	Constructor - Converts file, reads words from file and adds to arraylist.
	public Dictionary () {
//		convertFile();
		words = readWords();

	}
	
	
//	Choose a random word from the ArrayList
	public String getRandomWord () {
		Random slump = new Random();
		String fetchedWord = words.get(slump.nextInt(words.size()));
		removeWord(fetchedWord);
		return fetchedWord;
				
	}
	
//	Are there any words left
	public boolean isWordsLeft () {
		return !words.isEmpty();
	}
 	
	
//	Remove word from the list
	public void removeWord (String word ) {
		words.remove(word);
	}
	

//	Make a uri path from the txt file in the resources folder. 
	private void convertFile () {
		
		try {
			file = new File(getClass().getClassLoader().getResource("word.txt").toURI());
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
	}
	
	
//	Reads words from textfile and adds them to array 
//	This is only done once (when instantiating the Dictionary).
	private List<String> readWords () {
		
		List<String> readWords = new ArrayList<>();
			
		InputStream in = getClass().getClassLoader().getResourceAsStream("words.txt");
		BufferedReader reader = new BufferedReader(new InputStreamReader(in));
		
		while (true) {
			String textWord = "";
			try {
				textWord = reader.readLine();
			} catch (IOException e) {
				e.printStackTrace();
			}
			if (textWord == null ) {
				break;
			} else {
				readWords.add(textWord);
			}
		}
		System.out.println(readWords.size());
		
		try {
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return readWords;

	}
	


}
