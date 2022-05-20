package GUI;

import java.awt.BorderLayout;

import javax.swing.JPanel;

import Business.Particule;
import Business.Point;
import Business.Potential;

public class MyPanel extends JPanel {
	 private static final long serialVersionUID = 1L;
	 
	public MyPanel() {
	        this.setLayout(new BorderLayout());  
	        Particule A = new Particule("A");
	        Particule B = new Particule("B");
	        Potential p = new Potential("Potentiel", A, B);
	        
	        Control panelControl = new Control(p);
	        Help panelHelp = new Help();
	        Drawing panelDrawing = new Drawing(p, panelControl);
	        
	        
	        this.add(panelDrawing, BorderLayout.CENTER);
	        this.add(panelHelp, BorderLayout.NORTH);
	        this.add(panelControl, BorderLayout.WEST);  
	        
	        panelControl.setpanelDrawing(panelDrawing);
	        panelControl.setpanelHelp(panelHelp);
	    } 
}
 
