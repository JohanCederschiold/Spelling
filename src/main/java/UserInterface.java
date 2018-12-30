import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
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
	private JLabel [] alphabet;
	private Border border = new LineBorder(Color.black, 2);
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
		progress = new JLabel(""); 
		upDateProgress();
		keysAndLabels.add(progress);
		
		
//		Add listeners
		playWord.addActionListener(e -> game.playWord());
		

		
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
						game.checkLetter(letters[i]);
						alphabet[i].setBorder(border);
					}
					
				}
				upDateProgress();
				
				
				
				if (game.getIsWin()) {

					gameWon();

				}
			}
			

			
		}; 

	};
	
	public void upDateProgress () {
		progress.setText(String.format("Ordet: %s", game.getWordSoFar()));
	}
	
	public void gameWon () {
		
//		TODO: Try putting the applaus in separate class and thread it.

//		game.playApplause();
		if (game.moreWords()) {
			game.getNewWord();
			upDateProgress();
		} else {
//			game.playApplause();
		}
	}
	
	
}
