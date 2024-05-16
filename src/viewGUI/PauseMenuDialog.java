package viewGUI;

import java.awt.Color;
import java.awt.Component;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Box;
import javax.swing.BoxLayout;
import java.awt.Image;

import javax.swing.ImageIcon;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JSeparator;

public class PauseMenuDialog extends JDialog {

	// Attributes:
	private int option;
	JPanel panel;
	String fileName;
	
	// Buttons:
	private JButton continueButton;
	private JButton resetButton;
	private JButton saveExitButton;
	private JButton exitButton;
	
	// Images:
	private Image continueButtonImg = new ImageIcon("icons/pause_menu/continue_button_pause_menu.png").getImage();
	private Image resetButtonImg = new ImageIcon("icons/pause_menu/reset_button_pause_menu.png").getImage();
	private Image saveExitButtonImg = new ImageIcon("icons/pause_menu/save_exit_button_pause_menu.png").getImage();
	private Image exitButtonImg = new ImageIcon("icons/pause_menu/exit_button_pause_menu.png").getImage();
		
	private Dimension imgButtons = Tools.pixels(500,134);
	private Dimension gapButtons = Tools.pixels(0,0);
	private Dimension dimButton = new Dimension(500,110); 
	private Dimension dimDialog = new Dimension(820, 550);
	private JSeparator separator;
	
	// Colors:
	Color backGroundColor = new Color(255,238,248);
	Color borderColor = new Color(233,82,113);
	
	// Constructor:
	public PauseMenuDialog(Frame frame) {	
		super(frame, true);
		setUndecorated(true);
		setPreferredSize(dimDialog);
		setLocation((MainView._screenSize.width/2 - dimDialog.width/2), MainView._screenSize.height/2 - dimDialog.height/2);
		panel = new JPanel();
		this.setContentPane(panel);
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		panel.setBackground(backGroundColor);		
		initGUI();	
	}
	
	// Methods:
	private void initGUI() {
		option = 0;
		
		// Continue button:
		continueButton = new JButton();
		continueButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		continueButton.setAlignmentY(Component.CENTER_ALIGNMENT);
		continueButton.setMinimumSize(dimButton);
		continueButton.setPreferredSize(dimButton);
		continueButton.setMaximumSize(dimButton);
		continueButton.setBackground(backGroundColor);
		continueButton.setIcon(new ImageIcon(Tools.resizeImage(continueButtonImg, imgButtons)));
		setRollOverEffect(continueButton, continueButtonImg);
		Tools.removeShapeButton(continueButton);
		// action:
		continueButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				option = 0;
				setVisible(false);
			}
		});

		// Reset button:
		resetButton = new JButton();
		resetButton.setAlignmentX(Component.CENTER_ALIGNMENT); 
		resetButton.setAlignmentY(Component.CENTER_ALIGNMENT);
		resetButton.setMinimumSize(dimButton);
		resetButton.setPreferredSize(dimButton);
		resetButton.setMaximumSize(dimButton);
		resetButton.setBackground(backGroundColor);
		resetButton.setIcon(new ImageIcon(Tools.resizeImage(resetButtonImg, imgButtons)));
		setRollOverEffect(resetButton, resetButtonImg);
		Tools.removeShapeButton(resetButton);
		// action:
		resetButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				option = 1;
				setVisible(false);
			}
		});

		// Save and exit button:
		saveExitButton = new JButton();
		saveExitButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		saveExitButton.setAlignmentY(Component.CENTER_ALIGNMENT);
		saveExitButton.setMinimumSize(dimButton);
		saveExitButton.setPreferredSize(dimButton);
		saveExitButton.setMaximumSize(dimButton);
		saveExitButton.setBackground(backGroundColor);
		saveExitButton.setIcon(new ImageIcon(Tools.resizeImage(saveExitButtonImg, imgButtons)));
		setRollOverEffect(saveExitButton, saveExitButtonImg);
		Tools.removeShapeButton(saveExitButton);
		// action:
		saveExitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				fileName = JOptionPane.showInputDialog(
						null,
						"Introduce save file name");
				if (fileName != null) {
		        	option = 2;
					setVisible(false);
				}
			}
		});

		// Exit button:
		exitButton = new JButton();
		exitButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		exitButton.setAlignmentY(Component.CENTER_ALIGNMENT);
		exitButton.setMinimumSize(dimButton);
		exitButton.setPreferredSize(dimButton);
		exitButton.setMaximumSize(dimButton);
		exitButton.setBackground(backGroundColor);
		exitButton.setIcon(new ImageIcon(Tools.resizeImage(exitButtonImg, imgButtons)));
		setRollOverEffect(exitButton, exitButtonImg);
		Tools.removeShapeButton(exitButton);
		// action:
		exitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Object[] options = { "Yes", "No" };
				ImageIcon exit_conf_icon_first = new ImageIcon("icons/exit_symbol_confirmation_dialog.png");
				ImageIcon exit_conf_icon = new ImageIcon(Tools.resizeImage(exit_conf_icon_first.getImage(), new Dimension(60, 60)));
		        int resp = JOptionPane.showOptionDialog(null, "Are you sure you want to exit?",
		                "Memory", JOptionPane.YES_NO_OPTION,
		                JOptionPane.QUESTION_MESSAGE, exit_conf_icon, options, options[1]);
		        if (resp == JOptionPane.OK_OPTION) {
		        	option = 3;
		        	setVisible(false);
		        }
			}
		});
		
		// North separator:
		separator = new JSeparator();
		separator.setMaximumSize(new Dimension(0,55));
		panel.add(separator);

		// Add buttons and gaps:
		panel.add(continueButton);
		panel.add(Box.createRigidArea(gapButtons));
		panel.add(resetButton);
		panel.add(Box.createRigidArea(gapButtons));
		panel.add(saveExitButton);
		panel.add(Box.createRigidArea(gapButtons));
		panel.add(exitButton);
		
	}
	
	public String getFileName() {
		return fileName;
	}
	
	private void setRollOverEffect(JButton b, Image img) {
		Image aux =img.getScaledInstance((int)(imgButtons.getWidth()+MainView._screenSize.width*0.005),(int)(imgButtons.getHeight()+MainView._screenSize.height*0.005),java.awt.Image.SCALE_SMOOTH);
		b.setRolloverIcon(new ImageIcon(aux));
	}
	
	protected int open() {
		pack();
		setVisible(true);
		return option;
	}

	public void paint(Graphics g) { // creates dialog border
		super.paint(g);
		Graphics2D gr = (Graphics2D) g;
		gr.setColor(borderColor);
		gr.fillRect(0, 0, (int)dimDialog.getWidth(), 10); // up
		gr.fillRect(0, (int)dimDialog.getHeight() - 10, (int)dimDialog.getWidth(), 10); // down
		gr.fillRect(0, 0, 10, (int)dimDialog.getHeight()); // left
		gr.fillRect((int)dimDialog.getWidth() - 10, 0, 10, (int)dimDialog.getHeight()); // right
	}
	
}
