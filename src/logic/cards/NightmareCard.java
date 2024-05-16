package logic.cards;

import org.json.JSONException;
import org.json.JSONObject;


public class NightmareCard extends Card
{
	
	// Constantes:
	public static final String TYPE = "special";
	public static final String NAME = "NIGHTMARE_CARD";
	
	// Constructor:
	public NightmareCard() {
		super(NAME, TYPE);
	}

	// Métodos:	
	public Card pair() {
		return null;
	}

	public Card parse(JSONObject card) throws JSONException {
		if(card.getString("name").contentEquals(name)) return new NightmareCard();
		else return null;
	}

	public Action activate_single() {
		return new Action(){
			public void apply(int player, ActionReceiver list) {
				list.nightmare(player);
			}
			public String info() {
				return " is having a dreadful nightmare: will miss next turn.";
			}
		};
	}
	
	public Action activate_pair() {
		return null;
	}
}
