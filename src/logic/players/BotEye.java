package logic.players;

import java.util.Arrays;

import java.util.Random;
import java.util.TreeMap;

import org.json.JSONArray;
import org.json.JSONObject;

import logic.GameObserver;


public class BotEye implements GameObserver{
	private static String[] cardsInfo; // Nombres de las cartas ordenados por su posición en el tablero
	private static boolean[] seen; // Marca las cartas ya vistas
	private static boolean[] pickableRandomly; // Recoge si una carta puede ser seleccionada de forma aleatoria o no (por si ya ha sido descubierta)
	private static TreeMap<String, Integer> known; // Recoge las cartas que se conocen y a las que les falta la pareja
	private static TreeMap<Integer, Integer> pendingFowards; // Recoge las parejas de cartas pendientes de ser levantadas
	private static TreeMap<Integer, Integer> pendingBackwards; // mismo conjunto, invertido
	private static Random rand = new Random();
	
	
	public static void init(JSONObject json) {
		JSONArray Seen = json.getJSONArray("seen");
		int size = Seen.length();
		seen = new boolean[size];
		for(int i = 0; i < size; i++) {
			seen[i] = Seen.getBoolean(i);
		}
		known = new TreeMap<String, Integer>();
		JSONObject Known = json.getJSONObject("known");
		String[] names = JSONObject.getNames(Known);
		for(int i = 0; i < Known.length(); i++) {
			known.put(names[i], Known.getInt(names[i]));
		}
		pendingFowards = new TreeMap<Integer, Integer>();
		JSONObject PF = json.getJSONObject("pendingFowards");;
		names = JSONObject.getNames(PF);
		for(int i = 0; i < PF.length(); i++) {
			pendingFowards.put(Integer.parseInt(names[i]), PF.getInt(names[i]));
		}
		pendingBackwards = new TreeMap<Integer, Integer>();
		JSONObject PB = json.getJSONObject("pendingBackwards");
		names = JSONObject.getNames(PB);
		for(int i = 0; i < PB.length(); i++) {
			pendingBackwards.put(Integer.parseInt(names[i]), PB.getInt(names[i]));
		}
	}
	
	private static void update(int card, boolean discovered) {
		seen[card] = true;
		if(discovered) {
			if(pendingFowards.containsKey(card) || pendingBackwards.containsKey(card)) { // En pendientes
				removeFromPending(card);
			}
			else if(known.containsKey(cardsInfo[card]) && known.get(cardsInfo[card]) != card) { // Conocíamos su pareja
				pickableRandomly[known.get(cardsInfo[card])] = false;
				pickableRandomly[card] = false;
				known.remove(cardsInfo[card]);
			}
			else pickableRandomly[card] = false; // Era una carta especial, pues no conocíamos a su pareja y ha sido descubierta
		}
		else {
			if(known.containsKey(cardsInfo[card]) && known.get(cardsInfo[card]) != card) { // Conocemos a la pareja pero no a ella -> la añadimos a pendientes
				pendingFowards.put(known.get(cardsInfo[card]), card);
				pendingBackwards.put(card, known.get(cardsInfo[card]));
				known.remove(cardsInfo[card]);
			}
			else if (!(pendingFowards.containsKey(card) || pendingBackwards.containsKey(card))) known.put(cardsInfo[card], card); // Es nueva o ya la conocíamos
		}
	}
	
	// Elimina una pareja descubierta de la lista de pendientes y las marca como unpickable
	private static void removeFromPending(int card) {
		if(pendingFowards.containsKey(card)) {
			pickableRandomly[pendingFowards.get(card)] = false;
			pickableRandomly[card] = false;
			pendingBackwards.remove(pendingFowards.get(card));
			pendingFowards.remove(card);
		}
		else {
			pickableRandomly[pendingBackwards.get(card)] = false;
			pickableRandomly[card] = false;
			pendingFowards.remove(pendingBackwards.get(card));
			pendingBackwards.remove(card);
		}
	}
	
	public static int getRandCard() {
		int card = BotEye.rand.nextInt(BotEye.pickableRandomly.length);
		while(!BotEye.pickableRandomly[card]) card = BotEye.rand.nextInt(BotEye.pickableRandomly.length);
		return card;
	}
	
	public static int getRandUnseenCard() {
		int card = BotEye.rand.nextInt(BotEye.pickableRandomly.length);
		while(!BotEye.pickableRandomly[card] || BotEye.seen[card]) card = BotEye.rand.nextInt(BotEye.pickableRandomly.length);
		return card;
	}
	
	public static boolean getIfPending() {
		return !pendingFowards.isEmpty();
	}
	
	public static int getFirstPending1() {
		return BotEye.pendingFowards.firstKey();
	}
	
	public static int getFirstPending2() {
		return BotEye.pendingFowards.get(BotEye.pendingFowards.firstKey());
	}
	
	public static JSONObject getBotEyeState() {
		JSONObject Bot = new JSONObject();
		Bot.put("seen", seen);
		Bot.put("known", known);
		Bot.put("pendingFowards", pendingFowards);
		Bot.put("pendingBackwards", pendingBackwards);
		return Bot;
	}
	
	// Inicializa los atributos estáticos de Bot
	public void onRegister(JSONArray players, JSONArray cards, int round, int turn) {
		cardsInfo = new String[cards.length()];
		pickableRandomly = new boolean[cards.length()];
		for(int i = 0; i < cards.length(); i++) {
			cardsInfo[i] = cards.getJSONObject(i).getString("name");
			pickableRandomly[i] = cards.getJSONObject(i).getBoolean("discovered");
		}
		seen = new boolean[cardsInfo.length];
		Arrays.fill(pickableRandomly, true); // Las ponemos todas a tue
		known = new TreeMap<String, Integer>();
		pendingFowards = new TreeMap<Integer, Integer>();
		pendingBackwards = new TreeMap<Integer, Integer>();
	}

	public void onTurnLost(int turn, int round) {}

	// Actualiza la información de Bot
	public void onCardPicked(int card, boolean discovered) {
		update(card, discovered);
	}
	
	public void onCardUnpicked(int card) {}
	
	public void onActionOccurred(JSONArray players, String s) {}

	public void onFinished(boolean winner, JSONArray players) {}
	
}
