package logic.cards;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

public class Arcade extends GameMode{

	public Arcade() {
		name = "Arcade";
	}

	public ArrayList<Card> loadCards(int dim) throws Exception{
		ArrayList<Card> arrayCards = new ArrayList<Card>();
		int num_of_cards = dim * 4;
		InputStream BasicCardsIn = new FileInputStream(new File("resources/BasicCards.json"));
		JSONObject jsonInput = new JSONObject(new JSONTokener(BasicCardsIn));
		JSONArray ja = jsonInput.getJSONArray("basic_cards");
		
		// Metemos todas las cartas basicas (sin sus parejas) en un ArrayList auxiliar
		ArrayList<Card> auxArray = new ArrayList<Card>();
		for (int i = 0; i < ja.length(); i++) {
			Card c = CardGenerator.parse(ja.getJSONObject(i));
			auxArray.add(c);
		}
		
		// Lo barajamos
		Collections.shuffle(auxArray);
		
		// En arcade tenemos que introducir las especiales
		InputStream SpecialCardsIn = new FileInputStream(new File("resources/SpecialCards.json"));
		jsonInput = new JSONObject(new JSONTokener(SpecialCardsIn));
		jsonInput = jsonInput.getJSONObject("special_cards");
		ja = jsonInput.getJSONArray("dim " + dim);
		
		// Metemos las especiales y sus parejas si las tienen
		for (int i = 0; i < ja.length(); i++) {
			Card c = CardGenerator.parse(ja.getJSONObject(i));
			arrayCards.add(c);
			c = c.pair();
			if(c != null) {
				arrayCards.add(c);
			}
		}
		
		// Rellena el resto con cartas normales y sus parejas
		int ini_size = arrayCards.size();
		for(int i = 0; 2*i < num_of_cards - ini_size; i++) {
			arrayCards.add(auxArray.get(i));
			arrayCards.add(auxArray.get(i).pair());
		}
		
		Collections.shuffle(arrayCards);
		return arrayCards;
	}
}
