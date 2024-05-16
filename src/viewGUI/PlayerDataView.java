package viewGUI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.json.JSONArray;

import logic.GameObserver;


public class PlayerDataView extends JPanel implements GameObserver{

	private int id;
	private JSONArray arrayPlayers;
	
	// Componentes
	private JTextField pointsView;		//Se visualizan los puntos
	private JLabel starImg;
	private JLabel name;
	private JLabel imgPN = new JLabel();
	
	// Imagenes
	private Image _startIMG = new ImageIcon("icons/board/star_points_label.png").getImage();
	private Image _playerIMG;
	private Image _playIMG;
	
	//Dim Img
	private Dimension dimIMG = Tools.pixels(70, 70);
	
	public PlayerDataView(int id) {
		this.id = id;
		this.setBackground(Color.WHITE);
		this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		this.setMinimumSize(Tools.pixels(215, 90));
		this.setMaximumSize(Tools.pixels(215, 90));
		this.setPreferredSize(Tools.pixels(215, 90));
		String path1 = new String("icons/board/player" + id + "_image.png");
		String path2= new String("icons/board/player" + id + "_image_turn.png");
		_playerIMG = new ImageIcon(path1).getImage();
		_playIMG = new ImageIcon(path2).getImage();
		// Imagen con Nombre
		JPanel imgName = new JPanel(); // Panel que contiene la imagen y el nombre
		imgName.setBackground(Color.WHITE);
		imgName.setLayout(new BoxLayout(imgName, BoxLayout.Y_AXIS));
		imgPN.setMinimumSize(Tools.pixels(70, 70));
		imgPN.setMaximumSize(Tools.pixels(70, 70));
		imgPN.setPreferredSize(Tools.pixels(70, 70));
		imgPN.setIcon(new ImageIcon(Tools.resizeImage(_playerIMG, Tools.pixels(70, 70))));
		imgName.add(imgPN);
		name =  new JLabel("");
		imgName.add(name);
		this.add(imgName);

		// Puntos
		pointsView = new JTextField();
		pointsView.setMinimumSize(Tools.pixels(70, 70));
		pointsView.setMaximumSize(Tools.pixels(70, 70));
		pointsView.setPreferredSize(Tools.pixels(70, 70));
		pointsView.setHorizontalAlignment(JTextField.CENTER);
		pointsView.setFont(MainView.arcadeFontPoints);
		pointsView.setForeground(Color.BLACK);
		pointsView.setFocusable(false);
		this.add(pointsView);

		// Estrella
		starImg = new JLabel();
		starImg.setIcon(new ImageIcon(Tools.resizeImage(_startIMG, Tools.pixels(70, 70))));
		this.add(starImg);
	}
		
	
	protected void plays(boolean t) { // Le toca jugar
		if (t) {
			imgPN.setIcon(new ImageIcon(Tools.resizeImage(_playIMG, dimIMG)));
		} else {
			imgPN.setIcon(new ImageIcon(Tools.resizeImage(_playerIMG, dimIMG)));
		}

	}
	
	public void onRegister(JSONArray players, JSONArray cards, int round, int turn) {
		arrayPlayers = players;
		name.setText(arrayPlayers.getJSONObject(id - 1).getString("name"));
		pointsView.setText(Integer.toString(arrayPlayers.getJSONObject(id - 1).getInt("points")));
	}
	
	public void onTurnLost(int turn, int round) {}

	public void onCardPicked(int card, boolean discovered) {}

	public void onCardUnpicked(int card) {}

	public void onActionOccurred(JSONArray players, String s) {
		arrayPlayers = players;
		pointsView.setText(Integer.toString(arrayPlayers.getJSONObject(id - 1).getInt("points")));
	}

	public void onFinished(boolean winner, JSONArray players) {}

	public void onReset() {}
		
}
