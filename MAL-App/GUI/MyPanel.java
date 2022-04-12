package GUI;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JPanel;

import Business.Particule;
import Business.Point;
import Business.Potentiel;

public class MyPanel extends JPanel {
	 Potentiel p;
 	 Particule A,B;
 	 Point M;

     Status panelStatus;
     static Control panelControl;
     Help panelHelp;
     Drawing panelDrawing;
     
	 public MyPanel() {
	        this.setLayout(new BorderLayout());  
	        A = new Particule("A");
	        B = new Particule("B");
	        M = new Point("M");
	        p = new Potentiel("Potentiel", A, B ,M);
	        
	        panelStatus = new Status();
	        panelControl = new Control(p);
	        panelHelp = new Help();
	        panelDrawing = new Drawing(p);
	        
	        
	        this.add(panelStatus, BorderLayout.SOUTH);
	        this.add(panelDrawing, BorderLayout.CENTER);
	        this.add(panelHelp, BorderLayout.NORTH);
	        this.add(panelControl, BorderLayout.WEST);  
	        
	        panelControl.setpanelDrawing(panelDrawing);
	        panelControl.setpanelStatus(panelStatus);
	        panelControl.setpanelHelp(panelHelp);
	    }  
	 public static void setControlDimension(Dimension size) {
		 panelControl.setMaximumSize(size);;
	 }
}
 
