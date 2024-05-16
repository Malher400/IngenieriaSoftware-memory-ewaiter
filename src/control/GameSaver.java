package control;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;

import org.json.JSONObject;



public class GameSaver {
	// Guarda el juego en un archivo con el nombre elegido
	public static void saveGame(JSONObject game, String fileName) {
		String file = "resources/" + fileName + ".json";
		PrintStream printer = System.out;
		try {
			OutputStream out = new FileOutputStream(new File(file));
			printer = new PrintStream(out);
		}
		catch (FileNotFoundException e){
			System.out.println("Error while saving game");
		}
		printer.print(game);
		System.out.println("Game saved successfully");
	}
}
