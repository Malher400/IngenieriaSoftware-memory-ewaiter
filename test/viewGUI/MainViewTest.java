package viewGUI;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import control.Controller;
import logic.Game;


class MainViewTest {


	@Test
	public static void constructorTest() { // Test del constructor por defecto
		Controller ctrl = new Controller(new Game());
		
		MainView mainView = new MainView(ctrl);
		
	}
	

}
