import java.io.Serializable;

public class Player implements Serializable {
	
	private int points;
	private String name;
	
	
	
	public Player (String name) {
		points = 0;
		this.name = name;
	}
	
	

	public int getPoints() {
		return points;
	}

	public String getName() {
		return name;
	}

	public void addPoints(int pointsToAdd) {
		points += pointsToAdd;
	}

	public void changeName(String name) {
		this.name = name;
	}
	
	

}
