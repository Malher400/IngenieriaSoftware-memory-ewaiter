package cards;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import logic.cards.DeathSkullCard;

class DeathSkullTest {

	private static DeathSkullCard c3;
	public static final String TYPE = "special";
	public static final String NAME = "DEATH_SKULL_CARD";;
	

	@BeforeAll
	public static void constructorTest() { // Test del constructor por defecto
		c3 = new DeathSkullCard();
	}
	
	@Test
	public void nameTest() {
		// Comparamos el nombre:
		assertEquals(NAME, c3.getName());
	}
	
	
	
	
}
