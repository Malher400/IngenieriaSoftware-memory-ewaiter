package viewGUI;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.io.File;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.ImageIcon;
import javax.swing.JButton;

public class Tools {
	
	protected static Dimension pixels(int width,int height) {
		Dimension d = new Dimension();
		Dimension _screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		double w = (double)(width)/1920;
		double h = (double) (height)/1080;
		d.width = (int) (_screenSize.width*w);
		d.height = (int)(_screenSize.height*h);
		return d;
	}

	protected static Image resizeImage(Image img, Dimension dim) {	//Redimensiona una imagen
		return img.getScaledInstance((int)dim.getWidth(),(int)dim.getHeight(),java.awt.Image.SCALE_SMOOTH);
	}
	
	protected static void removeShapeButton(JButton b) {		//Elimina forma del boton 
		b.setBorderPainted(false);			//elimina contorno
		b.setContentAreaFilled(false);		//Elimina fondo
		b.setOpaque(true);
		b.setFocusable(false);		//No marca el contorno del bton al pulsarlo
	}
	
	protected static void playSound(String soundFile) {
		try {
	    File f = new File("./" + soundFile);
	    AudioInputStream audioIn = AudioSystem.getAudioInputStream(f.toURI().toURL());  
	    Clip clip = AudioSystem.getClip();
	    clip.open(audioIn);
	    clip.loop(Clip.LOOP_CONTINUOUSLY);
	    clip.start();
		}
		catch(Exception e) {
			
		}
	}
	
	protected static void setRollOverEffect(JButton b, Image img, Dimension dim) {		//Efecto al pasar por encima raton
		Image aux =img.getScaledInstance(dim.width,dim.height,java.awt.Image.SCALE_SMOOTH);
		b.setRolloverIcon(new ImageIcon(aux));
	}
}
