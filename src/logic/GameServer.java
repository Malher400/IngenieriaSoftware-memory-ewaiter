package logic;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.*;
import java.util.ArrayList;

import org.json.JSONArray;

import logic.exceptions.AlreadyDiscoveredException;

public class GameServer implements GameObserver {
	
	private static final int MAX_PLAYERS = 2; // Se podr�a llegar a aumentar
	
	private ServerSocket serverSocket;
	private int numPlayers;
	private ArrayList<ServerConnection> connectionsList;
	private Game game;
	
	public GameServer() {
		
		try {
			serverSocket = new ServerSocket(17071);
			connectionsList = new ArrayList();
		}
		catch (IOException e) {
			System.out.println("IOException");
		}
		
	}
	
	public void acceptConnections() {
		
		try {
			while (numPlayers < MAX_PLAYERS) {
				Socket s = serverSocket.accept();
				++numPlayers;
				ServerConnection sc = new ServerConnection(s, numPlayers);
				connectionsList.add(sc);
				Thread thread = new Thread(sc);
				thread.start();
			}
		}
		catch (IOException e) {
			System.out.println("IOException");
		}
		
	}
	
	private class ServerConnection implements Runnable {

		private Socket socket;
		private DataInputStream dataIn;
		private DataOutputStream dataOut;
		private int playerNum;
		
		public ServerConnection(Socket s, int num) {
			socket = s;
			playerNum = num;
			try {
				dataIn = new DataInputStream(socket.getInputStream());
				dataOut = new DataOutputStream(socket.getOutputStream());
			}
			catch (IOException e) {
				System.out.println("IOException");
			}
		}
		
		@Override
		public void run() {
			try {
				dataOut.writeInt(playerNum);
				dataOut.flush();
			}
			catch (IOException e) {
				System.out.println("IOException");
			}
			
		}
		
	}
	
	// EN LOS M�TODOS COMO OBSERVADOR MANDA LA INFORMACI�N A LOS CLIENTES
	// Hay que darle vueltas a esto

	@Override
	public void onRegister(JSONArray players, JSONArray cards, int round, int turn) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onTurnLost(int turn, int round) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onCardPicked(int card, boolean discovered) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onCardUnpicked(int card) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onActionOccurred(JSONArray players, String s) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onFinished(boolean winner, JSONArray players) {
		// TODO Auto-generated method stub
		
	}
	
	/*public void setMenu1(int num, int d, String g) {
		dim = d;
		gm = g;
	}
	
	public void setMenu2(String[] names, String[] diff) throws Exception {
		this.names = names;
		this.diff = diff;
		setGame();
	}
	
	public void setGame() throws Exception {
		game.setGame(dim, gm, names, diff);
	}
	
	public void startGame() throws AlreadyDiscoveredException {
		game.automaticMove(); // si el primer jugador es un bot, empieza
	}
	
	public void pick(int card) throws AlreadyDiscoveredException{
		game.pick(card);
	}
	
	public void load(FileInputStream file) throws Exception {
		game = GameLoader.loadGame(file);
	}
	
	public void save(String file) {
		GameSaver.saveGame(game, file);
	}
	
	public void addObserver(GameObserver o) {
		game.addObserver(o);
	}*/
}
