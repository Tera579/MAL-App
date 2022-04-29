package GUI;

import javax.swing.JFrame;

public class MyFrame extends JFrame{
	 private static final long serialVersionUID = 1L;

	 public MyFrame() {
	        super() ;
	        this.setTitle("MAL-App project");
	        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        this.setExtendedState(JFrame.MAXIMIZED_BOTH); 
	        //this.setUndecorated(true);
	        this.setResizable(false);
	        this.add(new MyPanel());
	        this.pack();
	        this.setVisible(true);
	    }
}
