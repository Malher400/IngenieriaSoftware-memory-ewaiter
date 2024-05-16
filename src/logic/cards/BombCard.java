package logic.cards;

import org.json.JSONException;
import org.json.JSONObject;


public class BombCard extends Card{
	
	// Constantes:
	public static final String TYPE = "special";
	public static final String NAME = "BOMB_CARD";
	public static final int SUBTRACTION = 20;
	
	// Constructor:
	public BombCard() {
		super(NAME, TYPE);
	}

	// Métodos:
	public Card pair() {
		return null;
	}

	public Card parse(JSONObject card) throws JSONException {
		if(card.getString("name").contentEquals(name)) return new BombCard();
		else return null;
	}

	public Action activate_single() {
		return new Action(){
			public void apply(int player, ActionReceiver list) {
				list.sub_points(player, SUBTRACTION);
			}
			public String info() {
				return " discovered a hidden bomb: -" + SUBTRACTION + " points.";
			}
		};
	}
	
	public Action activate_pair() {
		return null;
	}

}
