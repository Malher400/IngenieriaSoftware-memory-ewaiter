package cards;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import logic.cards.PoisonCard;

class PoisonCardTest {

	private static PoisonCard c7;
	// Constantes:
		public static final String TYPE = "special";
		public static final String NAME = "POISON_CARD";
	

	@BeforeAll
	public static void constructorTest() { // Test del constructor por defecto
		c7 = new PoisonCard();
	}
	
	@Test
	public void nameTest() {
		// Comparamos el nombre:
		assertEquals(NAME, c7.getName());
	}

	

}
