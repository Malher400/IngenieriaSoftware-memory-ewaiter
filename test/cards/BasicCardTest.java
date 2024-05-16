package cards;


import static org.junit.jupiter.api.Assertions.*;

import org.junit.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import logic.cards.BasicCard;
import logic.cards.Card;

class BasicCardTest {
	private static BasicCard c1; 
	public static final String NAME = "card1";
	public static final String TYPE = "basic";  
	public static final String DESC = "A basic card.";
	

	@BeforeAll
	public static void constructorTest() { // Test del constructor por defecto
		c1 = new BasicCard(NAME, TYPE);
	}
	
}
