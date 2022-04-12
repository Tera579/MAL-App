package GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.SwingConstants;

import Business.*;



public class Control extends JPanel {
    
    private static JButton Valider;
    
    private static Drawing panelDrawing;
    private static Status panelStatus;
    private static Help panelHelp;
    
    private Potentiel p;
    private double[][] pot;
    private String mode="Ech"; // Ech Point Para 
    double plus, minus;
    
    JPanel panEch;
    JPanel panPoint;
    JPanel panPara;
    
    public Control(Potentiel p) {
    	this.setPreferredSize(new Dimension(200,180));
    	this.p =p;
    	
    	panEch = new JPanel();
        panPoint = new JPanel();
        panPara = new JPanel();
        
        genPanEch();
        genPanPoint();
        genPanPara();
        
        panEch.setVisible(true);
        panPoint.setVisible(false);
        panPara.setVisible(false);
        
        // Selection
        JPanel Selec = new JPanel();
        JRadioButton Graph = new JRadioButton();
        JRadioButton Point = new JRadioButton();
        JRadioButton Para = new JRadioButton();
        ButtonGroup SelecButton = new ButtonGroup();
        SelecButton.add(Graph);
        SelecButton.add(Point);
        SelecButton.add(Para);
        Selec.add(Graph);
        Selec.add(Point);
        Selec.add(Para);
        this.add(Selec);
        Graph.doClick();
        Graph.addActionListener((ActionEvent evt) -> {
        	panEch.setVisible(true);
            panPoint.setVisible(false);
            panPara.setVisible(false);
            });
        Point.addActionListener((ActionEvent evt) -> {
        	panEch.setVisible(false);
            panPoint.setVisible(true);
            panPara.setVisible(false);
            });
        Para.addActionListener((ActionEvent evt) -> {
        	panEch.setVisible(false);
            panPoint.setVisible(false);
            panPara.setVisible(true);
            });
        
        this.add(panEch);
        this.add(panPoint);
        this.add(panPara);
        
    }
    private void genPanEch() {
    	panEch.setLayout(new GridLayout(0,1));
    	JLabel label = new JLabel("Paramètres Graphique", SwingConstants.CENTER);
    	label.setBorder(BorderFactory.createLineBorder(Color.black, 2));
    	
    	// Saisie de la graduation
    	JPanel grad = new JPanel();
    	grad.add(new JLabel("graduation ="));
    	JFormattedTextField gradtext = new JFormattedTextField() ;
    	gradtext.setColumns(4);
    	gradtext.setText("1");
    	grad.add(gradtext);
    	
    	// Saisie de xmax
    	JPanel xmax = new JPanel();
    	xmax.add(new JLabel("xmax ="));
    	JFormattedTextField xmaxtext = new JFormattedTextField() ;
    	xmaxtext.setColumns(4);
    	xmaxtext.setText("15");
    	xmax.add(xmaxtext);
    	
    	// Bouton Valider
        JButton Valider = new JButton("Valider"); 
        
        
        Valider.addActionListener((ActionEvent evt) -> {
        	boolean pass = true;
        	try{Double.parseDouble(gradtext.getText());}
        	catch(NumberFormatException a){
        		pass=false;
        		gradtext.setText("1");
        		System.out.println("le xmax est non conforme");}
        	try{Double.parseDouble(xmaxtext.getText());}
        	catch(NumberFormatException a){
        		pass=false;
        		xmaxtext.setText("15");
        		System.out.println("l'echelle est non conforme");}
        	if (pass) {
        		double graddouble = Double.parseDouble(gradtext.getText());
        		double xmaxdouble = Double.parseDouble(xmaxtext.getText());
        		int realEch = (int)((panelDrawing.getWidth()-40)/(2*xmaxdouble));
        		panelDrawing.setEch(realEch, (int)xmaxdouble, (int)graddouble);
        		Conversion.setEch(realEch);
        		panelDrawing.setMode("Axes");
        		panelDrawing.repaint();
        		panPoint.setVisible(true);
        		panEch.setVisible(false);
        	}
        	
            });
        panEch.add(label);
        panEch.add(xmax);
        panEch.add(grad);
        panEch.add(Valider);
    }
    private void genPanPoint() {
    	panPoint.setLayout(new GridLayout(0,1));
    	JLabel label = new JLabel("Coordonées des Points", SwingConstants.CENTER);
    	label.setBorder(BorderFactory.createLineBorder(Color.black, 2));
    	
    	// Saisie point A
    	JFormattedTextField xtextA = new JFormattedTextField() ;
        xtextA.setColumns(4);
        
        JFormattedTextField  ytextA = new JFormattedTextField() ;
        ytextA.setColumns(4);
        
        JFormattedTextField qtextA = new JFormattedTextField() ;
        qtextA.setColumns(4);

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
        
        JFormattedTextField  ytextB = new JFormattedTextField() ;
        ytextB.setColumns(4);
        
        JFormattedTextField qtextB = new JFormattedTextField() ;
        qtextB.setColumns(4);

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
        
        JFormattedTextField  ytextM = new JFormattedTextField() ;
        ytextM.setColumns(4);

        JPanel MXY = new JPanel();
        
        MXY.add(new JLabel("M=("));
        MXY.add(xtextM);
        MXY.add(new JLabel(";"));
        MXY.add(ytextM);
        MXY.add(new JLabel(")"));
        
        // Bouton Valider
        JButton Valider = new JButton("Valider"); 
        
        // Bouton Modifier
        JButton Modifier = new JButton("Modifier"); 
        Modifier.setEnabled(false);
    	
        // Ligne Bouton
        JPanel Bouton = new JPanel();
        Bouton.add(Valider);
        Bouton.add(Modifier);
        
    	// Ajouts des element
        panPoint.add(label);
        panPoint.add(AXY);  
        panPoint.add(AQ);
        panPoint.add(BXY);  
        panPoint.add(BQ);
        panPoint.add(MXY);  
        panPoint.add(Bouton);
    	
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
        	   //p.calculPotentiel(p.getA(), p.getB(), p.getM());
        	   plus = Field.getElectricFieldPlus();
        	   minus = Field.getElectricFieldMinus();
        	   //System.out.println("Le potentiel en M est: "+p.getV());
        	   
        	   // Setters de Drawing
        	   panelDrawing.setMode("Clas");
        	   panPoint.setVisible(false);
       		   panPara.setVisible(true);
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
    
    private void genPanPara() {
    	panPara.setLayout(new BoxLayout(panPara, BoxLayout.Y_AXIS));
    	JLabel label = new JLabel("Options d'Affichage", SwingConstants.CENTER);
    	label.setBorder(BorderFactory.createLineBorder(Color.black, 2));
    	panPara.add(label, BorderLayout.CENTER);
    	
    	JPanel Button = new JPanel();
    	Button.setLayout(new GridLayout(0,1));
    	JRadioButton Gradient = new JRadioButton("Gradient");
        JRadioButton Champ = new JRadioButton("Champ Electrique");
        JRadioButton Equipote = new JRadioButton("Equipotentiel");
        ButtonGroup SelecButton = new ButtonGroup();
        SelecButton.add(Gradient);
        SelecButton.add(Champ);
        SelecButton.add(Equipote);
        Button.add(Gradient);
        Button.add(Champ);
        Button.add(Equipote);
        panPara.add(Button, BorderLayout.CENTER);
        JPanel panGradient = new JPanel();
        JPanel panChamp = new JPanel();
        JPanel panEquipote = new JPanel();
        panGradient.setVisible(false);
    	panChamp.setVisible(false);
    	panEquipote.setVisible(false);
        Gradient.addActionListener((ActionEvent evt) -> {
        	panGradient.setVisible(true);
        	panChamp.setVisible(false);
        	panEquipote.setVisible(false);
            });
        Champ.addActionListener((ActionEvent evt) -> {
        	panGradient.setVisible(false);
        	panChamp.setVisible(true);
        	panEquipote.setVisible(false);
            });
        Equipote.addActionListener((ActionEvent evt) -> {
        	panGradient.setVisible(false);
        	panChamp.setVisible(false);
        	panEquipote.setVisible(true);
            });
        
    	// Gradient
        panGradient.add(new JLabel("Nombre de Couleurs :"));
        panGradient.setLayout(new GridLayout(0,1));
    	JFormattedTextField nbrColor = new JFormattedTextField() ;
    	nbrColor.setColumns(4);
    	nbrColor.setText("25");
    	panGradient.add(nbrColor);
    	JButton ValiderNbrColor = new JButton("Valider"); 
    	ValiderNbrColor.addActionListener((ActionEvent evt) -> {
        	boolean pass = true;
        	try{Double.parseDouble(nbrColor.getText());}
        	catch(NumberFormatException a){
        		pass=false;
        		nbrColor.setText("25");
        		System.out.println("le xmax est non conforme");}
        	if (pass) {
        		panelDrawing.setNbrColor(25);
         	    panelDrawing.setPot(pot, plus, minus);
        		panelDrawing.setMode("Grad");
        		panelDrawing.repaint();
        	}
        	
            });
    	panGradient.add(ValiderNbrColor);
    	panPara.add(panGradient);
    	
    	// Champ
    	
    	// Equipote
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