package GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import Business.*;



public class Control extends JPanel {
    
    private static JButton Valider;
    
    private static Drawing panelDrawing;
    private static Status panelStatus;
    private static Help panelHelp;
    
    private Potentiel p;
    private double[][] pot;
    
    public Control(Potentiel p) {
    	this.setPreferredSize(new Dimension(200,180));
    	this.p =p;
    	
        //Région NORTH du BorderLayout 
        JPanel panNORTH = new JPanel(); //le panneau est instancié
        genPanNORTH(panNORTH); //méthode constructionRegionOuest à construire en dessous avec tout ce qu'on veut 
        
        this.add(panNORTH, BorderLayout.NORTH);
    }
    private void genPanNORTH(JPanel panNORTH) {
           
    	panNORTH.setPreferredSize(new Dimension(400, 200)); //taille du panneau
    	panNORTH.setLayout(null);
    	
    	// Saisie point A
    	JFormattedTextField xtextA = new JFormattedTextField() ;
        xtextA.setColumns(4);
        xtextA.setText("0");
        xtextA.addPropertyChangeListener(null);
        
        JFormattedTextField  ytextA = new JFormattedTextField() ;
        ytextA.setColumns(4);
        ytextA.setText("0");
        ytextA.addPropertyChangeListener(null);
        
        JFormattedTextField qtextA = new JFormattedTextField() ;
        qtextA.setColumns(4);
        qtextA.setText("0");
        qtextA.addPropertyChangeListener(null);

        JPanel AXY = new JPanel();
        JPanel AQ = new JPanel();
        
        AXY.add(new JLabel("A=("));
        AXY.add(xtextA);
        AXY.add(new JLabel(";"));
        AXY.add(ytextA);
        AXY.add(new JLabel(")"));
        AQ.add(new JLabel("qA="));
        AQ.add(qtextA);
        AQ.add(new JLabel("nC"));
        
     // Saisie point B
    	JFormattedTextField xtextB = new JFormattedTextField() ;
    	xtextB.setColumns(4);
    	xtextB.setText("0");
    	xtextB.addPropertyChangeListener(null);
        
        JFormattedTextField  ytextB = new JFormattedTextField() ;
        ytextB.setColumns(4);
        ytextB.setText("0");
        ytextB.addPropertyChangeListener(null);
        
        JFormattedTextField qtextB = new JFormattedTextField() ;
        qtextB.setColumns(4);
        qtextB.setText("0");
        qtextB.addPropertyChangeListener(null);

        JPanel BXY = new JPanel();
        JPanel BQ = new JPanel();
        
        BXY.add(new JLabel("B=("));
        BXY.add(xtextB);
        BXY.add(new JLabel(";"));
        BXY.add(ytextB);
        BXY.add(new JLabel(")"));
        BQ.add(new JLabel("qB="));
        BQ.add(qtextB);
        BQ.add(new JLabel("nC"));
        
     // Saisie point M
    	JFormattedTextField xtextM = new JFormattedTextField() ;
    	xtextM.setColumns(4);
        xtextM.setText("0");
        xtextM.addPropertyChangeListener(null);
        
        JFormattedTextField  ytextM = new JFormattedTextField() ;
        ytextM.setColumns(4);
        ytextM.setText("0");
        ytextM.addPropertyChangeListener(null);

        JPanel MXY = new JPanel();
        
        MXY.add(new JLabel("M=("));
        MXY.add(xtextM);
        MXY.add(new JLabel(";"));
        MXY.add(ytextM);
        MXY.add(new JLabel(")"));
        
        // Bouton Valider
        JButton Valider = new JButton("Valider"); 
        Valider.setBounds(195, 85, 100, 40);
        
        // Bouton Modifier
        JButton Modifier = new JButton("Modifier"); 
        Modifier.setBounds(95, 85, 100, 40);
        Modifier.setEnabled(false);
    	
    	// Ajouts des elements
        this.add(AXY);  
        this.add(AQ);
        this.add(BXY);  
        this.add(BQ);
        this.add(MXY);  
        this.add(Valider);
        this.add(Modifier);
    	
        // Action de Valider
        Valider.addActionListener((ActionEvent evt) -> {
            boolean pass=true;
            
            // Vérifier si les valeurs saisies sont conformes
            try{Double.parseDouble(xtextA.getText());}
        	catch(NumberFormatException a){
        		pass=false;
        		xtextA.setText(""+p.getA().getPoint().getX());
        		System.out.println("le X de A est non conforme");}
            
            try{Double.parseDouble(xtextB.getText());}
        	catch(NumberFormatException a){
        		pass=false;
        		xtextB.setText(""+p.getB().getPoint().getX());
        		System.out.println("le X de B est non conforme");}
            
            try{Double.parseDouble(xtextM.getText());}
        	catch(NumberFormatException a){
        		pass=false;
        		xtextM.setText(""+p.getM().getX());
        		System.out.println("le X de M est non conforme");}
            
            try{Double.parseDouble(ytextA.getText());}
        	catch(NumberFormatException a){
        		pass=false;
        		ytextA.setText(""+p.getA().getPoint().getY());
        		System.out.println("le Y de A est non conforme");}
            
            try{Double.parseDouble(ytextB.getText());}
        	catch(NumberFormatException a){
        		pass=false;
        		ytextB.setText(""+p.getB().getPoint().getY());
        		System.out.println("le Y de B est non conforme");}
            
            try{Double.parseDouble(ytextM.getText());}
        	catch(NumberFormatException a){
        		pass=false;
        		ytextM.setText(""+p.getM().getY());
        		System.out.println("le Y de M est non conforme");}
            
            try{Double.parseDouble(qtextA.getText());}
        	catch(NumberFormatException a){
        		pass=false;
        		qtextA.setText(""+p.getA().getQ());
        		System.out.println("la charge de A est non conforme");}
            
            try{Double.parseDouble(qtextB.getText());}
        	catch(NumberFormatException a){
        		pass=false;
        		qtextB.setText(""+p.getA().getQ());
        		System.out.println("la charge de B est non conforme");}
            
            
           if (pass) {
        	   double qA= ((double)(int)(Double.parseDouble(qtextA.getText())*100))/100;
        	   double xA= ((double)(int)(Double.parseDouble(xtextA.getText())*100))/100;
        	   double yA= ((double)(int)(Double.parseDouble(ytextA.getText())*100))/100;
        	   double qB= ((double)(int)(Double.parseDouble(qtextB.getText())*100))/100;
        	   double xB= ((double)(int)(Double.parseDouble(xtextB.getText())*100))/100;
        	   double yB= ((double)(int)(Double.parseDouble(ytextB.getText())*100))/100;
        	   double xM= ((double)(int)(Double.parseDouble(xtextM.getText())*100))/100;
        	   double yM= ((double)(int)(Double.parseDouble(ytextM.getText())*100))/100;
        	   
        	   p.getA().setQ(qA*1e-9);
        	   p.getA().getPoint().setX(xA);
        	   p.getA().getPoint().setY(yA);
        	   p.getB().setQ(qB*1e-9);
        	   p.getB().getPoint().setX(xB);
        	   p.getB().getPoint().setY(yB);
        	   p.getM().setX(xM);
        	   p.getM().setY(yM);
        	   
        	   ElectricField Field = new ElectricField(p);
        	   pot = Field.getElectricField();
        	   
        	   //Calcul du pot en M
        	   p.calculPotentiel(p.getA(), p.getB(), p.getM());
        	   System.out.println("Le potentiel en M est: "+p.getV());
        	   
        	   panelDrawing.start(pot, "grad");
        	   panelDrawing.gradient();
        	   panelDrawing.repaint();
        	   

               Modifier.setEnabled(true);
               Valider.setEnabled(false);
               xtextA.setEnabled(false);
               xtextB.setEnabled(false);
               xtextM.setEnabled(false);
               ytextA.setEnabled(false);
               ytextB.setEnabled(false);
               ytextM.setEnabled(false);
               qtextA.setEnabled(false);
               qtextB.setEnabled(false);
           }
         
            });
        
     // Action de Modifier
        Modifier.addActionListener((ActionEvent evt) -> {

        	Modifier.setEnabled(false);
            Valider.setEnabled(true);
            xtextA.setEnabled(true);
            xtextB.setEnabled(true);
            xtextM.setEnabled(true);
            ytextA.setEnabled(true);
            ytextB.setEnabled(true);
            ytextM.setEnabled(true);
            qtextA.setEnabled(true);
            qtextB.setEnabled(true);
            });
    }
    
    
    public void setpanelDrawing(Drawing panelDrawing) {
        Control.panelDrawing = panelDrawing;
    }
    public void setpanelStatus(Status panelStatus) {
        Control.panelStatus = panelStatus;
    }
    public void setpanelHelp(Help panelHelp) {
        Control.panelHelp = panelHelp;
    }
}