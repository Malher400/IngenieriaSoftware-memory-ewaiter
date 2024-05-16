package cards;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import logic.cards.ShieldPair;

class ShieldPairTest {

	private static ShieldPair c8;
	public static final String TYPE = "special";
	public static final String NAME = "SHIELD_PAIR";
	

	@BeforeAll
	public static void constructorTest() { // Test del constructor por defecto
		c8 = new ShieldPair();
	}
	
	@Test
	public void nameTest() {
		// Comparamos el nombre:
		assertEquals(NAME, c8.getName());
	}
	
	

}
