package GUI;

import java.awt.*;
import java.awt.event.ActionEvent;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import Business.*;



public class Control extends JPanel {
    private static final long serialVersionUID = 1L;
    
	// Instance des differents panneaux
    private static Drawing panelDrawing;
    private static Status panelStatus;
    private static Help panelHelp;
    
    // Constucteur
    private Potentiel p;
    
    // Stockage du potentiel en chaques points
    private double[][] pot;
    double plus, minus;
    
    // Mise en commun des panneaux et des JRadioButton entre les methodes
    JPanel panEch;
    JPanel panPoint;
    JPanel panPara;
    JRadioButton Graph;
    JRadioButton Point;
    JRadioButton Para;
    
    // Variables d'etat utilises par PanPara
    boolean Gradientselected=false;
    boolean Champselected=false;
    boolean Equipoteselected=false;
    boolean ShowCoordselected=false;
    boolean ShowMselected=false;
    
    // Bouton enregistrer
    private JButton Enregistrer;
    
    // Constructeur de Control
    public Control(Potentiel p) {
    	this.p =p;
    	this.setPreferredSize(new Dimension(200,180));
    	
    	// Instanciement des panneaux
    	panEch = new JPanel();
        panPoint = new JPanel();
        panPara = new JPanel();
        
        genPanEch();
        genPanPoint();
        genPanPara();
        
        panEch.setVisible(true);
        panPoint.setVisible(false);
        panPara.setVisible(false);
        
        // Bouton Enregistrer
    	Enregistrer = new JButton();
    	Enregistrer.addActionListener((ActionEvent evt) -> {
        	panelDrawing.saveImage();
            });
    	this.add(Enregistrer);
    	Enregistrer.setVisible(false);
    	ImageIcon EnregistrerIcon = new ImageIcon("Enregistrer.png");
    	Image img = EnregistrerIcon.getImage();
    	Image ico = img.getScaledInstance( 25, 25,  java.awt.Image.SCALE_SMOOTH ) ;  
    	EnregistrerIcon = new ImageIcon(ico);
    	Enregistrer.setFocusable(false);
    	Enregistrer.setIcon(EnregistrerIcon);
        
        // Selection de la page
        JPanel Selec = new JPanel();
        Graph = new JRadioButton();
        Point = new JRadioButton();
        Para = new JRadioButton();
        
        // Creation et ajout d'un groupe de JRadioButton pour en selectioner un a la fois
        ButtonGroup SelecButton = new ButtonGroup();
        SelecButton.add(Graph);
        SelecButton.add(Point);
        SelecButton.add(Para);
        Selec.add(Graph);
        Selec.add(Point);
        Selec.add(Para);
        this.add(Selec);
        
        // Evite les cercles bleus de focus
        Graph.setFocusable(false);
        Point.setFocusable(false);
        Para.setFocusable(false);
        
        // Active la fenetre Graph de base
        Graph.doClick();
        
        /* Desactive les JRadioButton Point et Para. Pour ne pas causer d'erreur de programme, 
         * il faut d'abord selectionner l'echelle puis les points et enfin les parametres graphiques.
         */
        Point.setEnabled(false);
        Para.setEnabled(false);
        
        // Listeners des JRadioButton
        Graph.addActionListener((ActionEvent evt) -> {
        	panEch.setVisible(true);
            panPoint.setVisible(false);
            panPara.setVisible(false);
            Point.setEnabled(false);
            Para.setEnabled(false);
            Enregistrer.setVisible(false);
            });
        Point.addActionListener((ActionEvent evt) -> {
        	panEch.setVisible(false);
            panPoint.setVisible(true);
            panPara.setVisible(false);
            Para.setEnabled(false);
            Enregistrer.setVisible(false);
            });
        Para.addActionListener((ActionEvent evt) -> {
        	panEch.setVisible(false);
            panPoint.setVisible(false);
            panPara.setVisible(true);
            Enregistrer.setVisible(true);
            });
        
        this.add(panEch);
        this.add(panPoint);
        this.add(panPara);
        
    }
    
    // Creation de la page 1 : Choix de l'echelle
    private void genPanEch() {
    	panEch.setLayout(new GridLayout(0,1));
    	
    	// Text "Paramètres Graphique"
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
        
        // Listener de Valider
        Valider.addActionListener((ActionEvent evt) -> {
        	boolean pass = true;
        	
        	// Vérifier si les valeurs saisies sont conformes
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
        	
        	// Action effectue si les valeurs sont conformes
        	if (pass) {
        		double graddouble = Double.parseDouble(gradtext.getText());
        		double xmaxdouble = Double.parseDouble(xmaxtext.getText());
        		int realEch = (int)((panelDrawing.getWidth()-40)/(2*xmaxdouble));
        		
        		// Setters de Drawing et de Conversion
        		panelDrawing.setEch(realEch, (int)xmaxdouble, (int)graddouble);
        		Conversion.setEch(realEch);
        		panelDrawing.setMode("Axes");
        		panelDrawing.repaint();
        		
        		// Reactive le JRadioButton de la 2e page et change de page
        		Point.setEnabled(true);
        		panEch.setVisible(false);
        		Point.doClick();
        	}
        	
            });
        panEch.add(labelWrapper);
        panEch.add(xmax);
        panEch.add(grad);
        panEch.add(Valider);
    }
    
    // Creation de la page 2 : Choix des points
    private void genPanPoint() {
    	panPoint.setLayout(new GridLayout(0,1));
    	
    	// Texte "Coordonées des Points"
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
            
           // Action effectue si les valeurs sont conformes
           if (pass) {
        	   // Conversion des valeurs saisies en double
        	   double qA= ((double)(int)(Double.parseDouble(qtextA.getText())*100))/100;
        	   double xA= ((double)(int)(Double.parseDouble(xtextA.getText())*100))/100;
        	   double yA= ((double)(int)(Double.parseDouble(ytextA.getText())*100))/100;
        	   double qB= ((double)(int)(Double.parseDouble(qtextB.getText())*100))/100;
        	   double xB= ((double)(int)(Double.parseDouble(xtextB.getText())*100))/100;
        	   double yB= ((double)(int)(Double.parseDouble(ytextB.getText())*100))/100;
        	   double xM= ((double)(int)(Double.parseDouble(xtextM.getText())*100))/100;
        	   double yM= ((double)(int)(Double.parseDouble(ytextM.getText())*100))/100;
        	   
        	   // Enregistrement des valeurs saisies dans A,B et M
        	   p.getA().setQ(qA*1e-9);
        	   p.getA().getPoint().setX(xA);
        	   p.getA().getPoint().setY(yA);
        	   p.getB().setQ(qB*1e-9);
        	   p.getB().getPoint().setX(xB);
        	   p.getB().getPoint().setY(yB);
        	   p.getM().setX(xM);
        	   p.getM().setY(yM);
        	   
        	   // Calcul du champ electrique (utilise pour le gradient)
        	   ElectricField Field = new ElectricField(p);
        	   pot = Field.getElectricField();
        	   plus = Field.getElectricFieldPlus();
        	   minus = Field.getElectricFieldMinus();
        	   
        	   //Calcul du pot en M
        	   p.calculPotentiel(p.getA(), p.getB(), p.getM());
        	   System.out.println("Le potentiel en M est: "+p.getV());
        	   
        	   // Setters de Drawing
        	   panelDrawing.setMode("Clas");
        	   panelDrawing.setPot(pot, plus, minus);
        	   panelDrawing.repaint();
        	   
        	   // Reactive le JRadioButton de la 3e page et changement de page
        	   panPoint.setVisible(false);
               Para.setEnabled(true);
               Para.doClick();
           }
         
            });
        
    }

    // Creation de la page 3 : Choix des parametres graphiques
    private void genPanPara() {
    	panPara.setLayout(new BoxLayout(panPara, BoxLayout.Y_AXIS));
    	
    	// Texte "Options d'Affichage"
    	JPanel labelWrapper = new JPanel();
    	JLabel label = new JLabel("Options d'Affichage", SwingConstants.CENTER);
    	label.setBackground(Color.BLACK);
    	label.setOpaque(true);
    	label.setForeground(Color.WHITE);
    	labelWrapper.add(label);
    	panPara.add(labelWrapper);
    	
    	// Groupe de JRadioButton (choix des parametres)
    	JPanel Button = new JPanel();
    	Button.setLayout(new BoxLayout(Button, BoxLayout.Y_AXIS));
    	Button.setAlignmentX(Component.CENTER_ALIGNMENT);
    	JRadioButton Gradient = new JRadioButton("Gradient");
        JRadioButton Champ = new JRadioButton("Champ Electrique");
        JRadioButton Equipote = new JRadioButton("Equipotentiel");
        JRadioButton ShowCoord = new JRadioButton("Afficher les Coordonnées");
        JRadioButton ShowM = new JRadioButton("Afficher M");
        Button.add(Gradient);
        Button.add(Champ);
        Button.add(Equipote);
        Button.add(ShowCoord);
        Button.add(ShowM);
        Button.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        panPara.add(Button);
        
        // Creation des sous-panneaux de parametre
        JPanel panGradient = new JPanel();
        JPanel panChamp = new JPanel();
        JPanel panEquipote = new JPanel();
        panGradient.setVisible(false);
    	panChamp.setVisible(false);
    	panEquipote.setVisible(false);
    	
    	// Listener des JRadioButton
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
    	 ChangeListener ShowCoordselectedListener = new ChangeListener() {
    		@Override
    		public void stateChanged(ChangeEvent e) {
    			AbstractButton aButton = (AbstractButton)e.getSource();
                ButtonModel aModel = aButton.getModel();
                ShowCoordselected = aModel.isSelected();
    		}
            };
        ShowCoord.addChangeListener(ShowCoordselectedListener);
        ChangeListener ShowMselectedListener = new ChangeListener() {
    		@Override
    		public void stateChanged(ChangeEvent e) {
    			AbstractButton aButton = (AbstractButton)e.getSource();
                ButtonModel aModel = aButton.getModel();
                ShowMselected = aModel.isSelected();
    		}
            };
        ShowM.addChangeListener(ShowMselectedListener);
        
        Gradient.addActionListener((ActionEvent evt) -> {
        	panGradient.setVisible(Gradientselected);
        	if (!Gradientselected) panelDrawing.setMode("Clas");
            panChamp.setVisible(Champselected);
        	panEquipote.setVisible(Equipoteselected);
        	panelDrawing.setShowCoord(ShowCoordselected);
        	panelDrawing.setShowM(ShowMselected);
        	panelDrawing.repaint();
            });
        Champ.addActionListener((ActionEvent evt) -> {
        	panGradient.setVisible(Gradientselected);
            panChamp.setVisible(Champselected);
        	panEquipote.setVisible(Equipoteselected);
        	panelDrawing.setShowCoord(ShowCoordselected);
        	panelDrawing.setShowM(ShowMselected);
        	panelDrawing.repaint();
            });
        Equipote.addActionListener((ActionEvent evt) -> {
        	panGradient.setVisible(Gradientselected);
            panChamp.setVisible(Champselected);
        	panEquipote.setVisible(Equipoteselected);
        	panelDrawing.setShowCoord(ShowCoordselected);
        	panelDrawing.setShowM(ShowMselected);
        	panelDrawing.repaint();
            });
        ShowCoord.addActionListener((ActionEvent evt) -> {
        	panGradient.setVisible(Gradientselected);
            panChamp.setVisible(Champselected);
        	panEquipote.setVisible(Equipoteselected);
        	panelDrawing.setShowCoord(ShowCoordselected);
        	panelDrawing.setShowM(ShowMselected);
        	panelDrawing.repaint();
            });
        ShowM.addActionListener((ActionEvent evt) -> {
        	panGradient.setVisible(Gradientselected);
            panChamp.setVisible(Champselected);
        	panEquipote.setVisible(Equipoteselected);
        	panelDrawing.setShowCoord(ShowCoordselected);
        	panelDrawing.setShowM(ShowMselected);
        	panelDrawing.repaint();
            });
        
    	// Sous-panneau Gradient
        panGradient.setLayout(new GridLayout(0,1));
        
        // Texte "Gradient"
        JPanel labelWrapperGradient = new JPanel();
    	JLabel labelGradient = new JLabel("Gradient", SwingConstants.CENTER);
    	labelGradient.setBackground(Color.BLACK);
    	labelGradient.setOpaque(true);
    	labelGradient.setForeground(Color.WHITE);
    	labelWrapperGradient.add(labelGradient);
    	panGradient.add(labelWrapperGradient);
    	
    	// Text "Nombre de Couleurs" et de la zone de saisie associé
    	JPanel panGradient1 = new JPanel();
        panGradient1.add(new JLabel("Nombre de Couleurs :"));
    	JFormattedTextField nbrColor = new JFormattedTextField() ;
    	nbrColor.setBackground(getBackground());
    	nbrColor.setBorder(BorderFactory.createLineBorder(getBackground(), 2));
    	nbrColor.setHorizontalAlignment(JTextField.CENTER);
    	nbrColor.setText("25");
    	nbrColor.setColumns(3);
    	panGradient1.add(nbrColor);
    	
    	// JButton ValiderNbrColor
    	JButton ValiderNbrColor = new JButton("Valider"); 
    	
    	// Listener de ValiderNbrColor
    	ValiderNbrColor.addActionListener((ActionEvent evt) -> {
    		boolean passGradient = true;
    		if (Gradientselected) {
            	// Vérifier si les valeurs saisies sont conformes
            	try{Double.parseDouble(nbrColor.getText());}
            	catch(NumberFormatException a){
            		passGradient=false;
            		nbrColor.setText("25");
            		System.out.println("le xmax est non conforme");}
            	
            	// Action effectue si les valeurs sont conformes
            	if (passGradient) {
            		panelDrawing.setNbrColor((int)Double.parseDouble(nbrColor.getText()));
            		panelDrawing.setMode("Grad");
            		panelDrawing.repaint();
            	}
    		}
            });
    	panGradient.add(panGradient1);
    	panGradient.add(ValiderNbrColor);
    	panPara.add(panGradient);
    	
    	// Sous-panneau Champ
    	
    	// Sous-panneau Equipote
    }
    
    // Instancie les differents panneaux (par MyPanel)
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