package logic.cards;

import org.json.JSONException;
import org.json.JSONObject;


public class BasicCard extends Card{
	
	// Constantes
	public static final String TYPE = "basic";  
	public static final int POINTS = 10;

	
	// Constructores:
	public BasicCard() {}
	
	public BasicCard(String name, String type) {
		super(name, type);
	}
	
	// Métodos:
	public BasicCard pair() {
		return new BasicCard(name, TYPE);
	}
	
	public BasicCard parse(JSONObject card) throws JSONException {
		if(card.getString("type").contentEquals(TYPE)) return new BasicCard(card.getString("name"), TYPE);
		else return null;
	}

	public Action activate_single() {
		return null;
	}
	
	public Action activate_pair() {
		return new Action(){
			public void apply(int player, ActionReceiver list) {
				list.add_points(player, POINTS);
			}
			public String info() {
				return " discovered a pair of basic cards and received points!";
			}
		};
	}

	
}			
