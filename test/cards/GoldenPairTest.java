package cards;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import logic.cards.GoldenPair;

class GoldenPairTest {

	private static GoldenPair c5;
	public static final String TYPE = "special";
	public static final String NAME = "GOLDEN_PAIR";
	

	@BeforeAll
	public static void constructorTest() { // Test del constructor por defecto
		c5 = new GoldenPair();
	}
	
	@Test
	public void nameTest() {
		// Comparamos el nombre:
		assertEquals(NAME, c5.getName());
	}
	

}
