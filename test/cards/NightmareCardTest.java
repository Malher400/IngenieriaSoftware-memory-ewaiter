
package cards;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import logic.cards.NightmareCard;

class NightmareCardTest {

	private static NightmareCard c6;
	public static final String TYPE = "special";
	public static final String NAME = "NIGHTMARE_CARD";
	
	

	@BeforeAll
	public static void constructorTest() { // Test del constructor por defecto
		c6 = new NightmareCard();
	}
	
	@Test
	public void nameTest() {
		// Comparamos el nombre:
		assertEquals(NAME, c6.getName());
	}
	
	
	
	
}