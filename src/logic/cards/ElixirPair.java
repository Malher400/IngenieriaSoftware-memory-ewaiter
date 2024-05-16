package logic.cards;

import org.json.JSONException;
import org.json.JSONObject;


public class ElixirPair extends Card{
	
	// Constantes:
	public static final String TYPE = "special";
	public static final String NAME = "ELIXIR_PAIR";
	public static final int EXTRA_PERCENTAGE = 50;
	
	// Constructores:
	public ElixirPair() {
		super(NAME, TYPE);
	}
	
	// Métodos:
	public Card pair() {
		return new ElixirPair();
	}

	public Card parse(JSONObject card) throws JSONException {
		if(card.getString("name").contentEquals(name)) return new ElixirPair();
		else return null;
	}

	public Action activate_single() {
		return null;
	}
	
	public Action activate_pair() {
		return new Action(){
			public void apply(int player, ActionReceiver list) {
				list.take_potion(player, EXTRA_PERCENTAGE);
			}
			public String info() {
				return " discovered a pair of vials, why not try them? +" + EXTRA_PERCENTAGE + "% in future points.";
			}
		};
	}
}
