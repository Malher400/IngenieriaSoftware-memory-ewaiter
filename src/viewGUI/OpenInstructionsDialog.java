package viewGUI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.MatteBorder;

public class OpenInstructionsDialog extends JDialog {
	
	private JTextArea storyArea ;
	
	private Color backgroundColor = new Color(194, 238, 255);
	
	public OpenInstructionsDialog(Frame parent) {
		super(parent, "How to Play",true);
		//this.setLocationRelativeTo(null);
		String info = "";
		try {
			InputStream Info = new FileInputStream(new File("resources/info.txt"));
			info = new BufferedReader(new InputStreamReader(Info)).lines().collect(Collectors.joining("\n"));
			Info.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		setResizable(true);
		

		storyArea = new JTextArea(info);
		// storyArea.setFont(MainView.instructionsFont);
		storyArea.setFont(new Font("DialogInput", Font.BOLD, 20));
		storyArea.setLineWrap(true);
		storyArea.setWrapStyleWord(true);
		storyArea.setBackground(backgroundColor);
		storyArea.setEditable(false);
		JScrollPane area = new JScrollPane(storyArea);
		area.setBackground(backgroundColor);
		area.setPreferredSize(new Dimension(800,600));
		area.setBorder(new MatteBorder(30, 30, 30, 0,backgroundColor));
		this.add(area);
		
		this.setSize(new Dimension(MainView._screenSize.width/2,MainView._screenSize.height/4));
		final int x = (MainView._screenSize.width)/2 - this.getWidth() / 2;
		this.setLocation(x, MainView._screenSize.height/8);
			
		this.getContentPane().add(area);
		pack();
		
		this.setVisible(true);

	}
}
