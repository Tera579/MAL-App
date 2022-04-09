package GUI;

import java.awt.Dimension;
import javax.swing.JFrame;

import GUI.MyPanel;

public class MyFrame extends JFrame{
	 public MyFrame() {
	        super() ;
	        this.setTitle("Générateur de signaux - Version 08 ...");
	        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        this.setExtendedState(JFrame.MAXIMIZED_BOTH); 
	        //this.setUndecorated(true);

	        this.add(new MyPanel());
	        
	        this.pack();
	        this.setVisible(true);
	    }
	    
}
