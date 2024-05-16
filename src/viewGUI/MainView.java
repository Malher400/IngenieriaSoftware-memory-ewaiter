package viewGUI;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;

import org.json.JSONArray;

import control.Controller;

import logic.GameObserver;
import logic.exceptions.AlreadyDiscoveredException;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.Toolkit;
import java.io.File;
import java.io.FileInputStream;
import java.util.HashMap;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;



public class MainView extends JFrame {
	
	Controller ctrl;
	
	//Identificadores que permiten el cambio de vistas
	final static String _starterMenuGUI = "starterMenuGUI";		
	final static String _gameModeMenuGUI = "gameModeMenuGUI";
	final static String _boardGUI = "boardGUI";
	final static String _settingsMenuGUI= "settingsMenuGUI";
	final static String _optionsGame = "optionsGameGUI";
	final static String _playerNamesGUI = "playerNamesGUI";
	final static String _endGameGUI="endGameGUI";
	
	//Vistas del juego
	private JPanel panelsMemory;		//Panel cambiante
	private StarterMenuView starterMenu;
	private OptionsGameView optionsGame;			//Caracteristicas de una partida
	private BoardView board;
	private PlayerNamesView playerNames;
	private EndGameView endGame;
	
	//Layouts
	private CardLayout mainCard;			//Layout del panel cambiante: permite cambiar entre distintas vistas
	
	//Fuente Arcadae
	public static Font levelBots;
	public static Font arcadeFont;
	public static Font arcadeFontPoints;
	public static Font instructionsFont;
	public static Font playerNamesFont;
	public static Font roundFont;
	
	//Tama�o pantalla del jugador
	public final static Dimension _screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	
	public MainView(Controller ctrl) {
		this.ctrl = ctrl;
		try {
			roundFont= Font.createFont(Font.TRUETYPE_FONT, new File("ARCADECLASSIC.TTF")).deriveFont(25f);
			playerNamesFont = Font.createFont(Font.TRUETYPE_FONT, new File("ARCADECLASSIC.TTF")).deriveFont(75f);
			instructionsFont = Font.createFont(Font.TRUETYPE_FONT, new File("PressStart2P-Regular.TTF")).deriveFont(24f);
			levelBots= Font.createFont(Font.TRUETYPE_FONT, new File("ARCADECLASSIC.TTF")).deriveFont(34f);
			arcadeFontPoints=Font.createFont(Font.TRUETYPE_FONT, new File("ARCADECLASSIC.TTF")).deriveFont(30f);
			arcadeFont = Font.createFont(Font.TRUETYPE_FONT, new File("ARCADECLASSIC.TTF")).deriveFont(60f);
			GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
			ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("ARCADECLASSIC.TTF")));
			ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("PressStart2P-Regular.TTF")));
		}
		catch(Exception e) {}
		Tools.playSound("music/fondo1.wav");
		initMainViewGUI();
		
		setVisible(true);
	}
	

	private void initMainViewGUI() {
		// Ventana Principal
		this.setTitle("Memory");
		this.setExtendedState(MAXIMIZED_BOTH);	//Obliga a que la ventana sea en pantalla completa
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.getContentPane().setLayout(new BorderLayout(0, 0));
		this.setSize(_screenSize);
	
		// Paneles Cambiante
		panelsMemory = new JPanel();
		this.setContentPane(panelsMemory);
		mainCard = new CardLayout();
		panelsMemory.setLayout(mainCard);
		
		
		//PRUEBAS--No borrar pls
//		ArrayList p = new ArrayList<Player>();
//		
//		for(int i=0;i<4;i++) {
//			p.add(new Player("aaaaa"));
//		}
//		
//		addEndGamePanel(p);
		
		// Menus
		starterMenu = new StarterMenuView(this);
		optionsGame = new OptionsGameView(this);
		
		panelsMemory.add(starterMenu, _starterMenuGUI); // Se a�ade el componente y el identificador
		panelsMemory.add(optionsGame, _optionsGame);
		
		this.pack();
	}
	
	protected void addEndGamePanel(JSONArray players, boolean winner, HashMap<String, Color> colorMap) {
		endGame = new EndGameView(this, players, winner, colorMap);
		panelsMemory.add(endGame,_endGameGUI);
	}
	
	protected void addPlayersPanel(int num) {
		playerNames = new PlayerNamesView(this, num);
		panelsMemory.add(playerNames,_playerNamesGUI);
	}
	
	protected void addBoardPanel() {
		board = new BoardView(this);
		panelsMemory.add(board, _boardGUI);
	}
	
	protected void changeView(String view) {
		if(view.equals(_optionsGame)) {
			optionsGame.removeAll();
			optionsGame.resetPanel();
			mainCard.show(panelsMemory, view);			//Cambia directamente al panel que se indique	
		}
		else {
			mainCard.show(panelsMemory, view);			//Cambia directamente al panel que se indique
		}
	}
	
	protected void reset() {
		try {
			setGame();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		addBoardPanel();
		changeView(MainView._boardGUI);
		new Thread() {
			public void run() {
				try {
				  startGame();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}.start();
	}
	
	// Hace de interfaz del paquete
	protected void addObserver(GameObserver panel) {
		ctrl.addObserver(panel);
	}
	
	protected void setMenu1(int numPlayers, int dim, String gm) {
		ctrl.setMenu1(numPlayers, dim, gm);
	}
	
	protected void setMenu2(String[] names, String[] diffs) throws Exception{
		ctrl.setMenu2(names, diffs);
	}
	
	protected void setGame() throws Exception {
		ctrl.setGame();
	}
	
	protected void load(FileInputStream in) throws Exception {
		ctrl.load(in);
	}
	
	protected void save(String file) {
		ctrl.save(file);
	}
	
	protected void startGame() throws AlreadyDiscoveredException {
		ctrl.startGame();
	}
	
	protected void pick(int card) throws AlreadyDiscoveredException {
		ctrl.pick(card);
	}
	
}