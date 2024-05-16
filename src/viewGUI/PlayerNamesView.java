package viewGUI;

import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.SwingConstants;
import javax.swing.border.MatteBorder;
import javax.swing.JButton;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;
import java.awt.event.ActionEvent;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JTextField;

public class PlayerNamesView extends JPanel implements ActionListener {
	
	// Attributes:
	private String[] name;
	private String[] diff;	
	private int num_players;
	
	// Names & levels :
	private List<JTextField> names; // Lista de JTextfield donde se escribe el nombre
	private List<LevelSelector> levelsSelected;	// Niveles que se han seleccionado
	
	// go_back y start:
	private JButton start, go_back;
	
	// Images:
	private Image playerNamesImg = new ImageIcon("icons/game_config/player_names_title_label.png").getImage();
	private Image startButtonImg = new ImageIcon("icons/game_config/start_button.png").getImage();
	private Image backButtonImg = new ImageIcon("icons/go_back_button.png").getImage();
	
	// Dimensions: 
	private Dimension dimStartButton = Tools.pixels(404,118);
	private Dimension dimBackButton = Tools.pixels(118,118);
	private Dimension dimBorder = Tools.pixels(100,10); // Borders	
	
	// Colors:
	Color backGroundPanel = new Color(227,255,255);
	//Color backGroundPanel = Color.WHITE;
	
	// Main Panel:
	private MainView _mainPanel;
		
	// Constructor:
	public PlayerNamesView(MainView mainPanel, int num_players) {
		this.num_players = num_players;
		_mainPanel = mainPanel;
		initGUI();
		setNamesFont();	// Cambia la fuente y limita el numero de caracteres
	}
	
	private void initGUI() {
		this.setLayout(new BorderLayout());
		
		//Panel norte
		this.add(northPanel(),BorderLayout.NORTH);
		
		//Panel central
		JPanel center = new JPanel();
		center.setBackground(backGroundPanel);
		initialiteNames(4);					//Inicializa el array de JTextFields
		initialiteLevelSelector(4);
		center.setLayout(new BoxLayout(center,BoxLayout.Y_AXIS));

		for(int i=1;i<=num_players;i++) {				//Anade el rectangulo con etiqueta, JTextfield y spinner
			center.add(playerN(i));
		}
		this.add(center,BorderLayout.CENTER);
		
		// Panel sur
		this.add(southPanel(),BorderLayout.PAGE_END);
	}

	private JPanel northPanel() {			//Contiene la etiqueta de PLAYER NAMES
		JPanel north = new JPanel();
		north.setBackground(backGroundPanel);
		//Tama�os
		north.setLayout(new BoxLayout(north,BoxLayout.X_AXIS));
		north.setMinimumSize(Tools.pixels(MainView._screenSize.width,176));
		north.setMaximumSize(Tools.pixels(MainView._screenSize.width,176));
		north.setPreferredSize(Tools.pixels(MainView._screenSize.width,176));
		north.setBorder(new MatteBorder(40,dimBorder.width, dimBorder.height, dimBorder.width,backGroundPanel));
		//Etiqueta
		JLabel playerNamesLbl = new JLabel();
		playerNamesLbl.setIcon(new ImageIcon(Tools.resizeImage(playerNamesImg, Tools.pixels(900,176))));
		playerNamesLbl.setMinimumSize(Tools.pixels(900,176));
		playerNamesLbl.setMaximumSize(Tools.pixels(900,176));
		playerNamesLbl.setPreferredSize(Tools.pixels(900,176));
		north.add(playerNamesLbl);
		
		return north;
	}
	
	private JPanel southPanel() {
		JPanel south = new JPanel();
		south.setBackground(backGroundPanel);
		go_back = new JButton();
		go_back.setMinimumSize(Tools.pixels(88,88));
		go_back.setMaximumSize(dimBackButton);
		go_back.setPreferredSize(dimBackButton);
		go_back.addActionListener(this);
		go_back.setBackground(backGroundPanel);
		go_back.setVerticalAlignment(SwingConstants.CENTER);
		go_back.setHorizontalAlignment(SwingConstants.RIGHT);
		go_back.setIcon(new ImageIcon(Tools.resizeImage(backButtonImg,go_back.getMinimumSize())));
		setRollOverEffect(go_back, backButtonImg, Tools.pixels(95,95));
		Tools.removeShapeButton(go_back);
		south.add(go_back);
		south.setBorder(new MatteBorder(0,dimBorder.width - 10,dimBorder.width / 4,0, backGroundPanel));
		south.setLayout(new BoxLayout(south,BoxLayout.LINE_AXIS));
		
		south.add(startButtonPanel());			// Anade el boton start
		
		return south;
	}
	
	private JPanel playerN(int n) {				//Contiene la etiqueta de Player N, JtextField y el selector de nivel
		JPanel playerN = new JPanel();
		playerN.setLayout(new BoxLayout(playerN,BoxLayout.X_AXIS));
		playerN.setBackground(backGroundPanel);
		playerN.setBorder(new MatteBorder(dimBorder.height,0, dimBorder.height, 0,backGroundPanel));
		//etiqueta
		String path = "icons/game_config/player"+n+"_label.png";
		Image pN_img = new ImageIcon(path).getImage();
		JLabel pN_lbl= new JLabel();
		pN_lbl.setMinimumSize(Tools.pixels(640,125));
		pN_lbl.setMaximumSize(Tools.pixels(640,125));
		pN_lbl.setPreferredSize(Tools.pixels(640,125));
		pN_lbl.setIcon(new ImageIcon(Tools.resizeImage(pN_img, pN_lbl.getPreferredSize())));
		playerN.add(pN_lbl);										//Anade etiqueta
		playerN.add(Box.createRigidArea(Tools.pixels(40,0)));			//Anade separador
		//JTextField
		names.get(n-1).setMinimumSize(Tools.pixels(740,125));
		names.get(n-1).setMaximumSize(Tools.pixels(740,125));
		names.get(n-1).setPreferredSize(Tools.pixels(740,125));
		playerN.add(names.get(n-1));								//Anade JTextField
		playerN.add(Box.createRigidArea(Tools.pixels(100,0)));
		playerN.add(levelsSelected.get(n-1));						//Anadimos spinner
		
		return playerN;
	}
	
	private JPanel startButtonPanel() {			//Panel con el botón START
		
		JPanel startButton = new JPanel();
		startButton.setLayout(new FlowLayout());
		startButton.setBackground(backGroundPanel);
		startButton.setAlignmentX(CENTER_ALIGNMENT);
		start = new JButton();
		start.setMinimumSize(Tools.pixels(370,108));
		start.setMaximumSize(dimStartButton);
		start.setPreferredSize(dimStartButton);
		start.addActionListener(this);
		start.setBackground(backGroundPanel);
		start.setIcon(new ImageIcon(Tools.resizeImage(startButtonImg,start.getMinimumSize())));
		setRollOverEffect(start, startButtonImg, Tools.pixels(394,113));
		Tools.removeShapeButton(start);
		startButton.add(start);
		startButton.setBorder(new MatteBorder(0,0,dimBorder.width - 30,dimBorder.width + 40, backGroundPanel));
		
		return startButton;
	}
	
	private String[] getNameList() {
		name = new String[num_players];
		for (int i = 0; i < name.length; ++i)
			name[i] = names.get(i).getText();
		return name;
	}

	private String[] getDiffList() {
		diff = new String[num_players];
		for (int i = 0; i < diff.length; ++i)
			diff[i] = levelsSelected.get(i).getDiffSelected();
		return diff;
	}

	public void actionPerformed(ActionEvent e) {
		
		if (e.getSource() == go_back) 
			_mainPanel.changeView(MainView._optionsGame);
		
		else if (e.getSource() == start) {
			try {
				_mainPanel.setMenu2(getNameList(), getDiffList());
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			_mainPanel.addBoardPanel();
			_mainPanel.changeView(MainView._boardGUI);
			new Thread() {
				public void run() {
					try {
					  _mainPanel.startGame();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}.start();
		}			
	}
	
	private class LevelSelector extends JPanel implements ActionListener {		//Selector de nivel(actua como spinner)
		//Componentes
		private JTextField indicator;
		private JButton up, down;
		
		//Imagenes
		Image upImg = new ImageIcon("icons/game_config/up_select_spinner_difficulty_bots.png").getImage();
		Image downImg = new ImageIcon("icons/game_config/down_select_spinner_difficulty_bots.png").getImage();
		
		public LevelSelector() {
			this.setBackground(backGroundPanel);
			this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
			this.setMinimumSize(Tools.pixels(187,125));
			this.setMaximumSize(Tools.pixels(187,125));
			this.setPreferredSize(Tools.pixels(187,125));
			
			//Indicador
			indicator= new JTextField();
			indicator.setMinimumSize(Tools.pixels(125, 125));
			indicator.setMaximumSize(Tools.pixels(125, 125));
			indicator.setPreferredSize(Tools.pixels(125,125));
			indicator.setText("Human");
			indicator.setHorizontalAlignment(JTextField.CENTER);  //Centra el texto del JTextField
			indicator.setEditable(false);
			this.add(indicator);
			
			// Botones UP & DOWM
			JPanel buttons = new JPanel();		//Panel auxiliar donde van los botones 
			buttons.setLayout(new BoxLayout(buttons,BoxLayout.Y_AXIS));
			
			up = new JButton();
			up.setMinimumSize(Tools.pixels(40,40));
			up.setPreferredSize(Tools.pixels(48,48));
			up.setMaximumSize(Tools.pixels(62,62));
			up.addActionListener(this);
			up.setBackground(backGroundPanel);
			up.setVerticalAlignment(SwingConstants.TOP);
			up.setHorizontalAlignment(SwingConstants.RIGHT);
			up.setIcon(new ImageIcon(Tools.resizeImage(upImg, up.getMinimumSize())));
			Tools.removeShapeButton(up);
			setRollOverEffect(up,upImg,up.getPreferredSize());
			
			down = new JButton();
			down.setMinimumSize(Tools.pixels(40,40));
			down.setPreferredSize(Tools.pixels(50,50));
			down.setMaximumSize(Tools.pixels(62,62));
			down.addActionListener(this);
			down.setBackground(backGroundPanel);
			down.setVerticalAlignment(SwingConstants.TOP);
			down.setHorizontalAlignment(SwingConstants.RIGHT);
			down.setIcon(new ImageIcon(Tools.resizeImage(downImg, down.getMinimumSize())));
			Tools.removeShapeButton(down);
			setRollOverEffect(down,downImg,down.getPreferredSize());
			
			buttons.add(up);
			buttons.add(down);
			this.add(buttons);
			
			indicator.setFont(MainView.levelBots);
			
		}
		
		
		public void actionPerformed(ActionEvent e) {
			String difficulty = indicator.getText();
			if (e.getSource() == up) {
				if(difficulty.equals("Human"))
					indicator.setText("Easy");
				else if (difficulty.equals("Easy")) 
					indicator.setText("Normal");
				else if (difficulty.equals("Normal"))
					indicator.setText("Hard");
			}

			else if (e.getSource() == down) {
				if (difficulty.equals("Hard"))
					indicator.setText("Normal");
				else if (difficulty.equals("Normal"))
					indicator.setText("Easy");
				else if (difficulty.equals("Easy"))
					indicator.setText("Human");
			}
		}
		private String getDiffSelected() {
			return indicator.getText();
		}
		
	}

	//Inicializan listas
	private void initialiteLevelSelector(int numPlayers) {
		levelsSelected= new ArrayList<LevelSelector>();
		for(int i=0;i<numPlayers;i++) {
			levelsSelected.add(new LevelSelector());
		}
	}
	
	private void initialiteNames(int numPlayers) {				//Inicializa los JTextField para que los jugadores escriban su nombre
		names= new ArrayList<JTextField>();
		for(int i=0;i<numPlayers;i++) {
			names.add(new JTextField("Player" + (i + 1)));
			
		}
	}
	
	private void setNamesFont() {		//Cambia la fuente y limita el numero de caracteres
		for(JTextField name: names) {
			name.setFont(MainView.playerNamesFont);
			name.setForeground(Color.BLACK);
			name.setHorizontalAlignment(SwingConstants.CENTER);
			name.addKeyListener(new KeyListener() {
				
				public void keyTyped(KeyEvent e) {
					if(name.getText().length()==7)		//Limite el nombre a 7 caracteres
						e.consume();
				}
				public void keyPressed(KeyEvent e) {}
				public void keyReleased(KeyEvent e) {}});
		}
	}
	
	
	private void setRollOverEffect(JButton b, Image img, Dimension dim) {		//Efecto al pasar por encima raton
		Image aux =img.getScaledInstance(dim.width,dim.height,java.awt.Image.SCALE_SMOOTH);
		b.setRolloverIcon(new ImageIcon(aux));
	}
	
}