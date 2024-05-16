package logic.cards;

import org.json.JSONException;
import org.json.JSONObject;


public class ThiefPair extends Card{
	
	// Constantes:
	public static final String TYPE = "special";
	public static final String NAME = "THIEF_PAIR";
	public static final int STEAL = 10;
	
	// Constructores:
	public ThiefPair() {
		super(NAME, TYPE);
	}
	
	// Métodos:
	public Card pair() {
		return new ThiefPair();
	}

	public Card parse(JSONObject card) throws JSONException {
		if(card.getString("name").contentEquals(name)) return new ThiefPair();
		else return null;
	}

	public Action activate_single() {
		return null;
	}
	
	public Action activate_pair() {
		return new Action(){
			public void apply(int player, ActionReceiver list) {
				 list.steal(player, STEAL);
			}
			public String info() {
				return " discovered a pair of picklocks. Playing fair is for losers: steals up to " + STEAL + " points form the other players.";
			}
		};
	}
	
}
