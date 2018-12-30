import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.border.LineBorder;

public class UserInterface extends JFrame {
	
	
//	Components 
	private JButton startGame;
	private JButton playWord;
	private JButton getNextWord;
	private JLabel [] alphabet;
	private Border border = new LineBorder(Color.black, 2);
	private Font font = new Font("Arial Black", Font.BOLD, 18);
	private JLabel progress;
	
//	Containers
	private JPanel letterPanel;
	private JPanel keysAndLabels;
	
//	Gamecomponents
	private String [] letters = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M",
			"N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z", "Å", "Ä", "Ö"	};
	private Game game;

	
	

	public UserInterface () {
		
		game = new Game();
		setTitle("Stavnings-spel");

		setLayout(new BorderLayout());
		add(letterPanel = new JPanel(), BorderLayout.CENTER);
		add(keysAndLabels = new JPanel(), BorderLayout.NORTH);
		letterPanel.setPreferredSize(new Dimension(1700, 900));
		alphabet = new JLabel [letters.length];
		
//		Add letters
		for (int i = 0; i < letters.length ; i++ ) {
			alphabet[i] = new JLabel(new ImageIcon(getClass().getClassLoader().getResource(letters[i]+".png")));
			letterPanel.add(alphabet[i]);
			alphabet[i].addMouseListener(l);
		}
		
		
//		Add buttons & components
		playWord = new JButton("Spela upp ordet"); 
		keysAndLabels.add(playWord);
		playWord.setEnabled(false);
		getNextWord = new JButton("Få nytt ord");
		keysAndLabels.add(getNextWord);
		progress = new JLabel(""); 
		progress.setFont(font);
		upDateProgress();
		keysAndLabels.add(progress);
		
		
//		Add listeners
		playWord.addActionListener(e -> game.playWord());
		getNextWord.addActionListener(e -> getNewWord());
		
		
		

		
		pack();
		setLocationRelativeTo(null);
		setVisible(true);
		
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		
	}
	
	MouseListener l = new MouseAdapter () {
		
		public void mouseReleased(MouseEvent e) {
			
			if (!game.getIsWin()) {
				for (int i = 0 ; i < letters.length ; i++ ) {
					alphabet[i].setBorder(null);
					if (e.getSource() == alphabet[i] ) {
						alphabet[i].setBorder(border);
						game.checkLetter(letters[i]);
					}
					
				}
				upDateProgress();
				
				
				
				if (game.getIsWin()) {
					upDateProgress();
					gameWon();

				}
			}
			

			
		}; 

	};
	
	public void upDateProgress () {
		progress.setText(String.format("Ordet: %s", game.getWordSoFar()));
	}
	
	public void gameWon () {
		
		playWord.setEnabled(false);
		getNextWord.setEnabled(true);
		game.playApplause();
		
		if (!game.moreWords()) {
			progress.setText("No more words");
		}

	}
	
	public void getNewWord () {
		
		game.getNewWord();
		upDateProgress();
		playWord.setEnabled(true);
		getNextWord.setEnabled(false);
	}
	
	
}
