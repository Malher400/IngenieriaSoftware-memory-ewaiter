package logic;

import org.json.JSONArray;


public interface GameObserver {

	public void onRegister(JSONArray players, JSONArray cards, int round, int turn);
	public void onTurnLost(int turn, int round);
	public void onCardPicked(int card, boolean discovered);
	public void onCardUnpicked(int card);
	public void onActionOccurred(JSONArray players, String s);
	public void onFinished(boolean winner, JSONArray players);
}
