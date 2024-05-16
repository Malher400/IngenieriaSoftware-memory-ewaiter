package viewGUI;

import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Image;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.awt.event.ActionEvent;
import javax.swing.SwingConstants;


public class StarterMenuView extends JPanel implements ActionListener{
	
	//JFileChooser
	private JFileChooser file_chooser= new JFileChooser();
	
	//Botones
	private MainView _mainPanel;
	private JButton newGameButton;
	private JButton loadButton;
	private JButton howToPlayButton;
	private JButton exitButton;
	private JLabel memoryTitle = new JLabel();
	
	//Imganes
	private Image newGameImage= new ImageIcon("icons/initial_menu/new_game_button.png").getImage();
	private Image howToPlayImage = new ImageIcon("icons/initial_menu/how_to_play_button.png").getImage();
	private Image exitImage = new ImageIcon("icons/initial_menu/exit_button.png").getImage();
	private Image loadImage = new ImageIcon("icons/initial_menu/load_button.png").getImage();
	private Image memoryImage = new ImageIcon("icons/memory_title.png").getImage();
	
	//Dimensiones de los botones segun la pantalla de cada jugador
	private Dimension dimButton = Tools.pixels(400 + 50 ,115 + 10);
	private Dimension gapButtons = Tools.pixels(0,25);
	private Dimension title = Tools.pixels(1248,300);
	private Dimension imgButtons = Tools.pixels(430,115);

	
	
	public StarterMenuView(MainView mainPanel) {
		_mainPanel= mainPanel;
		initStarterMenuGUI();
	
	}
	
	private void  initStarterMenuGUI() {
		setBackground(Color.WHITE);
		setLayout(new  BorderLayout(5,30)); //Separacion entre regiones del panel
		
		//Panel Norte
		JPanel northPanel = new JPanel();
		northPanel.setBackground(Color.WHITE);
		northPanel.setLayout(new FlowLayout());
		memoryImage = memoryImage.getScaledInstance(title.width, title.height,java.awt.Image.SCALE_SMOOTH);
		memoryTitle.setIcon(new ImageIcon(memoryImage));
		memoryTitle.setAlignmentX(CENTER_ALIGNMENT);
		northPanel.add(memoryTitle);
		
		//Panel Central 
		JPanel centralPanel = centralPanel();
		centralPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		this.add(northPanel,BorderLayout.NORTH);
		this.add(centralPanel,BorderLayout.CENTER);
		
	}

	private JPanel centralPanel() {
		//Panel Central donde van los botones
		JPanel center = new JPanel();
		center.setLayout(new BoxLayout(center, BoxLayout.Y_AXIS));
		center.setBackground(Color.WHITE);
		
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
		setRollOverEffect(newGameButton,newGameImage);
		Tools.removeShapeButton(newGameButton);
		
		// LOAD BUTTON
		loadButton = new JButton();
		loadButton.setActionCommand("");
		loadButton.addActionListener(this);
		
		loadButton.setAlignmentX(Component.CENTER_ALIGNMENT);		//Alineacion en el BoxLayout
		loadButton.setMinimumSize(dimButton);
		loadButton.setPreferredSize(dimButton);
		loadButton.setMaximumSize(dimButton);
		
		loadButton.setVerticalAlignment(SwingConstants.TOP);		//Alieneacion Imagen y efectos
		loadButton.setHorizontalAlignment(SwingConstants.RIGHT);
		loadButton.setBackground(Color.WHITE);
		loadButton.setIcon(new ImageIcon(Tools.resizeImage(loadImage, imgButtons)));
		setRollOverEffect(loadButton,loadImage);
		Tools.removeShapeButton(loadButton);
		
		//HOW TO PLAY BUTTON
		howToPlayButton = new JButton();
		howToPlayButton.setActionCommand("");
		howToPlayButton.addActionListener(this);
		
		howToPlayButton.setAlignmentX(Component.CENTER_ALIGNMENT);		//Alineacion en el BoxLayout
		howToPlayButton.setMinimumSize(dimButton);
		howToPlayButton.setPreferredSize(dimButton);
		howToPlayButton.setMaximumSize(dimButton);

		howToPlayButton.setVerticalAlignment(SwingConstants.TOP);		//Alieneacion Imagen y efectos
		howToPlayButton.setHorizontalAlignment(SwingConstants.RIGHT);
		howToPlayButton.setBackground(Color.WHITE);
		howToPlayButton.setIcon(new ImageIcon(Tools.resizeImage(howToPlayImage, imgButtons)));
		setRollOverEffect(howToPlayButton,howToPlayImage);
		Tools.removeShapeButton(howToPlayButton);
		
		//EXIT BUTTON
		exitButton = new JButton();
		exitButton.setActionCommand("");
		exitButton.addActionListener(this);
	
		exitButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		exitButton.setAlignmentY(Component.BOTTOM_ALIGNMENT);
		exitButton.setMinimumSize(dimButton);
		exitButton.setPreferredSize(dimButton);
		exitButton.setMaximumSize(dimButton);
		
		exitButton.setVerticalAlignment(SwingConstants.TOP);		//Alieneacion Imagen y efectos
		exitButton.setHorizontalAlignment(SwingConstants.RIGHT);
		exitButton.setBackground(Color.WHITE);
		exitButton.setIcon(new ImageIcon(Tools.resizeImage(exitImage, imgButtons)));
		setRollOverEffect(exitButton,exitImage);
		Tools.removeShapeButton(exitButton);
		
		
		//Añadimos botones y espacio entre ellos
		center.add(newGameButton);
		center.add(Box.createRigidArea(gapButtons));
		center.add(loadButton);
		center.add(Box.createRigidArea(gapButtons));
		center.add(howToPlayButton);
		center.add(Box.createRigidArea(gapButtons));
		center.add(exitButton);
		
		return center;
	}
	
	
	private void setRollOverEffect(JButton b, Image img) {
		Image aux =img.getScaledInstance((int)(imgButtons.getWidth()+MainView._screenSize.width*0.005),(int)(imgButtons.getHeight()+MainView._screenSize.height*0.005),java.awt.Image.SCALE_SMOOTH);
		b.setRolloverIcon(new ImageIcon(aux));
	}
	
	public void actionPerformed(ActionEvent e) {	
		if(e.getSource()==newGameButton) {
			_mainPanel.changeView(MainView._optionsGame);
		}
		else if( e.getSource() == loadButton) {
			int ret = file_chooser.showOpenDialog(this.getParent()); // Abrimos la ventana para seleccionar el fichero
			if (ret == JFileChooser.APPROVE_OPTION) { // Comprobamos como se ha interactuado con la ventana
				 File file = file_chooser.getSelectedFile();
				 try {
					FileInputStream in = new FileInputStream(file);
					_mainPanel.load(in);
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
				 catch (Exception fne) {
					 fne.printStackTrace();
					 JOptionPane.showMessageDialog(this.getParent(), fne.getMessage() + "\nUnable to load this file", "Error", JOptionPane.ERROR_MESSAGE);
				 }
			}
		}
		else if(e.getSource()==howToPlayButton) {
			//Nueva pantalla donde se explican las reglas del juego
			new OpenInstructionsDialog((Frame)_mainPanel);
		}
		else if(e.getSource()==exitButton) {
			//Ventana emergente
			Object[] options = { "Yes", "No" };
			ImageIcon exit_conf_icon_first = new ImageIcon("icons/exit_symbol_confirmation_dialog.png");
			ImageIcon exit_conf_icon = new ImageIcon(Tools.resizeImage(exit_conf_icon_first.getImage(), new Dimension(60, 60)));
	        int resp = JOptionPane.showOptionDialog(null, "Are you sure you want to exit?",
	                "Memory", JOptionPane.YES_NO_OPTION,
	                JOptionPane.QUESTION_MESSAGE, exit_conf_icon, options, options[1]);
	        if (resp == JOptionPane.OK_OPTION)
	        	System.exit(0);
		}
		
	}	
	

}