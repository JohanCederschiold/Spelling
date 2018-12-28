import java.io.File;
import java.io.FileNotFoundException;
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
		convertFile();
		words = readWords();

	}
	
	
	public String getRandomWord () {
		Random slump = new Random();
		
		return words.get(slump.nextInt(words.size()));
				
	}
	
	
	public boolean isWordsLeft () {
		return !words.isEmpty();
	}
 	
	
	
	public void removeWord (String word ) {
		words.remove(word);
	}
	

	
	private void convertFile () {
		
		try {
			file = new File(path.toURI());
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
	}
	
	
	private List<String> readWords () {
//		Reads words from textfile and adds them to array.
		List<String> readWords = new ArrayList<>();
		
		try {
			Scanner fileReader = new Scanner(file);
			while (fileReader.hasNextLine()) {
				readWords.add(fileReader.nextLine());
			}
	
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		return readWords;

	}
	


}
