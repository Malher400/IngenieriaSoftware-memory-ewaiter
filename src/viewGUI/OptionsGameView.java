package viewGUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.MatteBorder;


public class OptionsGameView extends JPanel implements ActionListener {
	
	// Info que luego pasara al controller
	private int dim = 6;
	private String gm = "CLASSIC";
	private int num_players = 1;
	
	// Componentes
	private JButton continueButton;
	private JButton backButton;
	private JButton upSpinner;
	private JButton downSpinner;
	private List<JButton> sizeButtons = new ArrayList<JButton>();
	private JTextField indicator;
	private JComboBox<String> gameModeOption;

	// Modos de Juego
	private String[] valores = { "CLASSIC", "ARCADE", "ONLINE" };
	
	// Imagenes
	private Image _NOPImg = new ImageIcon("icons/game_config/number_of_players_label.png").getImage();
	private Image _BDImg = new ImageIcon("icons/game_config/board_dimension_label.png").getImage();
	private Image _GMImg = new ImageIcon("icons/game_config/game_mode_label.png").getImage();
	private Image _continueButtonImg = new ImageIcon("icons/game_config/continue_button.png").getImage();
	private Image _backButtonImg = new ImageIcon("icons/go_back_button.png").getImage();
	private Image _upButtonImg =  new ImageIcon("icons/game_config/up_select_spinner_numPlayers.png").getImage();
	private Image _downButtonImg = new ImageIcon("icons/game_config/down_select_spinner_numPlayers.png").getImage();

	// Dimensiones
	private Dimension dimBorder = Tools.pixels(100,20);	//Distancia del panel a lo margenes de la ventana: 20-UP/DOWN y 100-LEFT/RIGHT

	// Panel principal
	private MainView _mainPanel;

	// Colores
	Color _backGround = new Color(172, 255, 196);
	Color backGroundPanel = new Color(227,255,236);
	//Color backGroundPanel = Color.WHITE;
	
	//Fuente
	Font arcadeFont;

	public OptionsGameView(MainView mainPanel) {
		_mainPanel = mainPanel;
		initGUI();
	}

	private void initGUI() {
		num_players = 1;
		this.setLayout(new BorderLayout());
		this.setLayout(new BorderLayout(0, 0));

		JPanel north = northPanel(); // Contiene NUMBER OF PLAYERS																									
		JPanel center = centralPanel(); // Contiene BOARD DIMENSION
		JPanel south = southPanel(); // Contiene GAME MODE y CONTINUE

		add(north, BorderLayout.NORTH);
		add(center, BorderLayout.CENTER);
		add(south, BorderLayout.SOUTH);

		gameModeOption.setFont(MainView.arcadeFont);
		gameModeOption.setForeground(Color.BLACK);
		indicator.setFont(MainView.arcadeFont);
		indicator.setForeground(Color.BLACK);
	}
	
	private JPanel northPanel() {
		JPanel northPanel = new JPanel();
		northPanel.setLayout(new BorderLayout());	
		northPanel.setBorder(new MatteBorder(dimBorder.height + 30, dimBorder.width, dimBorder.height, dimBorder.width,backGroundPanel));
		northPanel.add(numberOfPlayersPanel());			//A�adimos el panel con etiqueta, JPSinner etc
		return northPanel;
	}
	
	private JPanel numberOfPlayersPanel() {
		JPanel numberOfPlayers = new JPanel();
		numberOfPlayers.setBackground(_backGround);
		numberOfPlayers.setLayout(new BoxLayout(numberOfPlayers, BoxLayout.X_AXIS));

		//Etiqueta NUMBER OF PLAYERS
		JLabel lblNOP = new JLabel();
		lblNOP.setMinimumSize(Tools.pixels(1390, 125));
		lblNOP.setMaximumSize(Tools.pixels(1390, 125));
		lblNOP.setPreferredSize(Tools.pixels(1390, 125));
		lblNOP.setBackground(Color.BLACK);
		lblNOP.setIcon(new ImageIcon(Tools.resizeImage(_NOPImg, lblNOP.getPreferredSize())));
		numberOfPlayers.add(lblNOP);
		
		//A�adimos separador y el spinner
		numberOfPlayers.add(createSeparator(Tools.pixels(50, 125)));
		numberOfPlayers.add(spinner());
		numberOfPlayers.add(createSeparator(Tools.pixels(170, 31)));

		return numberOfPlayers;
	}
	
	
	private JPanel centralPanel() {	//Panel central. Contiene GAME BOARD DIMENSION

		JPanel center = new JPanel();
		center.setLayout(new BorderLayout(0, 0));
		center.setBorder(new MatteBorder(0,dimBorder.width,0,dimBorder.width, backGroundPanel));
		center.add(boardDimensionPanel(),BorderLayout.CENTER);
		return center;
	}
	
	private JPanel boardDimensionPanel() {
	JPanel boardDimensionPanel = new JPanel();
		boardDimensionPanel.setLayout(new BorderLayout());
	
		//Etiqueta
		JLabel bdLbl = new JLabel();
		bdLbl.setMinimumSize(Tools.pixels(1720,100));
		bdLbl.setMaximumSize(Tools.pixels(1720,100));
		bdLbl.setPreferredSize(Tools.pixels(1720,100));
		bdLbl.setIcon(new ImageIcon(Tools.resizeImage(_BDImg, bdLbl.getPreferredSize())));
		boardDimensionPanel.add(bdLbl, BorderLayout.NORTH);
		
		//Botones: panel con GridBagLayout
		JPanel buttons = new JPanel();
		buttons.setLayout(new GridBagLayout());
		buttons.setBackground(_backGround);
		initialiteButtons();
		//Disposicion de los botones
		
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.weighty = 1.0;		//Separacion entre columnas y filas
		constraints.weightx=  1.0;
		int k =0;
		for(int i=0;i<2;i++) {			//Primera dos filas de botones
			for(int j=0;j<5;j++) {
				constraints.gridx=j;		//Columna j
				constraints.gridy = i;		//fila i
				constraints.gridwidth = 1;		//Ocupa una columna
				constraints.gridheight=1;		//Ocupa una fila
				buttons.add(sizeButtons.get(k),constraints);
				k++;
			}
		}
		
		for(int i=1;i<=3;i++) {
			constraints.gridx=i;		//Columna i
			constraints.gridy = 2;		//fila 2
			constraints.gridwidth = 1;		//Ocupa una columna
			constraints.gridheight=1;		//Ocupa una fila
			buttons.add(sizeButtons.get(k),constraints);
			k++;
		}
		
		
		boardDimensionPanel.add(buttons);
		
		return boardDimensionPanel;
	}
	
	private JButton createSizeButton(int n) {			//Crea los botones de dimension del tablero
		String path = "icons/game_config/dimension_button_"+n+"x4.png";
		if(n == 6) path = "icons/game_config/dimension_button_"+6+"x4_selected.png";
		Image nxn = new ImageIcon(path).getImage();
		JButton button = new JButton();
		button.setMinimumSize(Tools.pixels(195,80));
		button.setPreferredSize(Tools.pixels(205,90));
		button.setMaximumSize(Tools.pixels(205,90));
		button.setVerticalAlignment(SwingConstants.TOP);		//Alieneacion Imagen y efectos
		button.setHorizontalAlignment(SwingConstants.CENTER);
		button.setBackground(_backGround);
		button.setIcon(new ImageIcon(Tools.resizeImage(nxn, button.getMinimumSize())));
		Tools.removeShapeButton(button);
		setRollOverEffect(button,nxn, Tools.pixels(200,85));
		button.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				int i=4;
				for(JButton bi : sizeButtons) {
					String path = "icons/game_config/dimension_button_"+i+"x4.png";
					Image nxn = new ImageIcon(path).getImage();
					if(bi != button) {
						bi.setIcon(new ImageIcon(Tools.resizeImage(nxn, bi.getMinimumSize())));
						setRollOverEffect(bi, nxn, Tools.pixels(200,85));
					}
					else {
						path = "icons/game_config/dimension_button_"+i+"x4_selected.png";
						nxn = new ImageIcon(path).getImage();
						button.setIcon(new ImageIcon(Tools.resizeImage(nxn, button.getMinimumSize())));
						setRollOverEffect(button, nxn, Tools.pixels(200,85));
					}
					i++;
				}
				dim = n;
				
			}});
		return button;
	}

	private JPanel southPanel() {		//Panel que contien GAME MODE y botones CONTINUE y BACK
		JPanel southPanel = new JPanel();
		southPanel.setLayout(new BorderLayout(0, 0));
		southPanel.setBackground(backGroundPanel);
		
		//GAME MODE 
		JPanel gameMode = gameModePanel();
		southPanel.add(gameMode,BorderLayout.NORTH);
		
		//Continue
		JPanel continuePanel = new JPanel();
		continuePanel.setBackground(backGroundPanel);
		continuePanel.setBorder(new MatteBorder(dimBorder.height,0,dimBorder.height + 20,dimBorder.width, backGroundPanel));
		
		continueButton = new JButton();
		continueButton.setAlignmentX(CENTER_ALIGNMENT);
		continueButton.setMinimumSize(Tools.pixels(384,108));
		continueButton.setMaximumSize(Tools.pixels(404,118));
		continueButton.setPreferredSize(Tools.pixels(404,118));
		continueButton.addActionListener(this);
		continueButton.setBackground(backGroundPanel);
		
		setRollOverEffect(continueButton, _continueButtonImg, Tools.pixels(394,113));
		Tools.removeShapeButton(continueButton);
		continueButton.setIcon(new ImageIcon(Tools.resizeImage(_continueButtonImg,continueButton.getMinimumSize())));
		continuePanel.add(continueButton);
		southPanel.add(continuePanel,BorderLayout.CENTER);
		
		//Back
		JPanel backPanel = new JPanel();
		backPanel.setBorder(new MatteBorder(0,dimBorder.width,dimBorder.height + 20,0, backGroundPanel));
		backPanel.setBackground(backGroundPanel);
		backPanel.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);		//Invierte la orientacion
		backPanel.setLayout(new BoxLayout(backPanel,BoxLayout.LINE_AXIS));			//De abajo a arriba
		backButton = backButton();
		backPanel.add(backButton);
		southPanel.add(backPanel,BorderLayout.WEST);
		
		return southPanel;
	}

	private JPanel gameModePanel() { // Crea el panel de GAMEMODE
		JPanel gameMode = new JPanel();
		gameMode.setLayout(new BoxLayout(gameMode,BoxLayout.X_AXIS));
		gameMode.setBorder(new MatteBorder(dimBorder.height,dimBorder.width,dimBorder.height,dimBorder.width, backGroundPanel));
		gameMode.setBackground(_backGround);
		
		//GAME MODE
		JLabel gameModeLbl = new JLabel ();
		gameModeLbl.setMinimumSize( Tools.pixels(810, 125));
		gameModeLbl.setMaximumSize(Tools.pixels(810, 125));
		gameModeLbl.setPreferredSize(Tools.pixels(810, 125));
		gameModeLbl.setIcon(new ImageIcon(Tools.resizeImage(_GMImg, gameModeLbl.getPreferredSize())));
		gameMode.add(gameModeLbl);
		gameMode.add(createSeparator(Tools.pixels(50,0)));
		
		//JCombo
		gameModeOption = new JComboBox<String>(valores);
		((JLabel) gameModeOption.getRenderer()).setHorizontalAlignment(SwingConstants.CENTER);
		gameModeOption.setMinimumSize(Tools.pixels(810, 65));
		gameModeOption.setMaximumSize(Tools.pixels(810, 65));
		gameModeOption.setPreferredSize(Tools.pixels(810, 65));
		gameModeOption.addActionListener(this);
		
		gameMode.add(gameModeOption);
		return gameMode;
	}

	private JButton backButton() {
		JButton back = new JButton();
		back.setMinimumSize(Tools.pixels(85,90));
		back.setMaximumSize(Tools.pixels(95,95));
		back.setPreferredSize(Tools.pixels(95,95));
		back.setBackground(backGroundPanel);
		back.setAlignmentY(BOTTOM_ALIGNMENT);
		back.setVerticalAlignment(SwingConstants.BOTTOM);		//Alieneacion Imagen y efectos
		back.addActionListener(this);
		back.setIcon(new ImageIcon(Tools.resizeImage(_backButtonImg, back.getMinimumSize())));
		setRollOverEffect(back,_backButtonImg ,Tools.pixels(87,92));
		Tools.removeShapeButton(back);
		return back;
	}
	

	private JPanel spinner() {
		JPanel spinner = new JPanel();
		spinner.setBackground(_backGround);
		spinner.setLayout(new BoxLayout(spinner, BoxLayout.X_AXIS));
		spinner.setMinimumSize(Tools.pixels(125,90));
		spinner.setMaximumSize(Tools.pixels(125,90));
		spinner.setPreferredSize(Tools.pixels(125,90));
		
		//Indicador
		indicator= new JTextField();
		indicator.setMinimumSize(Tools.pixels(80, 80));
		indicator.setMaximumSize(Tools.pixels(80, 80));
		indicator.setPreferredSize(Tools.pixels(80, 80));
		indicator.setText("1");
		indicator.setHorizontalAlignment(JTextField.CENTER);  //Centra el texto del JTextField
		spinner.add(indicator);
		
		// Botones UP & DOWM
		JPanel buttons = new JPanel();		//Panel auxiliar donde van los botones 
		buttons.setLayout(new BoxLayout(buttons,BoxLayout.Y_AXIS));
		
		upSpinner = new JButton();
		upSpinner.setMinimumSize(Tools.pixels(40,40));
		upSpinner.setPreferredSize(Tools.pixels(45,45));
		upSpinner.setMaximumSize(Tools.pixels(45,45));
		upSpinner.addActionListener(this);
		upSpinner.setBackground(_backGround);
		upSpinner.setIcon(new ImageIcon(Tools.resizeImage(_upButtonImg, upSpinner.getMinimumSize())));
		Tools.removeShapeButton(upSpinner);
		setRollOverEffect(upSpinner,_upButtonImg,upSpinner.getMaximumSize());
		
		downSpinner = new JButton();
		downSpinner.setMinimumSize(Tools.pixels(40,40));
		downSpinner.setPreferredSize(Tools.pixels(45,45));
		downSpinner.setMaximumSize(Tools.pixels(45,45));
		downSpinner.addActionListener(this);
		downSpinner.setBackground(_backGround);
		downSpinner.setIcon(new ImageIcon(Tools.resizeImage(_downButtonImg, downSpinner.getMinimumSize())));
		Tools.removeShapeButton(downSpinner);
		setRollOverEffect(downSpinner,_downButtonImg,downSpinner.getMaximumSize());
		
		
		buttons.add(upSpinner);
		buttons.add(downSpinner);
		spinner.add(buttons);
		
		return spinner;

	}
	
	private Component createSeparator(Dimension d) {			//Crea un separador
		Component separator = Box.createHorizontalStrut(20);
		separator.setMinimumSize(d);
		separator.setMaximumSize(d);
		separator.setPreferredSize(d);
		return separator;
	}
	
	private void setRollOverEffect(JButton b, Image img, Dimension dim) {		//Efecto al pasar por encima raton
		Image aux =img.getScaledInstance(dim.width,dim.height,java.awt.Image.SCALE_SMOOTH);
		b.setRolloverIcon(new ImageIcon(aux));
	}
	
	private void initialiteButtons() {
		for(int i=4;i<=16;i++) {
			sizeButtons.add(createSizeButton(i));
		}
	}
	
	protected void resetPanel() {
		gm = "CLASSIC";
		initGUI();
	}
	
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == backButton) {
			_mainPanel.changeView(MainView._starterMenuGUI);
		} 
		else if (e.getSource() == continueButton) {
			_mainPanel.setMenu1(num_players, dim, gm);
			_mainPanel.addPlayersPanel(num_players);
			_mainPanel.changeView(MainView._playerNamesGUI);
		}
		else if(e.getSource()==upSpinner) {
			int numPlayers = Integer.parseInt(indicator.getText());
			if(1<=numPlayers && numPlayers<4) {
				indicator.setText(Integer.toString(++numPlayers));
				num_players = numPlayers;
			}
		}
		else if(e.getSource()==downSpinner) {
			int numPlayers = Integer.parseInt(indicator.getText());
			if(1<numPlayers && numPlayers<=4) {
				indicator.setText(Integer.toString(--numPlayers));
				num_players = numPlayers;
			}
		}
		else if(e.getSource() == gameModeOption) {
			gm = (String) gameModeOption.getSelectedItem();
		}
	
	}

}
