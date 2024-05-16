package logic.cards;

import org.json.JSONException;
import org.json.JSONObject;


public class PoisonCard extends Card{
	// Constantes:
	public static final String TYPE = "special";
	public static final String NAME = "POISON_CARD";
	public static final int LESS_PERCENTAGE = -20;
	
	// Constructor:
	public PoisonCard() {
		super(NAME, TYPE);
	}

	// Métodos:
	public Card pair() {
		return null;
	}

	public Card parse(JSONObject card) throws JSONException {
		if(card.getString("name").contentEquals(name)) return new PoisonCard();
		else return null;
	}

	public Action activate_single() {
		return new Action(){
			public void apply(int player, ActionReceiver list) {
				list.take_potion(player, LESS_PERCENTAGE);
			}
			public String info() {
				return " discovered a nasty looking potion ...and drank it. Poison everywhere: " + LESS_PERCENTAGE + "% in future points.";
			}
		};
	}
	
	public Action activate_pair() {
		return null;
	}
}
