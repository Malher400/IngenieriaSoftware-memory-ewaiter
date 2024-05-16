package logic.players;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import org.json.JSONArray;
import org.json.JSONObject;

import logic.GameObserver;
import logic.cards.ActionReceiver;


public class ArrayPlayers implements ActionReceiver{
	
	// Atributos:
	private ArrayList <Player> players = new ArrayList <Player>();
	private JSONArray playersRO = new JSONArray(); // lista de solo lectura
	
	// Constructor:
	public ArrayPlayers() {}
	
	public ArrayPlayers(String[] name_players, String[] diffs) {
		for (int i = 0; i < name_players.length; ++i) {
			players.add(new Player(name_players[i], StrategyGenerator.parse(diffs[i])));
			playersRO.put(players.get(i).getState());
		}
	}
	
	// Constructor de carga
	public ArrayPlayers(JSONObject json) {
		BotEye.init(json.getJSONObject("BotEye"));
		players = new ArrayList <Player>();
		JSONArray Players = json.getJSONArray("players");
		for(int i = 0; i < Players.length(); i++) {
			players.add(new Player(Players.getJSONObject(i)));
		}
		playersRO = Players;
	}
	
	// Set BotEye
	public GameObserver setBotEye() {
		return new BotEye();
	}

	// Getters:
	public int getNumPlayers() {
		return players.size();
	}
	
	public String getPlayerName(int i) {
		return players.get(i).getName();
	}
	
	// Devuelve si un jugador está dormido o no (para saber si hay que saltarle el turno)
	public boolean is_asleep(int turn) {
		return players.get(turn).isAsleep();
	}
	
	public int selectCard(int turn) {
		return players.get(turn).select();
	}
	
	// Devuelve los resultados finales, ordenados de mayor a menor puntiación
	public boolean get_winner() {
		int top = 0;
		boolean draw = false;
		for(int i = 0; i < players.size(); i++) {
			if(players.get(i).getPoints() == players.get(top).getPoints()) draw = true;
			if(players.get(i).getPoints() > players.get(top).getPoints()) {
				draw = false;
				top = i;
			}
		}
		return draw;
	}
	
	public void order_players() {
		Collections.sort(players, new Comparator<Player>() {
			public int compare(Player p1, Player p2){
				return p2.getPoints() - p1.getPoints();
			}});
	}

	// Suma puntuación
	public void add_points(int turn, int points) {
		players.get(turn).add_points(points);
	}
	
	// Resta puntuación
	public void sub_points(int turn, int points) {
		players.get(turn).sub_points(points);
	}
	
	// Pone a 0 la puntuación
	public void zero(int p){ 
		players.get(p).zero();
	}
	
	// Activa el escudo
	public void shield(int p) {
		players.get(p).shield();
	}
	
	// Roba de los demás jugadores para sumarselo a uno
	public void steal(int p, int steal) {
		int stolen = 0;
		for(Player player : players) {
			if (player != players.get(p)) {
				stolen += player.steal(steal);
			}
		}
		players.get(p).add_points(stolen);
	}
	
	// Un jugador toma una poción
	public void take_potion(int p, int percentage) {
		if(percentage > 0) players.get(p).giveElixir(percentage);
		else players.get(p).givePoison(percentage);
	}
	
	// Duerme un jugador
	public void nightmare(int p) {
		players.get(p).sleep();
	}
	
	// Devuelve el estado del objeto para guardar la partida
	public JSONObject getState() {
		JSONObject ArrayPlayers = new JSONObject();
		ArrayPlayers.put("BotEye", BotEye.getBotEyeState());
		JSONArray Players = new JSONArray();
		for(int i = 0; i < players.size(); i++) {
			Players.put(players.get(i).getState());
		}
		ArrayPlayers.put("players", Players);
		return ArrayPlayers;
	}
	
	public JSONArray getArrayPlayersRO() {
		playersRO = new JSONArray();
		for(Player p : players) {
			playersRO.put(p.getState());
		}
		return playersRO;
	}
	
}
