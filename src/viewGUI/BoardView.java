package viewGUI;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.SwingUtilities;
import java.awt.GridBagLayout;
import java.awt.Image;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import javax.swing.ImageIcon;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.awt.event.ActionEvent;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import java.awt.Color;
import java.awt.Component;
import javax.swing.border.MatteBorder;
import javax.swing.border.TitledBorder;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import org.json.JSONArray;
import org.json.JSONObject;

import logic.GameObserver;


public class BoardView extends JPanel implements ActionListener, GameObserver {
	
	//Componentes
	private JButton pauseButton;
	private JButton helpButton;
	private JTextPane infoAction;
	private List<CardView> cards = new ArrayList<CardView>();
	private Deque<PlayerDataView> players= new LinkedList<PlayerDataView>();
	private HashMap<String, Color> colorMap;
	private JLabel roundsView;
	private PauseMenuDialog pauseDialog;
	
	//Atributos red
	private GameClient gameClient;
	
	//Atributos como observador de la partida
	private int numPlayers = 0;
	private JSONArray arrayCards;
	
	//Imagenes
	private Image _titleIMG = new ImageIcon("icons/memory_title.png").getImage();
	private Image _pauseIMG = new ImageIcon("icons/pause_menu/pause_button.png").getImage();
	private Image _helpIMg = new ImageIcon("icons/board/help_button.png").getImage();
	
	//Dimensiones
	private Dimension dimCards;
	private Dimension dimBorder = Tools.pixels(100,20);	//Distancia del panel a lo margenes de la ventana: 20-UP/DOWN y 100-LEFT/RIGHT
	
	MainView _mainPanel;
	Thread thread;
	
	public BoardView(MainView mainPanel) {
		_mainPanel =mainPanel;
		_mainPanel.addObserver(this);
		initGUI();
	}
	
	private void initGUI() {
		this.setLayout(new BorderLayout());
		
		JPanel north = northPanel();
		dimCards = selectDimCards(arrayCards.length()/4);			//Devuelve una dimension para las cartas q se ajusta (ma o meno) a la pantalla
		JPanel center = board4xN(arrayCards.length()/4,dimCards);
		JPanel left = sidePanel();
		JPanel right = sidePanel();
		JPanel south = southPanel();
		
		this.add(north,BorderLayout.NORTH);
		this.add(center, BorderLayout.CENTER);
		this.add(left,BorderLayout.WEST);
		this.add(right, BorderLayout.EAST);
		this.add(south,BorderLayout.SOUTH);
		
		pauseDialog = new PauseMenuDialog((Frame) SwingUtilities.getWindowAncestor(this));
	}
	
	private void initiateColors(JSONArray players) {
		colorMap = new HashMap<String, Color>();
		if(numPlayers >= 1) colorMap.put(players.getJSONObject(0).getString("name"), new Color(1, 134, 182)); //Azul
		if(numPlayers >= 2) colorMap.put(players.getJSONObject(1).getString("name"), new Color(215, 48, 98)); //Rojo
		if(numPlayers >= 3) colorMap.put(players.getJSONObject(2).getString("name"), new Color(12, 215, 117)); //Verde
		if(numPlayers >= 4) colorMap.put(players.getJSONObject(3).getString("name"), new Color(253, 224, 3)); //Amarillo	
	}
	
	private JPanel northPanel() {		//Panel norte con titulo
		JPanel north = new JPanel();
		north.setMinimumSize(Tools.pixels(1920,155));
		north.setMaximumSize(Tools.pixels(1920,155));
		north.setPreferredSize(Tools.pixels(1920,155));
		north.setBorder(new MatteBorder(0, 100, 0, 100,Color.WHITE));
		north.setBackground(Color.WHITE);
		north.setLayout(new BorderLayout());
		
		//Memory Title
		JPanel titlePanel = new JPanel();
		titlePanel.setBackground(Color.WHITE);
		titlePanel.setMinimumSize(Tools.pixels(1720,40));
		titlePanel.setMaximumSize(Tools.pixels(1720,40));
		titlePanel.setPreferredSize(Tools.pixels(1720,40));
		JLabel title = new JLabel();
		title.setMinimumSize(Tools.pixels(200,40));
		title.setMaximumSize(Tools.pixels(200,40));
		title.setPreferredSize(Tools.pixels(200,40));
		title.setBackground(Color.WHITE);
		title.setIcon(new ImageIcon(Tools.resizeImage(_titleIMG, Tools.pixels(200,40))));
		titlePanel.add(title);
		north.add(titlePanel,BorderLayout.NORTH);
		
		//Players y Ronda
		JPanel playersPanel = new JPanel();
		playersPanel.setBackground(Color.WHITE);
		for(int i = 0; i < numPlayers; i++) {
			PlayerDataView playerN = new PlayerDataView(i + 1);
			players.addLast(playerN);
			_mainPanel.addObserver(playerN);
			playersPanel.add(playerN);
			playersPanel.add(createSeparator(Tools.pixels(20,0)));
		}
		players.getFirst().plays(true);
		north.add(playersPanel,BorderLayout.CENTER);
		
		//Rondas : hay que poner varias etiquetas porque con la fuente no sse puede poner ":"
		JPanel rounds = new JPanel();
		rounds.setBackground(Color.WHITE);
		JLabel roundsLbl = new JLabel("Rounds");
		rounds.add(roundsLbl);
		JLabel aux =(new JLabel(" : "));
		aux.setFont(new Font("Calibri",Font.PLAIN, 27));
		rounds.add(aux);
		roundsView = new JLabel("0");
		rounds.add(roundsView);
		north.add(rounds,BorderLayout.EAST);
		roundsLbl.setFont(MainView.roundFont);
		roundsView.setFont(MainView.roundFont);
		
		return north;
	}
	
	
	private JPanel board4xN(int n, Dimension dimCard) {		//Panel Central
		JPanel boardNxN = new JPanel();
		boardNxN.setLayout(new GridBagLayout());
		boardNxN.setBorder(BorderFactory.createTitledBorder(
		BorderFactory.createLineBorder(Color.BLACK, 5), "Board",TitledBorder.LEFT, TitledBorder.TOP));
		//boardNxN.setBorder(new MatteBorder(10, dimBorder.width, 10, dimBorder.width,Color.WHITE));		//Groso margenes del panel
		boardNxN.setBackground(new Color(163, 228, 215));
		
		//Tablero
		initialiteCards(dimCard);
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.weighty = 1.0;		//Separacion entre columnas y filas
		constraints.weightx=  1.0;
		int k=0;
		for(int i=0;i<4;i++) {
			for(int j=0;j<n;j++) {
				constraints.gridx = j;			//Columna j
				constraints.gridy = i;			//Fila i
				constraints.gridwidth = 1;		//Ocupa una columna
				constraints.gridheight = 1;		//Ocupa una fila
				boardNxN.add (cards.get(k), constraints);
				k++;
			}
		}
		return boardNxN;
	}
	
	
	private JPanel sidePanel() {		//Paneles vacions
		JPanel side = new JPanel();
		side.setBackground(Color.WHITE);
		side.setMinimumSize(Tools.pixels(100,860));
		side.setMaximumSize(Tools.pixels(100,860));
		side.setPreferredSize(Tools.pixels(100,860));
		return side;
	}
	
	
	private JPanel southPanel() {
		JPanel south = new JPanel();
		south.setLayout(new BoxLayout(south,BoxLayout.X_AXIS));
		south.setBackground(Color.WHITE);
		
		//Dimensiones y margenes del panel
		south.setMinimumSize(Tools.pixels(1920,110));
		south.setMaximumSize(Tools.pixels(1920,110));
		south.setPreferredSize(Tools.pixels(1920,110));
		south.setBorder(new MatteBorder(10, 250, 10, 250,Color.WHITE));		//Groso margenes del panel
		
		//Botones y movimientos
		//PAUSE
		pauseButton = new JButton();
		pauseButton.setMinimumSize(Tools.pixels(210,120));
		pauseButton.setMaximumSize(Tools.pixels(210,120));
		pauseButton.setPreferredSize(Tools.pixels(210,120));
		pauseButton.addActionListener(this);
		pauseButton.setBackground(Color.WHITE);
		pauseButton.setAlignmentY(BOTTOM_ALIGNMENT);
		pauseButton.setAlignmentX(LEFT_ALIGNMENT);
		pauseButton.setIcon(new ImageIcon(Tools.resizeImage(_pauseIMG, Tools.pixels(200,65))));
		Tools.removeShapeButton(pauseButton);
		Tools.setRollOverEffect(pauseButton, _pauseIMG, Tools.pixels(210,70));
		south.add(pauseButton);
		south.add(createSeparator(new Dimension(130,0)));
		
		//INFO ACTION
		infoAction = new JTextPane();
		infoAction.setMinimumSize(Tools.pixels(750,100));
		infoAction.setMaximumSize(Tools.pixels(750,100));
		infoAction.setPreferredSize(Tools.pixels(750,100));
		infoAction.setBorder(new MatteBorder(5, 5, 5, 5,Color.BLACK) );
		infoAction.setAlignmentX(CENTER_ALIGNMENT);
		infoAction.setEditable(false);
		Font fuente = new Font("Dialog", Font.BOLD, 20);
		infoAction.setFont(fuente);
		JScrollPane scrollPane = new JScrollPane(infoAction);
		scrollPane.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		StyledDocument doc = infoAction.getStyledDocument(); 
		SimpleAttributeSet center = new SimpleAttributeSet(); 
		StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER); 
		doc.setParagraphAttributes(0, doc.getLength(), center, false); 
		south.add(infoAction);
		south.add(createSeparator(new Dimension(130,0)));
		
		//HELP
		helpButton = new JButton();
		helpButton.setMinimumSize(Tools.pixels(210,100));
		helpButton.setMaximumSize(Tools.pixels(210,100));
		helpButton.setPreferredSize(Tools.pixels(210,100));
		helpButton.addActionListener(this);
		helpButton.setBackground(Color.WHITE);
		helpButton.setAlignmentY(BOTTOM_ALIGNMENT);
		helpButton.setAlignmentX(RIGHT_ALIGNMENT);
		helpButton.setIcon(new ImageIcon(Tools.resizeImage(_helpIMg, Tools.pixels(195,55))));
		Tools.removeShapeButton(helpButton);
		Tools.setRollOverEffect(helpButton,_helpIMg, Tools.pixels(200,60));
		south.add(helpButton);
		return south;
	}
	
	
	private void initialiteCards(Dimension dimCard) {
		for(int i = 0; i < arrayCards.length(); i++) {
			cards.add(new CardView(dimCard, arrayCards.getJSONObject(i), i));
		}
	}
	
	
	private Component createSeparator(Dimension d) {			//Crea un separador
		Component separator = Box.createHorizontalStrut(20);
		separator.setMinimumSize(d);
		separator.setMaximumSize(d);
		separator.setPreferredSize(d);
		return separator;
	}
	
	
	private Dimension selectDimCards(int numOfCards) {		//No intenteis entender esto xd
		Dimension dim = Tools.pixels(120,160);		//4<=N<=7;
		Dimension aux = Tools.pixels(0,265);		//Es la altura del panel norte mas la del panel sur 
		double  height =MainView._screenSize.height-2*aux.height;		//altura del tablero segun los p�xeles de la pantalla del jugador
		double heightCard = (double)height/4;		//altura de la carta: altura tablero entre las 4 filas que hay
		double width = MainView._screenSize.width - 2*dimBorder.width;			//Ancho de la pantalla del jugador menos los margenes izq y der
		double widhtCard = (double)(width)/numOfCards-20;	
		
		if(8 <= numOfCards && numOfCards < 10) {
			dim = Tools.pixels((int)widhtCard-50,(int)heightCard);
		}
		else if(10 <= numOfCards && numOfCards < 13){
			dim = Tools.pixels((int)widhtCard-10,(int)heightCard);
		}
		else if(13 <= numOfCards && numOfCards <= 16){
			dim = Tools.pixels((int)widhtCard,(int)heightCard);
		}
		return dim;
	}
	

	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==pauseButton) {
			int option = pauseDialog.open();
			switch(option) {
			case 0: { // continue
				pauseDialog.setVisible(false);
			} break;
			case 1: { // reset
				if(thread != null) thread.interrupt();
				_mainPanel.reset();
			} break;
			case 2: { // save and exit
				String res = pauseDialog.getFileName();
				_mainPanel.save(res);
				if(thread != null) thread.interrupt();
				_mainPanel.changeView("starterMenuGUI");
			} break;
			case 3: { // exit
				if(thread != null) thread.interrupt();
				_mainPanel.changeView("starterMenuGUI");
			} break;
			}
		}
		else if(e.getSource()==helpButton) {
			new OpenInstructionsDialog((Frame)_mainPanel);
		}
	}

	
	public void onRegister(JSONArray players, JSONArray cards, int round, int turn) {
		numPlayers = players.length();
		initiateColors(players);
		arrayCards = cards;
	}

	public void onTurnLost(int turn, int round) {
		roundsView.setText(Integer.toString(round));
		//Cambiar Icono Jugador
		PlayerDataView p = players.getFirst();
		players.removeFirst();
		players.addLast(p);
		p.plays(false);
		players.getFirst().plays(true);
	}

	public void onCardPicked(int card, boolean discovered) {
		// Darle la vuelta a la carta
		cards.get(card).flip();
	}
	
	public void onCardUnpicked(int card) {
		cards.get(card).flip();
	}

	public void onActionOccurred(JSONArray players, String s) {
		infoAction.setText(s);
	}

	public void onFinished(boolean winner, JSONArray players) {
		// mostrar resultados y opcion de repetir o salir al menu inicial
		if(thread != null) thread.interrupt();
		SwingUtilities.invokeLater(new Runnable() {
			public void run(){
				try {
					TimeUnit.MILLISECONDS.sleep(1500);
				} catch (InterruptedException e) {
					return;
				}
				_mainPanel.addEndGamePanel(players, winner, colorMap);
				_mainPanel.changeView(MainView._endGameGUI);
			}
		});
	}
	
	
	private class CardView extends JButton implements ActionListener{
	
		//Imagenes
		private Image _backCardIMG = new ImageIcon("icons/dorso1.png").getImage();
		private Image _imgCard;			//Imagen de la parte frontal de la carta
		private boolean discovered = false;	
		
		private Dimension _dimCard;
		private int i;		//Posicion de la carta en el arrayList
		
		public CardView(Dimension dim, JSONObject card, int i ) {
			_dimCard = dim;
			this.i = i;
			String path = "icons/cards/"+ card.getString("name") + ".png";
			_imgCard= new ImageIcon(path).getImage();
			discovered = card.getBoolean("discovered");
			
			this.setMinimumSize(new Dimension(_dimCard.width,_dimCard.height));
			this.setMaximumSize(_dimCard);
			this.setPreferredSize(new Dimension(_dimCard.width,_dimCard.height));
			this.setBackground(Color.WHITE);
			if(discovered) this.setIcon(new ImageIcon(Tools.resizeImage(_imgCard, dim)));
			else this.setIcon(new ImageIcon(Tools.resizeImage(_backCardIMG, dim)));
			//MainView.removeShapeButton(this);
			//MainView.setRollOverEffect((JButton)this,_backCardIMG,new Pixels(_dimCard.width,_dimCard.height));
			this.addActionListener(CardView.this);
		}
		
		private void flip() {
			discovered = !discovered;
			if(discovered) {
				this.setIcon(new ImageIcon(Tools.resizeImage(_imgCard, _dimCard)));
			}
			else {
				this.setIcon(new ImageIcon(Tools.resizeImage(_backCardIMG,_dimCard)));
			}	
		}
		
		public void actionPerformed(ActionEvent e) {	
			thread = new Thread() {
				public void run() {
					try {
						_mainPanel.pick(i);
					} catch (Exception e) {
						infoAction.setText(e.getMessage());
					}
				}
			};
			thread.start();
		}
		
	}
	
	// M�TODOS RED
	
	public void connectToServer() {
		gameClient = new GameClient();
	}
}
	
	
	
