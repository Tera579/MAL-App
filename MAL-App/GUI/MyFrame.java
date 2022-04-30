package GUI;


import javax.swing.JFrame;

public class MyFrame extends JFrame{
	
	 private static final long serialVersionUID = 1L;

	 public MyFrame() {
		 	JFrame MyFrame = new JFrame();
		 	MyFrame.setTitle("MAL-App project");
		 	MyFrame.setPreferredSize(getPreferredSize());
		 	MyFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		 	MyFrame.setExtendedState(JFrame.MAXIMIZED_BOTH); 
		 	MyFrame.setResizable(false);
		 	MyFrame.add(new MyPanel());
		 	MyFrame.pack();
		 	MyFrame.setVisible(true);
	    }
}
