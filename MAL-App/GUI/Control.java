package GUI;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
    private Potential p;
    
    // Stockage du potentiel en chaques points
    private double[][] pot;
    double plus, minus;
    
    // Variable de Equipote
    int nbrMint=0;
    double[][] MCoord = new double[100][2];
    
    // Mise en commun des panneaux et des JRadioButton entre les methodes
    JPanel panEch;
    JPanel panPoint;
    JPanel panPara;
    JRadioButton Graph;
    JRadioButton Point;
    JRadioButton Para;
    
    // Variables d'etat utilises par PanPara
    boolean Gradientselected=false;
    boolean Fieldselected=false;
    boolean Equipoteselected=false;
    boolean FieldLinesselected=false;
    boolean ShowCoordselected=false;
    boolean ShowMselected=false;
    boolean ShowQselected=false;
    boolean showFieldLinesDirection=false;
    
    // Bouton enregistrer
    private JButton Enregistrer;
    
    // Constructeur de Control
    public Control(Potential p) {
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
    	EntryABM Ech1 = new EntryABM("Paramètres Graphique", "", true);
    	panEch.add(Ech1.getTextPanel());
    	
    	// Saisie de la graduation
    	EntryABM Ech2 = new EntryABM("Graduation =", "1", false);
    	panEch.add(Ech2.getTextPanel());
    	
    	// Saisie de xmax
    	EntryABM Ech3 = new EntryABM("Xmax =", "15", false);
    	panEch.add(Ech3.getTextPanel());
    	
    	// Bouton Valider
        JButton Valider = new JButton("Valider"); 
        
        // Listener de Valider
        Valider.addActionListener((ActionEvent evt) -> {
        	
        	// Vérifier si les valeurs saisies sont conformes
        	boolean pass1=true, pass2=true;
        	pass1 = Ech2.check("Text");
        	pass2 = Ech3.check("Text");
        	
        	// Action effectue si les valeurs sont conformes
        	if (pass1 && pass2) {
        		int grad = (int)Math.abs(Ech2.getTextDouble());
        		int xmax = (int)Math.abs(Ech3.getTextDouble());
        		
        		if (grad==0) grad=1;
        		if (xmax==0) xmax=1;
        		
        		Ech2.setText(grad);
        		Ech3.setText(xmax);
        		
        		int realEch = (int)((panelDrawing.getWidth()-40)/(2*xmax));
        		
        		// Setters de Drawing et de Conversion
        		panelDrawing.setEch(realEch, xmax, grad);
        		Conversion.setEch(realEch);
        		panelDrawing.setMode("Axes");
        		panelDrawing.repaint();
        		
        		// Reactive le JRadioButton de la 2e page et change de page
        		Point.setEnabled(true);
        		panEch.setVisible(false);
        		Point.doClick();
        	}
        	
            });
        panEch.add(Valider);
    }
    
    // Creation de la page 2 : Choix des points
    private void genPanPoint() {
    	panPoint.setLayout(new GridLayout(0,1));
    	
    	// Texte "Coordonées des Points"
    	EntryABM Point1 = new EntryABM("Coordonées des Points", "", true);
    	panPoint.add(Point1.getTextPanel());
    	
    	// Saisie A et B
        EntryABM AXYQ = new EntryABM("A", true, p);
        EntryABM BXYQ = new EntryABM("B", true, p);
        panPoint.add(AXYQ.getXYQ()); 
        panPoint.add(BXYQ.getXYQ());
        
        // Bouton Valider
        JPanel Bouton = new JPanel();
        JButton Valider = new JButton("Valider"); 
        Bouton.add(Valider);
        panPoint.add(Bouton);
    	
        // Action de Valider
        Valider.addActionListener((ActionEvent evt) -> {
            boolean passA=true, passB=true;
            // Vérifier si les valeurs saisies sont conformes
            passA = AXYQ.check("A");
            passB = BXYQ.check("B");
            
           // Action effectue si les valeurs sont conformes
           if (passA && passB) {
        	   System.out.println("passA et passB");
        	   // Conversion des valeurs saisies en double
        	   double qA= ((double)(int)(AXYQ.getQ()*100))/100;
        	   double xA= ((double)(int)(AXYQ.getX()*100))/100;
        	   double yA= ((double)(int)(AXYQ.getY()*100))/100;
        	   double qB= ((double)(int)(BXYQ.getQ()*100))/100;
        	   double xB= ((double)(int)(BXYQ.getX()*100))/100;
        	   double yB= ((double)(int)(BXYQ.getY()*100))/100;
        	   
        	   // Enregistrement des valeurs saisies dans A,B et M
        	   p.getA().setQ(qA);
        	   p.getA().getPoint().setX(xA);
        	   p.getA().getPoint().setY(yA);
        	   p.getB().setQ(qB);
        	   p.getB().getPoint().setX(xB);
        	   p.getB().getPoint().setY(yB);
        	   
        	   // Calcul du champ electrique (utilise pour le gradient)
        	   PotentialField Field = new PotentialField(p);
        	   pot = Field.getPotentialField();
        	   plus = Field.getPotentialFieldPlus();
        	   minus = Field.getPotentialFieldMinus();
        	   
        	   // Setters de Drawing
        	   if (Gradientselected) panelDrawing.setMode("Grad");
        	   else panelDrawing.setMode("Clas");
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
    	EntryABM Para1 = new EntryABM("Options d'Affichage", "", true);
    	panPara.add(Para1.getTextPanel());
    	
    	// Groupe de JRadioButton (choix des parametres)
    	JPanel Button = new JPanel();
    	Button.setLayout(new BoxLayout(Button, BoxLayout.Y_AXIS));
    	Button.setAlignmentX(Component.CENTER_ALIGNMENT);
    	JRadioButton Gradient = new JRadioButton("Gradient");
        JRadioButton Field = new JRadioButton("Champ Electrique");
        JRadioButton Equipote = new JRadioButton("Equipotentielle");
        JRadioButton FieldLines = new JRadioButton("Lignes de Champ");
        JRadioButton ShowCoord = new JRadioButton("Afficher les Coordonnées");
        JRadioButton ShowQ = new JRadioButton("Afficher les Charges");
        Button.add(Gradient);
        Button.add(Field);
        Button.add(Equipote);
        Button.add(FieldLines);
        Button.add(ShowCoord);
        Button.add(ShowQ);
        Button.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        panPara.add(Button);
        
        // Creation des sous-panneaux de parametre
        JPanel panGradient = new JPanel();
        JPanel panField = new JPanel();
        JPanel panEquipote = new JPanel();
        JPanel panFieldLines = new JPanel();
        panGradient.setVisible(false);
    	panField.setVisible(false);
    	panEquipote.setVisible(false);
    	panFieldLines.setVisible(false);
    	
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
    	ChangeListener FieldselectedListener = new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				AbstractButton aButton = (AbstractButton)e.getSource();
    	        ButtonModel aModel = aButton.getModel();
    	        Fieldselected = aModel.isSelected();
			}
    	    };
    	Field.addChangeListener(FieldselectedListener);
    	ChangeListener EquipoteselectedListener = new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				AbstractButton aButton = (AbstractButton)e.getSource();
    	        ButtonModel aModel = aButton.getModel();
    	        Equipoteselected = aModel.isSelected();
			}
    	    };
    	 Equipote.addChangeListener(EquipoteselectedListener);
    	 ChangeListener FieldLinesselectedListener = new ChangeListener() {
 			@Override
 			public void stateChanged(ChangeEvent e) {
 				AbstractButton aButton = (AbstractButton)e.getSource();
     	        ButtonModel aModel = aButton.getModel();
     	        FieldLinesselected = aModel.isSelected();
 			}
     	    };
     	 FieldLines.addChangeListener(FieldLinesselectedListener);
    	 ChangeListener ShowCoordselectedListener = new ChangeListener() {
    		@Override
    		public void stateChanged(ChangeEvent e) {
    			AbstractButton aButton = (AbstractButton)e.getSource();
                ButtonModel aModel = aButton.getModel();
                ShowCoordselected = aModel.isSelected();
    		}
            };
        ShowCoord.addChangeListener(ShowCoordselectedListener);
        ChangeListener ShowQselectedListener = new ChangeListener() {
    		@Override
    		public void stateChanged(ChangeEvent e) {
    			AbstractButton aButton = (AbstractButton)e.getSource();
                ButtonModel aModel = aButton.getModel();
                ShowQselected = aModel.isSelected();
    		}
            };
        ShowQ.addChangeListener(ShowQselectedListener);
        
        Gradient.addActionListener((ActionEvent evt) -> {
        	panGradient.setVisible(Gradientselected);
        	if (!Gradientselected) {
        		panelDrawing.setMode("Clas");
        		panelHelp.setText("Help Panel");
        	}
        	else {
        		panelDrawing.setMode("Grad");
        		panelHelp.setText("Appuyer sur le graphique pour un potentiel précis");
        	}
        	panelDrawing.repaint();
            });
        Field.addActionListener((ActionEvent evt) -> {
            panField.setVisible(Fieldselected);
            panelDrawing.setShowField(Fieldselected);
        	panelDrawing.repaint();
            });
        Equipote.addActionListener((ActionEvent evt) -> {
        	panEquipote.setVisible(Equipoteselected);
        	panelDrawing.setShowEquipote(Equipoteselected);
        	panelDrawing.repaint();
            });
        FieldLines.addActionListener((ActionEvent evt) -> {
        	panFieldLines.setVisible(FieldLinesselected);
        	panelDrawing.setShowFieldLines(FieldLinesselected);
        	panelDrawing.repaint();
            });
        ShowCoord.addActionListener((ActionEvent evt) -> {
        	panelDrawing.setShowCoord(ShowCoordselected);
        	panelDrawing.repaint();
            });
        ShowQ.addActionListener((ActionEvent evt) -> {
        	panelDrawing.setShowQ(ShowQselected);
        	panelDrawing.repaint();
            });
        
    	// Sous-panneau Gradient
        panGradient.setLayout(new GridLayout(0,1));
        
        // Texte "Gradient"
    	EntryABM Para2 = new EntryABM("Gradient", "", true);
    	panGradient.add(Para2.getTextPanel());
    	
    	// Text "Nombre de Couleurs" et de la zone de saisie associé
    	EntryABM Para3 = new EntryABM("Nombre de Couleurs :", "25", false);
    	panGradient.add(Para3.getTextPanel());
    	
    	// JButton ValiderNbrColor
    	JButton ValiderNbrColor = new JButton("Valider"); 
    	
    	// Listener de ValiderNbrColor
    	ValiderNbrColor.addActionListener((ActionEvent evt) -> {
    		if (Gradientselected) {
    			boolean passGradient = true;
            	// Vérifier si les valeurs saisies sont conformes
    			passGradient = Para3.check("Text");
            	
            	// Action effectue si les valeurs sont conformes
            	if (passGradient) {
            		int nbrColorSet = (int)Para3.getTextDouble();
            		if (nbrColorSet<10) {
            			nbrColorSet =10;
            			Para3.setText(nbrColorSet);
            		}
            		panelDrawing.setNbrColor(nbrColorSet);
            		panelDrawing.setMode("Grad");
            		panelDrawing.repaint();
            	}
    		}
            });
    	panGradient.add(ValiderNbrColor);
    	panPara.add(panGradient);
    	
    	// Sous-panneau Field
    	panField.setLayout(new GridLayout(0,1));
        
        // Texte "Champ Electrique"
    	EntryABM Para4 = new EntryABM("Champ Electrique", "", true);
    	panField.add(Para4.getTextPanel());
    	
    	// Text "Densite vectoriel" et de la zone de saisie associé
    	EntryABM Para5 = new EntryABM("Densite vectoriel :", "30", false);
    	panField.add(Para5.getTextPanel());
    	
    	// Text "Longueur des vecteurs" et de la zone de saisie associé
    	EntryABM Para6 = new EntryABM("Longueur des vecteurs :", "2", false);
    	panField.add(Para6.getTextPanel());
    	
    	// JButton ValiderDensiteLongueur
    	JButton ValiderDensiteLongueur = new JButton("Valider"); 
    	
    	
    	// Listener de ValiderDensite
    	ValiderDensiteLongueur.addActionListener((ActionEvent evt) -> {
    		boolean passField1=true, passField2=true;
    		if (Fieldselected) {
            	// Vérifier si les valeurs saisies sont conformes
    			passField1=Para5.check("Text");
    			passField2=Para6.check("Text");
    			
    			int densite = (int)Math.abs(Para5.getTextDouble());
    			int longueur = (int)Math.abs(Para6.getTextDouble());
        		if (densite==0) densite =1;
        		if (longueur==0) longueur =1;
        		Para5.setText(densite);
        		Para6.setText(longueur);
    			
            	// Action effectue si les valeurs sont conformes
            	if (passField1 && passField2) {
            		panelDrawing.setField(longueur, densite);
            		panelDrawing.setShowField(true);
            		panelDrawing.repaint();
            	}
    		}
            });
    	panField.add(ValiderDensiteLongueur);
    	panPara.add(panField);
    	
    	
    	// Sous-panneau Equipote
    	panEquipote.setLayout(new GridLayout(0,1));
        
        // Texte "Equipotentielle"
    	EntryABM Para7 = new EntryABM("Equipotentielle", "", true);
    	panEquipote.add(Para7.getTextPanel());
    	
    	// Saisie des points M0
    	EntryABM Para8 = new EntryABM("", false, p);

        JPanel MXY0 = new JPanel(new GridBagLayout());
        JLabel nbrMtext = new JLabel("M"+nbrMint);
        MXY0.add(nbrMtext);
        MXY0.add(Para8.getXY());
        panEquipote.add(MXY0);
        
        // Bouton Valider
        JPanel Bouton = new JPanel(new GridBagLayout());
        JButton Valider1 = new JButton("Valider"); 
        Bouton.add(Valider1);
        
        // Bouton Retour
        JButton Retour = new JButton("Retour"); 
        Bouton.add(Retour);
    	
        // Action d' Entrer
        Valider1.addActionListener((ActionEvent evt) -> {
        	boolean pass=true;
        	pass=Para8.check("M0");
            if (pass) {
            	MCoord[nbrMint][0]=Para8.getX();
            	MCoord[nbrMint][1]=Para8.getY();
            	System.out.println(MCoord[nbrMint][0]+" "+MCoord[nbrMint][1]);
            	nbrMint++;
            	Para8.setX(null);
        		Para8.setY(null);
            	nbrMtext.setText("M"+nbrMint);
            	panelDrawing.setEquipote(MCoord, nbrMint);
            	panelDrawing.repaint();
            }
        });
        
        // Action de Retour
        Retour.addActionListener((ActionEvent evt) -> {
        	if (nbrMint>0) {
        		nbrMint--;
        		nbrMtext.setText("M"+nbrMint);
        		Para8.setX(MCoord[nbrMint][0]);
        		Para8.setY(MCoord[nbrMint][1]);
        		panelDrawing.setEquipote(MCoord, nbrMint);
        		panelDrawing.repaint();
        	}
            
        });
        panEquipote.add(Bouton);
        panPara.add(panEquipote);
    
    // Sous-panneau FieldLines
	panFieldLines.setLayout(new GridLayout(0,1));
    
    // Texte "Lignes de Champ"
	EntryABM Para9 = new EntryABM("Lignes de Champ", "", true);
	panFieldLines.add(Para9.getTextPanel());
	
	// Saisie des points M
	JPanel PointM = new JPanel(new GridBagLayout());
	EntryABM Para10 = new EntryABM("M", false, p);
	PointM.add(Para10.getXY());
	panFieldLines.add(PointM);
	
    // Afficher la direction des lignes de champ
    JRadioButton FieldLinesDirection = new JRadioButton("Direction lignes de champ");
    ChangeListener FieldLinesDirectionselectedListener = new ChangeListener() {
		@Override
		public void stateChanged(ChangeEvent e) {
			AbstractButton aButton = (AbstractButton)e.getSource();
            ButtonModel aModel = aButton.getModel();
            showFieldLinesDirection = aModel.isSelected();
            panelDrawing.repaint();
		}
        };
    FieldLinesDirection.addChangeListener(FieldLinesDirectionselectedListener);
    panFieldLines.add(FieldLinesDirection);
    
    // Bouton Valider
    JButton Valider2 = new JButton("Valider"); 
    panFieldLines.add(Valider2);
    
    // Action d' Entrer
    Valider2.addActionListener((ActionEvent evt) -> {
       	boolean pass=true;
        pass=Para10.check("M");
        if (pass) {
            panelDrawing.setFieldLines(Para10.getX(), Para10.getY(), showFieldLinesDirection);
           	panelDrawing.repaint();
        }
    });
    panPara.add(panFieldLines);
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