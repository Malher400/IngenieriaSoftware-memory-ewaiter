package logic.cards;

import org.json.JSONException;
import org.json.JSONObject;


public class DeathSkullCard extends Card{

	// Constantes:
	public static final String TYPE = "special";
	public static final String NAME = "DEATH_SKULL_CARD";
	
	// Constructor:
	public DeathSkullCard() {
		super(NAME, TYPE);
	}
	
	// Métodos:
	public Card pair() {
		return null;
	}

	public Card parse(JSONObject card) throws JSONException {
		if(card.getString("name").contentEquals(name)) return new DeathSkullCard();
		else return null;
	}

	public Action activate_single() {
		return new Action(){
			public void apply(int player, ActionReceiver list) {
				list.zero(player);
			}
			public String info() {
				return " gets a taste of death: loses all points.";
			}
		};
	}
	
	public Action activate_pair() {
		return null;
	}

}
