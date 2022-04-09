package Application;

import GUI.MyFrame;
import javax.swing.SwingUtilities;


public class Main {
	public static void main(String[] args) {
        
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                MyFrame firstFrame = new MyFrame();
            }
        });
	}  
}
    