package logic.cards;

import org.json.JSONException;
import org.json.JSONObject;
import logic.exceptions.InvalidCardException;


public class CardGenerator {
	
public static final String unknownCommandMsg ="Unknown card";
	
	private static Card[] availableCards = {
		new BasicCard(),
		new GoldenPair(),
		new BombCard(),
		new DeathSkullCard(),
		new ShieldPair(),
		new ThiefPair(),
		new ElixirPair(),
		new PoisonCard(),
		new NightmareCard(),
	};
	
	// Recorre los comandos disponibles hasta que encuentre uno que encaje con la entrada y devuelve la carta apropiada
	public static Card parse (JSONObject card) throws InvalidCardException, JSONException {
		int i = 0;
		Card c = null;
		while (c == null && i < availableCards.length) {
			c = availableCards[i].parse(card);
			i++;
		}
		if(c == null) throw new InvalidCardException("[ERROR]: " + unknownCommandMsg);
		return c;
	}

}
