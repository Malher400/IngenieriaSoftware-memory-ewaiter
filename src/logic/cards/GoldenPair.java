package logic.cards;

import org.json.JSONException;
import org.json.JSONObject;


public class GoldenPair extends Card{
	
	// Constantes:
	public static final String TYPE = "special";
	public static final String NAME = "GOLDEN_PAIR";
	public static final int EXTRA_POINTS = 50;
	
	// Constructores:
	public GoldenPair() {
		super(NAME, TYPE);
	}
	
	// Métodos:
	public Card pair() {
		return new GoldenPair();
	}

	public Card parse(JSONObject card) throws JSONException {
		if(card.getString("name").contentEquals(name)) return new GoldenPair();
		else return null;
	}

	public Action activate_single() {
		return null;
	}
	
	public Action activate_pair() {
		return new Action(){
			public void apply(int player, ActionReceiver list) {
				list.add_points(player, EXTRA_POINTS);
			}
			public String info() {
				return " discovered a pair of shiny rocks, wonders if they are worth something: +" + EXTRA_POINTS + " points.";
			}
		};
	}

}
