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
	        Point M = new Point("M");
	        Potential p = new Potential("Potentiel", A, B ,M);
	        
	        Status panelStatus = new Status();
	        Control panelControl = new Control(p);
	        Help panelHelp = new Help();
	        Drawing panelDrawing = new Drawing(p);
	        
	        
	        this.add(panelStatus, BorderLayout.SOUTH);
	        this.add(panelDrawing, BorderLayout.CENTER);
	        this.add(panelHelp, BorderLayout.NORTH);
	        this.add(panelControl, BorderLayout.WEST);  
	        
	        panelControl.setpanelDrawing(panelDrawing);
	        panelControl.setpanelStatus(panelStatus);
	        panelControl.setpanelHelp(panelHelp);
	    } 
}
 
