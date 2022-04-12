package GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeListener;

import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.ButtonModel;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import Business.*;



public class Control extends JPanel {
    
    private static JButton Valider;
    
    private static Drawing panelDrawing;
    private static Status panelStatus;
    private static Help panelHelp;
    
    private Potentiel p;
    private double[][] pot;
    double plus, minus;
    
    JPanel panEch;
    JPanel panPoint;
    JPanel panPara;
    
    JRadioButton Graph;
    JRadioButton Point;
    JRadioButton Para;
    
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
        Graph = new JRadioButton();
        Point = new JRadioButton();
        Para = new JRadioButton();
        ButtonGroup SelecButton = new ButtonGroup();
        SelecButton.add(Graph);
        SelecButton.add(Point);
        SelecButton.add(Para);
        Selec.add(Graph);
        Selec.add(Point);
        Selec.add(Para);
        this.add(Selec);
        Graph.doClick();
        Graph.setFocusable(false);
        Point.setFocusable(false);
        Para.setFocusable(false);
        Point.setEnabled(false);
        Para.setEnabled(false);
        Graph.addActionListener((ActionEvent evt) -> {
        	panEch.setVisible(true);
            panPoint.setVisible(false);
            panPara.setVisible(false);
            Point.setEnabled(false);
            Para.setEnabled(false);
            });
        Point.addActionListener((ActionEvent evt) -> {
        	panEch.setVisible(false);
            panPoint.setVisible(true);
            panPara.setVisible(false);
            Para.setEnabled(false);
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
    	JPanel labelWrapper = new JPanel();
    	JLabel label = new JLabel("Paramètres Graphique", SwingConstants.CENTER);
    	label.setBackground(Color.BLACK);
    	label.setOpaque(true);
    	label.setForeground(Color.WHITE);
    	labelWrapper.add(label);
    	
    	// Saisie de la graduation
    	JPanel grad = new JPanel();
    	grad.add(new JLabel("graduation ="));
    	JFormattedTextField gradtext = new JFormattedTextField() ;
    	gradtext.setBackground(getBackground());
    	gradtext.setBorder(BorderFactory.createLineBorder(getBackground(), 2));
    	gradtext.setHorizontalAlignment(JTextField.CENTER);
    	gradtext.setColumns(4);
    	gradtext.setText("1");
    	grad.add(gradtext);
    	
    	// Saisie de xmax
    	JPanel xmax = new JPanel();
    	xmax.add(new JLabel("xmax ="));
    	JFormattedTextField xmaxtext = new JFormattedTextField() ;
    	xmaxtext.setBackground(getBackground());
    	xmaxtext.setBorder(BorderFactory.createLineBorder(getBackground(), 2));
    	xmaxtext.setHorizontalAlignment(JTextField.CENTER);
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
        		Point.setEnabled(true);
        		Point.doClick();
        		Point.doClick();
        	}
        	
            });
        panEch.add(labelWrapper);
        panEch.add(xmax);
        panEch.add(grad);
        panEch.add(Valider);
    }
    
    private void genPanPoint() {
    	panPoint.setLayout(new GridLayout(0,1));
    	JPanel labelWrapper = new JPanel();
    	JLabel label = new JLabel("Coordonées des Points", SwingConstants.CENTER);
    	label.setBackground(Color.BLACK);
    	label.setOpaque(true);
    	label.setForeground(Color.WHITE);
    	labelWrapper.add(label);
    	
    	// Saisie point A
    	JFormattedTextField xtextA = new JFormattedTextField() ;
    	xtextA.setBackground(getBackground());
    	xtextA.setColumns(2);
    	xtextA.setBorder(BorderFactory.createLineBorder(getBackground(), 2));
    	xtextA.setHorizontalAlignment(JTextField.CENTER);
        JFormattedTextField  ytextA = new JFormattedTextField() ;
        ytextA.setBackground(getBackground());
        ytextA.setColumns(2);
        ytextA.setBorder(BorderFactory.createLineBorder(getBackground(), 2));
        ytextA.setHorizontalAlignment(JTextField.CENTER);
        JFormattedTextField qtextA = new JFormattedTextField() ;
        qtextA.setBackground(getBackground());
        qtextA.setColumns(2);
        qtextA.setBorder(BorderFactory.createLineBorder(getBackground(), 2));
        qtextA.setHorizontalAlignment(JTextField.CENTER);

        JPanel AXYQ = new JPanel();
        AXYQ.setLayout(new BoxLayout(AXYQ, BoxLayout.X_AXIS));
        
        AXYQ.add(new JLabel(" A=("));
        AXYQ.add(xtextA);
        AXYQ.add(new JLabel(";"));
        AXYQ.add(ytextA);
        AXYQ.add(new JLabel(")"));
        AXYQ.add(new JLabel("   qA="));
        AXYQ.add(qtextA);
        AXYQ.add(new JLabel("nC "));
        
     // Saisie point B
        JFormattedTextField xtextB = new JFormattedTextField() ;
    	xtextB.setBackground(getBackground());
    	xtextB.setColumns(2);
    	xtextB.setBorder(BorderFactory.createLineBorder(getBackground(), 2));
    	xtextB.setHorizontalAlignment(JTextField.CENTER);
        JFormattedTextField  ytextB = new JFormattedTextField() ;
        ytextB.setBackground(getBackground());
        ytextB.setColumns(2);
        ytextB.setBorder(BorderFactory.createLineBorder(getBackground(), 2));
        ytextB.setHorizontalAlignment(JTextField.CENTER);
        JFormattedTextField qtextB = new JFormattedTextField() ;
        qtextB.setBackground(getBackground());
        qtextB.setColumns(2);
        qtextB.setBorder(BorderFactory.createLineBorder(getBackground(), 2));
        qtextB.setHorizontalAlignment(JTextField.CENTER);

        JPanel BXYQ = new JPanel();
        BXYQ.setLayout(new BoxLayout(BXYQ, BoxLayout.X_AXIS));
        
        BXYQ.add(new JLabel(" B=("));
        BXYQ.add(xtextB);
        BXYQ.add(new JLabel(";"));
        BXYQ.add(ytextB);
        BXYQ.add(new JLabel(")"));
        BXYQ.add(new JLabel("   qB="));
        BXYQ.add(qtextB);
        BXYQ.add(new JLabel("nC "));
        
     // Saisie point M
        JFormattedTextField xtextM = new JFormattedTextField() ;
        xtextM.setBackground(getBackground());
        xtextM.setColumns(2);
        xtextM.setBorder(BorderFactory.createLineBorder(getBackground(), 2));
        xtextM.setHorizontalAlignment(JTextField.CENTER);
        xtextM.setPreferredSize(new Dimension(35,20));
        JFormattedTextField  ytextM = new JFormattedTextField() ;
        ytextM.setBackground(getBackground());
        ytextM.setColumns(2);
        ytextM.setBorder(BorderFactory.createLineBorder(getBackground(), 2));
        ytextM.setHorizontalAlignment(JTextField.CENTER);
        ytextM.setPreferredSize(new Dimension(35,20));

        JPanel MXY = new JPanel(new GridBagLayout());
        
        MXY.add(new JLabel("M=("));
        MXY.add(xtextM);
        MXY.add(new JLabel(";"));
        MXY.add(ytextM);
        MXY.add(new JLabel(")"));
        
        // Bouton Valider
        JPanel Bouton = new JPanel();
        JButton Valider = new JButton("Valider"); 
        Bouton.add(Valider);
        
    	// Ajouts des element
        panPoint.add(labelWrapper);
        panPoint.add(AXYQ); 
        panPoint.add(BXYQ);
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
        	   p.calculPotentiel(p.getA(), p.getB(), p.getM());
        	   plus = Field.getElectricFieldPlus();
        	   minus = Field.getElectricFieldMinus();
        	   System.out.println("Le potentiel en M est: "+p.getV());
        	   
        	   // Setters de Drawing
        	   panelDrawing.setMode("Clas");
        	   panPoint.setVisible(false);
       		   panPara.setVisible(true);
        	   panelDrawing.repaint();
        	   
               Para.setEnabled(true);
               Para.doClick();
           }
         
            });
        
    }
    boolean Gradientselected=false;
    boolean Champselected=false;
    boolean Equipoteselected=false;
    
    private void genPanPara() {
    	panPara.setLayout(new BoxLayout(panPara, BoxLayout.Y_AXIS));
    	JPanel labelWrapper = new JPanel();
    	JLabel label = new JLabel("Options d'Affichage", SwingConstants.CENTER);
    	label.setBackground(Color.BLACK);
    	label.setOpaque(true);
    	label.setForeground(Color.WHITE);
    	labelWrapper.add(label);
    	panPara.add(labelWrapper);
    	
    	JPanel Button = new JPanel();
    	Button.setLayout(new BoxLayout(Button, BoxLayout.Y_AXIS));
    	Button.setAlignmentX(Component.CENTER_ALIGNMENT);
    	JRadioButton Gradient = new JRadioButton("Gradient");
        JRadioButton Champ = new JRadioButton("Champ Electrique");
        JRadioButton Equipote = new JRadioButton("Equipotentiel");
        Button.add(Gradient);
        Button.add(Champ);
        Button.add(Equipote);
        Button.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        panPara.add(Button);
        
        JPanel panGradient = new JPanel();
        JPanel panChamp = new JPanel();
        JPanel panEquipote = new JPanel();
        panGradient.setVisible(false);
    	panChamp.setVisible(false);
    	panEquipote.setVisible(false);
    	
    	// Listener
    	ChangeListener GradientselectedListener = new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				AbstractButton aButton = (AbstractButton)e.getSource();
    	        ButtonModel aModel = aButton.getModel();
    	        Gradientselected = aModel.isSelected();
			}
    	    };
    	Gradient.addChangeListener(GradientselectedListener);
    	ChangeListener ChampselectedListener = new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				AbstractButton aButton = (AbstractButton)e.getSource();
    	        ButtonModel aModel = aButton.getModel();
    	        Champselected = aModel.isSelected();
			}
    	    };
    	Champ.addChangeListener(ChampselectedListener);
    	ChangeListener EquipoteselectedListener = new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				AbstractButton aButton = (AbstractButton)e.getSource();
    	        ButtonModel aModel = aButton.getModel();
    	        Equipoteselected = aModel.isSelected();
			}
    	    };
        Equipote.addChangeListener(EquipoteselectedListener);
        
        Gradient.addActionListener((ActionEvent evt) -> {
        	panGradient.setVisible(Gradientselected);
            panChamp.setVisible(Champselected);
        	panEquipote.setVisible(Equipoteselected);
            });
        Champ.addActionListener((ActionEvent evt) -> {
        	panGradient.setVisible(Gradientselected);
            panChamp.setVisible(Champselected);
        	panEquipote.setVisible(Equipoteselected);
            });
        Equipote.addActionListener((ActionEvent evt) -> {
        	panGradient.setVisible(Gradientselected);
            panChamp.setVisible(Champselected);
        	panEquipote.setVisible(Equipoteselected);
            });
        
    	// Gradient
        panGradient.setLayout(new GridLayout(0,1));
        JPanel labelWrapperGradient = new JPanel();
    	JLabel labelGradient = new JLabel("Gradient", SwingConstants.CENTER);
    	labelGradient.setBackground(Color.BLACK);
    	labelGradient.setOpaque(true);
    	labelGradient.setForeground(Color.WHITE);
    	labelWrapperGradient.add(labelGradient);
    	panGradient.add(labelWrapperGradient);
    	JPanel panGradient1 = new JPanel();
        panGradient1.add(new JLabel("Nombre de Couleurs :"));
    	JFormattedTextField nbrColor = new JFormattedTextField() ;
    	nbrColor.setBackground(getBackground());
    	nbrColor.setBorder(BorderFactory.createLineBorder(getBackground(), 2));
    	nbrColor.setHorizontalAlignment(JTextField.CENTER);
    	nbrColor.setText("25");
    	nbrColor.setColumns(3);
    	panGradient1.add(nbrColor);
    	JButton ValiderNbrColor = new JButton("Valider"); 
    	ValiderNbrColor.addActionListener((ActionEvent evt) -> {
        	boolean pass = true;
        	try{Double.parseDouble(nbrColor.getText());}
        	catch(NumberFormatException a){
        		pass=false;
        		nbrColor.setText("25");
        		System.out.println("le xmax est non conforme");}
        	if (pass) {
        		panelDrawing.setNbrColor((int)Double.parseDouble(nbrColor.getText()));
         	    panelDrawing.setPot(pot, plus, minus);
        		panelDrawing.setMode("Grad");
        		panelDrawing.repaint();
        	}
        	
            });
    	panGradient.add(panGradient1);
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