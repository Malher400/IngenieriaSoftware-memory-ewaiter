package viewGUI;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.MatteBorder;

import org.json.JSONArray;


public class EndGameView extends JPanel implements ActionListener{

	//Componentes
	JButton newGameButton;
	JButton resetGameButton;
	JButton goMenuButton;
	//List<Color> colors= new ArrayList<>();
	HashMap<String, Color> colorMap;
	
	//Lista con la info de cada jugador
	JSONArray _playersList;
	boolean _winner; //Posicion del ganador en el array
	
	//Imganes
	private Image newGameImage= new ImageIcon("icons/initial_menu/new_game_button.png").getImage();
	private Image exitImage = new ImageIcon("icons/initial_menu/exit_button.png").getImage();
	private Image resetImage = new ImageIcon("icons/reset_button.png").getImage();

	//Dimensiones de los botones segun la pantalla de cada jugador
	private Dimension dimButton = Tools.pixels(384 + 20 ,108 + 10);
	private Dimension imgButtons = Tools.pixels(384,108);
	private Dimension dimBorder = Tools.pixels(100,10); // Borders	
	
	//Panel principal
	MainView _mainPanel;
	
	public EndGameView(MainView mainPanel, JSONArray players, boolean winner, HashMap<String, Color> colorMap) {
		_mainPanel = mainPanel;
		_playersList = players; 
		_winner = winner;
		this.colorMap = colorMap;
		initGUI();
	}
	
	private void initGUI() {
		this.setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
		this.setBackground(Color.WHITE);
		this.setBorder(new MatteBorder(dimBorder.height,0, dimBorder.height, 0,Color.WHITE));
		this.setAlignmentX(CENTER_ALIGNMENT);
		
		//Separador Margen Superior
		this.add(Box.createRigidArea(Tools.pixels(0,100)));
				
		//Etiquetas
		boolean winnerLbl;
		for(int i = 0; i < _playersList.length(); i++) {
			winnerLbl = _winner &&(i==0);
			String name = _playersList.getJSONObject(i).getString("name");
			this.add(dataPlayer(name, _playersList.getJSONObject(i).getInt("points"), winnerLbl, colorMap.get(name)));
		}

		this.add(Box.createRigidArea(Tools.pixels(0,50)));
		
		//Botones 
		JPanel panelButtons= new JPanel();
		panelButtons.setBackground(Color.WHITE);
		panelButtons.setLayout(new FlowLayout());
		panelButtons.setAlignmentX(CENTER_ALIGNMENT);
		this.add(panelButtons);
		
	
		// NEW GAME BUTTON
		newGameButton = new JButton();
		newGameButton.setActionCommand(MainView._gameModeMenuGUI);
		newGameButton.addActionListener(this);
																	
		newGameButton.setAlignmentX(Component.CENTER_ALIGNMENT);	//Posicion y tamaños respecto el panel	
		newGameButton.setMinimumSize(dimButton);
		newGameButton.setPreferredSize(dimButton);
		newGameButton.setMaximumSize(dimButton);
																
		newGameButton.setVerticalAlignment(SwingConstants.TOP);		//Alieneacion Imagen y efectos
		newGameButton.setHorizontalAlignment(SwingConstants.RIGHT);
		newGameButton.setBackground(Color.WHITE);
		newGameButton.setIcon(new ImageIcon(Tools.resizeImage(newGameImage,imgButtons)));
		Tools.setRollOverEffect(newGameButton,newGameImage,dimButton);
		Tools.removeShapeButton(newGameButton);
		panelButtons.add(newGameButton);
		panelButtons.add(Box.createRigidArea(new Dimension(10,0)));
		
		// RESET BUTTON
		resetGameButton = new JButton();
		resetGameButton.addActionListener(this);
		
		resetGameButton.setAlignmentX(Component.CENTER_ALIGNMENT);		//Alineacion en el BoxLayout
		resetGameButton.setMinimumSize(dimButton);
		resetGameButton.setPreferredSize(dimButton);
		resetGameButton.setMaximumSize(dimButton);
		
		resetGameButton.setVerticalAlignment(SwingConstants.TOP);		//Alieneacion Imagen y efectos
		resetGameButton.setHorizontalAlignment(SwingConstants.RIGHT);
		resetGameButton.setBackground(Color.WHITE);
		resetGameButton.setIcon(new ImageIcon(Tools.resizeImage(resetImage,imgButtons)));
		Tools.setRollOverEffect(resetGameButton,resetImage,dimButton);
		Tools.removeShapeButton(resetGameButton);
		panelButtons.add(resetGameButton);
		panelButtons.add(Box.createRigidArea(new Dimension(10,0)));
	
	
		//MENU BUTTON
		goMenuButton = new JButton();
		goMenuButton.addActionListener(this);
		
		goMenuButton.setAlignmentX(Component.CENTER_ALIGNMENT);		//Alineacion en el BoxLayout
		goMenuButton.setMinimumSize(dimButton);
		goMenuButton.setPreferredSize(dimButton);
		goMenuButton.setMaximumSize(dimButton);

		goMenuButton.setVerticalAlignment(SwingConstants.TOP);		//Alieneacion Imagen y efectos
		goMenuButton.setHorizontalAlignment(SwingConstants.RIGHT);
		goMenuButton.setBackground(Color.WHITE);
		goMenuButton.setIcon(new ImageIcon(Tools.resizeImage(exitImage,imgButtons)));
		Tools.setRollOverEffect(goMenuButton,exitImage,dimButton);
		Tools.removeShapeButton(goMenuButton);
		panelButtons.add(goMenuButton);
		panelButtons.add(Box.createRigidArea(new Dimension(10,0)));
		
	}
	
	private JPanel dataPlayer(String name, int points, boolean winner, Color color){
		JPanel player= new JPanel();
		player.setBackground(Color.WHITE);

		//Datos
		JLabel dataLbl = new JLabel(name + "  "+ points+"  Points");
		player.add(dataLbl);
		player.add(Box.createRigidArea(Tools.pixels(50,0)));
		
		//Ganador
		JLabel winnerLbl = new JLabel("WINS!");
		winnerLbl.setVisible(winner);
		player.add(winnerLbl);
		
		//Colores y fuente
		dataLbl.setFont(MainView.playerNamesFont);
		dataLbl.setForeground(color);
		winnerLbl.setForeground(Color.ORANGE);
		winnerLbl.setFont(MainView.playerNamesFont);
		
		return player;
	}
	
	public void actionPerformed(ActionEvent e) {
		if(newGameButton == e.getSource()) {
			_mainPanel.changeView(MainView._optionsGame);
		}
		else if(resetGameButton == e.getSource()) {
			_mainPanel.reset();
		}
		else {
			_mainPanel.changeView(MainView._starterMenuGUI);
		}
		
	}
}
