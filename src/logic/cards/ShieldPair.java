package logic.cards;

import org.json.JSONException;
import org.json.JSONObject;


public class ShieldPair extends Card{
	
	// Constantes:
	public static final String TYPE = "special";
	public static final String NAME = "SHIELD_PAIR";
	
	// Constructores:
	public ShieldPair() {
		super(NAME, TYPE);
	}
	
	// Métodos:
	public Card pair() {
		return new ShieldPair();
	}

	public Card parse(JSONObject card) throws JSONException {
		if(card.getString("name").contentEquals(name)) return new ShieldPair();
		else return null;
	}

	public Action activate_single() {
		return null;
	}
	
	public Action activate_pair() {
		return new Action(){
			public void apply(int player, ActionReceiver list) {
				list.shield(player);
			}
			public String info() {
				return " discovered a pair of interesting pieces of metal. Might be useful: +Shield.";
			}
		};
	}
	
}
