package logic.cards;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import logic.exceptions.AlreadyDiscoveredException;


public class ArrayCards {
	
	// Constantes:
	public static final String alreadyDiscoveredMsg = "This card is already turned";

	// Atributos:
	private ArrayList<Card> cards = new ArrayList<Card>();
	private JSONArray cardsRO;
	private boolean[] discovered; // indica que cartas del tablero han de estar dadas la vuelta
	private int numDiscovered;
	
	// Constructor:
	public ArrayCards() {}
	
	public ArrayCards(String gm, int dim) throws Exception{
		numDiscovered = 0;
		GameMode gameMode = GameMode.parse(gm);
		cards = gameMode.loadCards(dim); // Se cargan el ArrayLis de cartas de forma aleatoria
		discovered = new boolean[cards.size()];
		cardsRO = new JSONArray();
		for(int i = 0; i < cards.size(); i++) {
			JSONObject cd = new JSONObject();
			cd.put("name", cards.get(i).getName());
			cd.put("discovered", discovered[i]);
			cardsRO.put(cd);
		}
	}
	
	// Constructor de carga
	public ArrayCards(JSONObject json) throws Exception {
		numDiscovered = json.getInt("numDiscovered");
		JSONArray d = json.getJSONArray("discovered");
		discovered = new boolean[d.length()];
		for(int i = 0; i < d.length(); i++) {
			discovered[i] = d.getBoolean(i);
		}
		JSONArray c = json.getJSONArray("cards");
		for(int i = 0; i < c.length(); i++) {
			cards.add(CardGenerator.parse(c.getJSONObject(i)));
		}
		cardsRO = new JSONArray();
		for(int i = 0; i < cards.size(); i++) {
			JSONObject cd = new JSONObject();
			cd.put("name", cards.get(i).getName());
			cd.put("discovered", discovered[i]);
			cardsRO.put(cd);
		}
	}

	// Métodos:	
	// El nombre de las cartas coincide
	public boolean check_pair(int card1, int card2) {
		return cards.get(card1).pairs_with(cards.get(card2)); 
	}
	
	// Da la vuelta a una carta (permanentemente) y aumenta el contador
	public void discover_single(int card) { 
		discovered[card] = true;
		numDiscovered++;
	}
	
	// Da la vuelta a una pareja de cartas (permanentemente) y aumenta el contador
	public void discover_pair(int card1, int card2) { 
		discovered[card1] = true;
		discovered[card2] = true;
		numDiscovered += 2;
	}
	
	// Da la vuelta a una carta seleccionada (de manera temporal)
	public void pick(int card) throws AlreadyDiscoveredException { 
		if (discovered[card])
			throw new AlreadyDiscoveredException(alreadyDiscoveredMsg);
		discovered[card] = true;
	}
	
	// Pone boca abajo a una carta que había sido seleccionada
	public void unPick_single(int card) { 
		discovered[card] = false;
	}
	
	// Pone boca abajo a una pareja de cartas que había sido seleccionada
	public void unPick_pair(int card1, int card2) { 
		discovered[card1] = false;
		discovered[card2] = false;
	}
	
	// Devuelve la Acción de una carta que ha sido activada (carta especial o carta en blanco)
	public Action activate_single(int card) { 
		return cards.get(card).activate_single();
	}
	
	// Devuelve la Acción de una pareja de cartas que ha sido activada (tanto normales como especiales)
	public Action activate_pair(int card1, int card2) {  
		return cards.get(card1).activate_pair();
	}
	
	// Devuelve si todas las cartas han sido descubiertas y ha terminado la partida
	public boolean isFinished() {
		return numDiscovered >= cards.size();
	}
	
	// Devuelve el estado del objeto para poder guardar la partida
	public JSONObject getState() {
		JSONObject ArrayCards = new JSONObject();
		JSONArray Cards = new JSONArray();
		ArrayCards.put("discovered", discovered);
		ArrayCards.put("numDiscovered", numDiscovered);
		for(int i = 0; i < cards.size(); i++) {
			Cards.put(cards.get(i).getState());
		}
		ArrayCards.put("cards", Cards);
		return ArrayCards;
	}
	
	public JSONArray getArrayCardsRO() {
		return cardsRO;
	}
	
}
