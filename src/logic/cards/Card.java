package logic.cards;

import org.json.JSONObject;
import org.json.JSONException;

public abstract class Card {
	
	// Atributos:
	protected String name;
	protected String type;
	
	// Constructores:
	public Card() {}
	
	public Card(String name, String type) {
		this.name = name;
		this.type = type;
	}
	
	// Métodos:
	public String getName() {
		return name;
	}
	
	public boolean pairs_with(Card c) {
		return name.contentEquals(c.name);
	}
	
	// Devuelve la pareja de una carta (una copia) si tiene (algunas especiales no tienen pareja)
	public abstract Card pair();
	
	// Devuelve una carta de su clase si la información del JSONObject es la adecuada (type y name)
	public abstract Card parse(JSONObject card) throws JSONException;
	
	// Activa una carta individual que ha sido descubierta
	public abstract Action activate_single();
	
	// Activa una pareja de cartas que ha sido descubierta
	public abstract Action activate_pair();
	
	// Devuelve el estado de la carta para guardar la partida
	public JSONObject getState() {
		JSONObject card = new JSONObject();
		card.put("type", type);
		card.put("name", name);
		return card;
	}


}
