package logic.players;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
class PlayerTest {
		private static Player p1;
		private static final String NAME_1 = "Javier";
		private static final int POINTS_1 = 0;
		private static final int BONUS_1 = 0;
		private static final boolean ASLEEP_1 = false;
		
		@BeforeAll
		public static void constructorTest() { // Test del constructor por defecto
			p1 = new Player(NAME_1, new HumanStrategy());
		}
		
		@Test
		public void nameTest() {
			// Comparamos el nombre:
			assertEquals(NAME_1, p1.getName());
		}
		
		@Test
		public void pointsTest() {
			// Comparamos la puntuación:
			assertEquals(POINTS_1, p1.getPoints());
		}

		
		@Test
		public void isAsleepTest() {
			
			// Comprobamos si está dormido:
			assertEquals(ASLEEP_1, p1.isAsleep());
		}
	

	    @Test                                               
	    @DisplayName("Testing giveElixir()")   
	    public void testgiveElixir() {


	    	 Player instance = new Player("Prueba", new HumanStrategy());
	         int expResult = 15;

	         instance.giveElixir(8);
	         instance.giveElixir(50);
	         instance.add_points(10);

	         
	         assertEquals(expResult, instance.getPoints(), "The method give elixir should work for new instances");  

	         instance = new Player("Prueba", new HumanStrategy());

	       
	         int expResult2 = 8;
	         instance.giveElixir(-20);
	         instance.add_points(10);
	         assertEquals(expResult2, instance.getPoints(), "The method give elixir should work for negative percentages"); 

	    }

	    @Test
		public void testadd_points() { 
			
			 Player instance = new Player("Prueba", new HumanStrategy());
			 int expResult = 10;
			
			 instance.add_points(10);
			 
			 assertEquals(expResult, instance.getPoints(), "The method add_points() should work for new instances");  
			 
			 instance.giveElixir(100);
			 instance.add_points(10);
			 
			 int expResult2 = 30;
			 
			 assertEquals(expResult2, instance.getPoints(), "The method add_points() should work"); 
			 
			 
			 Player instance2 = new Player("Prueba", new HumanStrategy());
			 
			 instance.add_points(-1);
			 
			 assertEquals(0, instance2.getPoints(), "The method add_points() should work for negative parameters");
	    
	    }
}
