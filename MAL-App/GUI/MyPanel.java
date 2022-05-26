package GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;

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
	        panelControl.setPreferredSize(new Dimension(250, 800));
	        Help panelHelp = new Help();
	        panelHelp.setPreferredSize(new Dimension(1000, 30));
	        panelHelp.setText("Choisissez la taille du graphe");
	        Drawing panelDrawing = new Drawing(p, panelControl);
	        panelDrawing.setPreferredSize(new Dimension(750, 800));
	        
	        /*JPanel credit = new JPanel();
	        JLabel creditLabel = new JLabel("Projet informatique de: Amélie BOVE, Louis QUINOT et Marie TABARDIN");   
	        credit.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
	        credit.add(creditLabel);*/
	        
	        this.add(panelDrawing, BorderLayout.CENTER);
	        this.add(panelHelp, BorderLayout.NORTH);
	        this.add(panelControl, BorderLayout.WEST); 
	        //this.add(credit, BorderLayout.SOUTH);
	        
	        panelControl.setpanelDrawing(panelDrawing);
	        panelControl.setpanelHelp(panelHelp);
	    } 
}
 
