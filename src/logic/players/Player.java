package logic.players;

import org.json.JSONObject;

public class Player { 
	
	// Atributos:
	protected String name;
	protected Strategy strat;
	protected int points = 0;
	protected boolean shielded = false;
	protected int bonus = 0;
	protected boolean sleep = false;
	
	// Constructor:
	public Player (String name, Strategy strat){ 
		this.name = name;
		this.strat = strat;
	}
	
	public Player(JSONObject json) {
		name = json.getString("name");
		strat = StrategyGenerator.parse(json.getString("strat"));
		points = json.getInt("points");
		shielded = json.getBoolean("shielded");
		bonus = json.getInt("bonus");
		sleep = json.getBoolean("sleep");
	}

	// Devuelve la puntuación del jugador
	public int getPoints(){
		return points;
	}
	
	// Devuelve si el jugador está dormido para saber si hay que saltar el turno
	public boolean isAsleep() {
		boolean asleep = sleep;
		sleep = false; // Se despierta cuando le saltan el turno
		return asleep;
	}
	
	// Devuelve el nombre
	public String getName() {
		return name;
	}
	
	public int select() {
		return strat.select();
	}
	
	// Se le suma puntos
	public void add_points(int d){ 
		points = points + (d + d*bonus/100);
		if(points < 0) points = 0;
	}
	
	// Se le restan puntos 
	public void sub_points(int d){ 
		if (shielded) shielded = false;
		else {
			points = points - d;
			if(points < 0) points = 0;
		}
	}
	
	// puntuación a 0
	public void zero(){
		if (shielded) shielded = false;
		else points = 0;
	}

	// Se activa su escudo
	public void shield() {
		shielded = true;
	}
	
	// Se le roba
	public int steal(int steal) {
		if (shielded) {
			shielded = false;
			return 0;
		}
		int iniPoints = points;
		sub_points(steal);
		int stolen = iniPoints - points; // Total robado
		return stolen;
	}
	
	// Bebe el elixir
	public void giveElixir(int percentage) {
		bonus += percentage;
	}
	
	// Bebe el veneno
	public void givePoison(int percentage) {
		if (shielded) shielded = false;
		else bonus += percentage;
	}
	
	// Duerme
	public void sleep() {
		if (shielded) shielded = false;
		else sleep = true;
	}
	
	// Devuelve el estado del jugador para guardar la partida
	public JSONObject getState() {
		JSONObject player = new JSONObject();
		player.put("name", name);
		player.put("strat", strat.getName());
		player.put("points", points);
		player.put("shielded", shielded);
		player.put("bonus", bonus);
		player.put("sleep", sleep);
		return player;
	}

	
}
