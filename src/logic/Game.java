package logic;

import logic.cards.Action;
import logic.cards.ArrayCards;
import logic.exceptions.AlreadyDiscoveredException;
import logic.players.ArrayPlayers;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.swing.SwingUtilities;

import org.json.JSONObject;

public class Game {
	
	// Atributos:
	private ArrayPlayers playerList;
	private ArrayCards cardList;
	private int round = 1;
	private int turn = 0; //nos indica a qu� jugador le toca
	private int prevCard; // almacena la posición de la primera carta seleccionada en el turno
	private boolean hasPickedOnce = false;
	private List<GameObserver> obsList = new ArrayList<GameObserver>();
	private GameServer gameServer;
	
	// Constructor:
	public Game() {
		cardList = new ArrayCards();
		playerList = new ArrayPlayers();
	}
	
	// Constructor de carga
	public Game(JSONObject GameFile) throws Exception{
		cardList = new ArrayCards(GameFile.getJSONObject("cardList"));
		round = GameFile.getInt("round");
		turn = GameFile.getInt("turn");
		prevCard = GameFile.getInt("prevCard");
		hasPickedOnce = GameFile.getBoolean("hasPickedOnce");
		playerList = new ArrayPlayers(); // para poder añadir a BotEye de observador ha de existir
		obsList = new ArrayList<GameObserver>();
		addObserver(playerList.setBotEye());
		playerList = new ArrayPlayers(GameFile.getJSONObject("playerList"));
	}
	
	public void setGame(int dim, String gm, String[] names, String[] diffs) throws Exception{
		cardList = new ArrayCards(gm, dim);
		playerList = new ArrayPlayers(names, diffs);
		obsList = new ArrayList<GameObserver>();
		addObserver(playerList.setBotEye());
		hasPickedOnce = false;
		turn = 0;
		round = 1;
	}
	
	// Métodos:
	// Devuelve si la partida ha terminado
	private boolean isFinished() {
		return cardList.isFinished();
	}
	
	// Actualiza el mensaje de la última acción
	private String LastActionMessage(Action a) {
		if (a == null) return playerList.getPlayerName(turn) + " picked a card and... nothing really happened";
		else return playerList.getPlayerName(turn) + a.info(); 
	}
	
	// Selecciona una carta para darle la vuelta
	public void pick(int i) throws AlreadyDiscoveredException {
		cardList.pick(i);
		check(i);
		if(!isFinished()) automaticMove();
	}
	
	// Pasa de turno
	private void nextTurn() {
		if (turn == playerList.getNumPlayers() - 1) {
			turn = 0;
			round++;
		}
		else turn++;
		if (playerList.is_asleep(turn)) nextTurn(); // Si está dormido, le despierta y pasa turno de nuevo
		for(GameObserver o: obsList) {
			o.onTurnLost(turn, round);
		}
	}
	
	// Tirada automática
	public void automaticMove() throws AlreadyDiscoveredException {
		int card = playerList.selectCard(turn);
		while(card == prevCard) card = playerList.selectCard(turn); // para que no elija la misma que antes
		// Si la elección se corresponde con la estrategia de un bot
		if(card >= 0) { 
			try {
				if(hasPickedOnce) TimeUnit.SECONDS.sleep(1);
				else TimeUnit.SECONDS.sleep(3);
			} catch (InterruptedException e) {
				return;
			}
			pick(card);
		}
	}
	
	// Comprueba el resultado de la jugada
	private void check(int card) {
		if(!check_single(card)) { // No ha ocurrido ninguna activación de una sola carta
			if (!hasPickedOnce) {
				hasPickedOnce = true;
				prevCard = card;
				for(GameObserver o: obsList) {
					o.onCardPicked(card, false);
				}
			}
			else check_pair(card); // Es la segunda carta, se comprueba la pareja
		}
	}
	
	private boolean check_single(int card) {
		Action a = cardList.activate_single(card);
		if(a == null) return false; // No ha pasado nada
		// Hay activación
		cardList.discover_single(card); // Se descubre la carta
		a.apply(turn, playerList); // Se aplica la acción
		for(GameObserver o: obsList) {
			o.onCardPicked(card, true);
			o.onActionOccurred(playerList.getArrayPlayersRO(), LastActionMessage(a));
			if(isFinished()) {
				playerList.order_players();
				o.onFinished(playerList.get_winner(), playerList.getArrayPlayersRO());
			}
		}
		if (hasPickedOnce) { // Si es la segunda que levanta
			hasPickedOnce = false;
			// Esperamos para darle la vuela a la anterior elegida
			SwingUtilities.invokeLater( new Runnable() {
				public void run() {
					try {
						TimeUnit.MILLISECONDS.sleep(1500);
					} catch (InterruptedException e) {
						return;
					}
				}
			});
			cardList.unPick_single(prevCard); // Da la vuelta a la primera
			for(GameObserver o: obsList) {
				o.onCardUnpicked(prevCard);
			}
		}
		nextTurn(); // Todas cartas especiales unicas haran que pase el turno
		return true;
	}

	private void check_pair(int card) {
		if(cardList.check_pair(prevCard, card)) { // Si son pareja
			Action a = cardList.activate_pair(prevCard, card); // Obtiene la acción
			cardList.discover_pair(prevCard, card); // Descubre ambas cartas
			a.apply(turn, playerList); // Aplica la acción
			hasPickedOnce = false;
			for(GameObserver o: obsList) {
				o.onCardPicked(card, true);
				o.onActionOccurred(playerList.getArrayPlayersRO(), LastActionMessage(a));
				if(isFinished()) {
					playerList.order_players();
					o.onFinished(playerList.get_winner(), playerList.getArrayPlayersRO());
				}
			}
		}
		else {
			for(GameObserver o: obsList) {
				o.onCardPicked(card, false);
				o.onActionOccurred(playerList.getArrayPlayersRO(), LastActionMessage(null));
			}
			hasPickedOnce = false;
			// Pausamos para que se vean ambas cartas antes de dar la vuelta
			SwingUtilities.invokeLater( new Runnable() {
				public void run() {
					try {
						TimeUnit.MILLISECONDS.sleep(1500);
					} catch (InterruptedException e) {
						return;
					}
				}
			});
			nextTurn();	// Pasa el turno
			cardList.unPick_pair(prevCard, card); // Les da la vuelta
			for(GameObserver o: obsList) {
				o.onCardUnpicked(card);
				o.onCardUnpicked(prevCard);
			}
		}
	}
	
	// Devuelve el estado del juago para guardar la partida
	public JSONObject getState() {
		JSONObject Game = new JSONObject();
		Game.put("playerList", playerList.getState());
		Game.put("cardList", cardList.getState());
		Game.put("round", round);
		Game.put("turn", turn);
		Game.put("prevCard", prevCard);
		Game.put("hasPickedOnce", hasPickedOnce);
		return Game;
	}
	
	public void addObserver(GameObserver o) {
		if (!obsList.contains(o)) {
			obsList.add(o);
			o.onRegister(playerList.getArrayPlayersRO(), cardList.getArrayCardsRO(), round, turn);
		}
	}
	
	// M�TODOS RED
	
	public void acceptConnections() {
		gameServer = new GameServer();
		gameServer.acceptConnections();
	}
	
}
