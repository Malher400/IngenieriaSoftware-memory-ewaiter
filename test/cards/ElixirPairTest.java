
package cards;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import logic.cards.ElixirPair;

class ElixirPairTest {

	private static ElixirPair c4;
	public static final String TYPE = "special";
	public static final String NAME = "ELIXIR_PAIR";
	

	@BeforeAll
	public static void constructorTest() { // Test del constructor por defecto
		c4 = new ElixirPair();
	}
	
	@Test
	public void nameTest() {
		// Comparamos el nombre:
		assertEquals(NAME, c4.getName());
	}
	
	
	

}