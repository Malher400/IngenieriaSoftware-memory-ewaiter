package control;

import java.io.FileInputStream;

import org.json.JSONObject;
import org.json.JSONTokener;

import logic.Game;
import logic.GameObserver;
import logic.exceptions.AlreadyDiscoveredException;
import viewGUI.GameClient;


public class Controller {
	
	// Constantes:
	public static final String prompt = "Command > ";
	
	// Atributos:
	private Game game;
	int dim;
	String gm;
	String[] names;
	String[] diff;
	GameClient gameClient;
	
	// Constructor:
	public Controller(Game game) {
		this.game = game;
	}
	
	// Métodos:
	public void setMenu1(int num, int d, String g) {
		dim = d;
		gm = g;
		//gameClient.setMenu1(num, d, g);
	}
	
	public void setMenu2(String[] names, String[] diff) throws Exception {
		this.names = names;
		this.diff = diff;
		setGame();
		//gameClient.setMenu2(names, diff);
	}
	
	public void setGame() throws Exception {
		game.setGame(dim, gm, names, diff);
	}
	
	public void startGame() throws AlreadyDiscoveredException {
		game.automaticMove(); // si el primer jugador es un bot, empieza
		//gameClient.startGame();
	}
	
	public void pick(int card) throws AlreadyDiscoveredException{
		game.pick(card);
		//gameClient.pick(card);
	}
	
	public void load(FileInputStream file) throws Exception {
		game = new Game(new JSONObject(new JSONTokener(file)));
	}
	
	public void save(String file) {
		GameSaver.saveGame(game.getState(), file);
	}
	
	public void addObserver(GameObserver o) {
		game.addObserver(o);
	}
	
}
