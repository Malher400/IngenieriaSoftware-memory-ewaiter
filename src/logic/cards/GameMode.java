package logic.cards;

import java.util.ArrayList;

public abstract class GameMode {
	
	// Atributos:
	protected String name;
	private static GameMode[] modes = {
		new Classic(),
		new Arcade(),
	};
	
	// Constructor:
	public GameMode() {}
	
	// Getters:
	public String getName() {
		return name;
	}
	
	public static GameMode parse(String inputString) {
       for (GameMode mode : modes)
           if (mode.getName().equalsIgnoreCase(inputString)) 
            return mode;
       return null;
    }
	
	public abstract ArrayList<Card> loadCards(int dim) throws Exception;
	 
}
