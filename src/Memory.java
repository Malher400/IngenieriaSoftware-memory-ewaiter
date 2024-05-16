import java.awt.EventQueue;
import java.lang.reflect.InvocationTargetException;

import control.Controller;
import logic.Game;
import viewGUI.MainView;

public class Memory {
	// Main:
	public static void main(String[] args) throws InvocationTargetException, InterruptedException {
		Game game = new Game();
		Controller control = new Controller(game);
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					new MainView(control);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});	
	}
	
}
