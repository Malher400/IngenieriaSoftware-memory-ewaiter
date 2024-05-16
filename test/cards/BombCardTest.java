package cards;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import logic.cards.BombCard;

class BombCardTest {

		private static BombCard c3;
		public static final String TYPE = "special";
		public static final String NAME = "BOMB_CARD";
		
		
		@BeforeAll
		public static void constructorTest() { // Test del constructor por defecto
			c3 = new BombCard();
		}
		
		@Test
		public void nameTest() {
			// Comparamos el nombre:
			assertEquals(NAME, c3.getName());
		}
		
		
}
