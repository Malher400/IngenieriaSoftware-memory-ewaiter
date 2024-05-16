package cards;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import logic.cards.ThiefPair;

class ThiefPairTest {

	private static ThiefPair c10;
	public static final String TYPE = "special";
	public static final String NAME = "THIEF_PAIR";
	

	@BeforeAll
	public static void constructorTest() { // Test del constructor por defecto
		c10 = new ThiefPair();
	}
	
	@Test
	public void nameTest() {
		// Comparamos el nombre:
		assertEquals(NAME, c10.getName());
	}
	
	
	
	

}
