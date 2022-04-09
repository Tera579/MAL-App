package GUI;

import java.awt.BorderLayout;
import javax.swing.JPanel;

import Business.Particule;
import Business.Point;
import Business.Potentiel;

public class MyPanel extends JPanel {
	 Potentiel p;
 	 Particule A,B;
 	 Point M;
 	 
	 public MyPanel() {
	        this.setLayout(new BorderLayout());  
	        A = new Particule("A");
	        B = new Particule("B");
	        M = new Point("M");
	        p = new Potentiel("Potentiel", A, B ,M);
	        
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
 
