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
    private static Help panelHelp;
    
    // Constucteur
    private Potential p;
    
    // Gradient de couleur
    public int nbrColor = 25;
	
	// Champ vecteurs electirque
	public int densite = 30;
	public int longueur = 20;

    // Selection de l'echelle
    public double xmax=15;
    public double grad=1;
	public double ech;
    private boolean zoomAction;
    
    // Variable de Equipote
    public int nbrMintEqui=0;
    public double[][] MCoordEqui = new double[100][2];
    
    // Variable de FieldLines
    public int nbrMintFieldLines=0;
    public double[][] MCoordFieldLines = new double[100][2];
    
    // Mise en commun des panneaux et des JRadioButton entre les methodes
    JPanel panEch;
    JPanel panPoint;
    JPanel panPara;
    JRadioButton Graph;
    JRadioButton Point;
    JRadioButton Para;
    
    // Variables d'etat utilises par PanPara
    public boolean Gradientselected=false;
    public boolean Fieldselected=false;
    public boolean Equipoteselected=false;
    public boolean FieldLinesselected=false;
    public boolean ShowCoordselected=false;
    public boolean ShowMselected=false;
    public boolean ShowQselected=false;
    public boolean showFieldLinesDirection=false;
    public boolean GradientCursorselected=false;
    
    // Variable saisie des points
    public EntryABM AXYQ, BXYQ, Para8, Para10;
    
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
            Enregistrer.setVisible(false);
            panelHelp.setText("Choisissez la taille du graphe");
            });
        Point.addActionListener((ActionEvent evt) -> {
        	panEch.setVisible(false);
            panPoint.setVisible(true);
            panPara.setVisible(false);
            Enregistrer.setVisible(false);
            panelHelp.setText("Placez les charges A et B et renseignez leurs valeurs");
            });
        Para.addActionListener((ActionEvent evt) -> {
        	panEch.setVisible(false);
            panPoint.setVisible(false);
            panPara.setVisible(true);
            Enregistrer.setVisible(true);
            panelHelp.setText("Cliquez sur l'icone enregistrer pour sauvegarder une image du graphe. Cliquez sur la rubrique de votre choix");
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

        // Bouton Zoom
           JPanel Zoom = new JPanel();
           JButton ZoomOut = new JButton("-"); 
           JButton ZoomIn = new JButton("+"); 
           ZoomIn.setEnabled(false);
           Zoom.add(ZoomIn);
           Zoom.add(ZoomOut);
           
           
       	// Bouton Valider
           JButton ValiderEch = new JButton("Valider"); 
           
        // Listener de Zoom
           ZoomIn.addActionListener((ActionEvent evt) -> {
           	zoomAction = true;
           	Ech2.setText(String.valueOf(grad/2));
           	if (xmax>15) {
           		xmax=xmax/2;
           		Ech3.setText(String.valueOf(xmax/2));
           	}
           	if (xmax==15) ZoomIn.setEnabled(false);
       		ValiderEch.doClick();
               });
           ZoomOut.addActionListener((ActionEvent evt) -> {
           	ZoomIn.setEnabled(true);
           	zoomAction = true;
           	Ech2.setText(String.valueOf(grad*2));
       		Ech3.setText(String.valueOf(xmax*2));
       		ValiderEch.doClick();
           	});
        
        // Listener de Valider
        ValiderEch.addActionListener((ActionEvent evt) -> {
        	// Vérifier si les valeurs saisies sont conformes
        	boolean pass1=true, pass2=true;
        	pass1 = Ech2.check("Text");
        	pass2 = Ech3.check("Text");
        	
        	// Action effectue si les valeurs sont conformes
        	if (pass1 && pass2) {
        		grad = (int)Math.abs(Ech2.getTextDouble());
        		xmax = (int)Math.abs(Ech3.getTextDouble());
        		
        		if (grad==0) grad=1;
        		if (xmax<15) xmax=15;
        		
        		Ech2.setText(String.valueOf(grad));
        		Ech3.setText(String.valueOf(xmax));
        		
        		ech = (panelDrawing.getWidth()-40)/(2*xmax);
        		
        		// Setters de Drawing et de Conversion
        		Conversion.setValue(ech, panelDrawing.getWidth(), panelDrawing.getHeight());
        		panelDrawing.repaint();
        		if (!zoomAction) {
                    panelHelp.setText("Placez les charges A et B et renseignez leurs valeurs");
            		
            		// Reactive le JRadioButton de la 2e page et change de page
            		Point.setEnabled(true);
            		panEch.setVisible(false);
            		Point.doClick();
        		}
        		zoomAction = false;
        	}
        	
            });
        
        panEch.add(Zoom);
        panEch.add(ValiderEch);
    }
    
    // Creation de la page 2 : Choix des points
    private void genPanPoint() {
    	panPoint.setLayout(new GridLayout(0,1));
    	
    	// Texte "Coordonées des Points"
    	EntryABM Point1 = new EntryABM("Coordonées des Points", "", true);
    	panPoint.add(Point1.getTextPanel());
    	
    	// Saisie A et B
        AXYQ = new EntryABM("A", true, p);
        BXYQ = new EntryABM("B", true, p);
        panPoint.add(AXYQ.getXYQ()); 
        panPoint.add(BXYQ.getXYQ());
        
        // Bouton Valider
        JPanel Bouton = new JPanel();
        JButton Valider = new JButton("Valider"); 
        Bouton.add(Valider);
        panPoint.add(Bouton);
        // Action de Valider
        Valider.addActionListener((ActionEvent evt) -> {
            boolean passA=false, passB=false;
            // Vérifier si les valeurs saisies sont conformes
            passA = AXYQ.check("A");
            passB = BXYQ.check("B");
           if (passA && passB) {
        	   // Reactive le JRadioButton de la 3e page et changement de page
        	   panPoint.setVisible(false);
               Para.setEnabled(true);
               Para.doClick();
               panelDrawing.forceUpdate=true;
               panelHelp.setText("Cliquez sur l'icone enregistrer pour sauvegarder une image du graphe. Cliquez sur la rubrique de votre choix");
           }
           panelDrawing.repaint();
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
        	if (!Gradientselected) panelHelp.setText("");
        	else panelHelp.setText("Gradient: Vous pouvez choisir le nombre de couleurs");
        	panelDrawing.repaint();
            });
        Field.addActionListener((ActionEvent evt) -> {
            panField.setVisible(Fieldselected);
            if (Fieldselected) panelHelp.setText("Champ électrique: Vous pouvez choisir la longueur et la densité des vecteurs");
            if (!Fieldselected) panelHelp.setText("");

        	panelDrawing.repaint();
            });
        Equipote.addActionListener((ActionEvent evt) -> {
        	panEquipote.setVisible(Equipoteselected);
                if (Equipoteselected) panelHelp.setText("Equipotentielle: Sélectionnez un point M sur le graphe et validez. Vous pouvez aussi ajouter d'autres points ou en supprimer en cliquant sur retour ");
                if (!Equipoteselected) panelHelp.setText("");

                panelDrawing.repaint();
            });
        FieldLines.addActionListener((ActionEvent evt) -> {
        	panFieldLines.setVisible(FieldLinesselected);
        	panelDrawing.repaint();
                if (FieldLinesselected) panelHelp.setText("Lignes de champ: Sélectionnez un point M sur le graphe et validez. Vous pouvez aussi ajouter d'autres points ou en supprimer en cliquant sur retour. Vous pouvez aussi ajouter la direction des lignes de champ. ");
                if (!FieldLinesselected) panelHelp.setText("");

            });
        ShowCoord.addActionListener((ActionEvent evt) -> {
                if (ShowCoordselected) panelHelp.setText("Affichage des coordonnées des points");
                if (!ShowCoordselected) panelHelp.setText("");
        	panelDrawing.repaint();
            });
        ShowQ.addActionListener((ActionEvent evt) -> {
            if (ShowQselected) panelHelp.setText("Affichage des charges");
                if (!ShowQselected) panelHelp.setText("");
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
    	
    	// Potentiel précis
        JRadioButton ShowCursor = new JRadioButton("Potentiel precis");
    	ShowCursor.addActionListener((ActionEvent evt) -> {
            if (GradientCursorselected) panelHelp.setText("Appuyez sur le graphique pour un potentiel précis");
            if (!GradientCursorselected) panelHelp.setText("");

        	panelDrawing.repaint();
            });
    	ChangeListener GradientCursorselectedListener = new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				AbstractButton aButton = (AbstractButton)e.getSource();
    	        ButtonModel aModel = aButton.getModel();
    	        GradientCursorselected = aModel.isSelected();
			}
    	    };
    	ShowCursor.addChangeListener(GradientCursorselectedListener);
    	
    	// Listener de ValiderNbrColor
    	ValiderNbrColor.addActionListener((ActionEvent evt) -> {
    		if (Gradientselected) {
    			boolean passGradient = true;
            	// Vérifier si les valeurs saisies sont conformes
    			passGradient = Para3.check("Text");
            	
            	// Action effectue si les valeurs sont conformes
            	if (passGradient) {
            		nbrColor = (int)Para3.getTextDouble();
            		if (nbrColor<10) {
            			nbrColor =10;
            			Para3.setText(String.valueOf(nbrColor));
            		}
            		panelDrawing.repaint();
            	}
    		}
            });
    	panGradient.add(ValiderNbrColor);
    	panGradient.add(ShowCursor);
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
    	EntryABM Para6 = new EntryABM("Longueur des vecteurs :", "20", false);
    	panField.add(Para6.getTextPanel());
    	
    	// JButton ValiderDensiteLongueur
    	JButton ValiderDensiteLongueur = new JButton("Valider"); 
    	
    	
    	// Listener de ValiderDensiteLongueur
    	ValiderDensiteLongueur.addActionListener((ActionEvent evt) -> {
    		boolean passField1=true, passField2=true;
    		if (Fieldselected) {
            	// Vérifier si les valeurs saisies sont conformes
    			passField1=Para5.check("Text");
    			passField2=Para6.check("Text");
    			
    			densite = (int)Math.abs(Para5.getTextDouble());
    			longueur = (int)Math.abs(Para6.getTextDouble());
        		if (densite==0) densite =1;
        		if (longueur==0) longueur =1;
        		Para5.setText(String.valueOf(densite));
        		Para6.setText(String.valueOf(longueur));
    			
            	// Action effectue si les valeurs sont conformes
            	if (passField1 && passField2) {
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
    	Para8 = new EntryABM("", false, p);

        JPanel MXYequi = new JPanel(new GridBagLayout());
        JLabel nbrMtext1 = new JLabel("M"+nbrMintEqui);
        MXYequi.add(nbrMtext1);
        MXYequi.add(Para8.getXY());
        panEquipote.add(MXYequi);
        
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
            	MCoordEqui[nbrMintEqui][0]=Para8.getX();
            	MCoordEqui[nbrMintEqui][1]=Para8.getY();
            	System.out.println(MCoordEqui[nbrMintEqui][0]+" "+MCoordEqui[nbrMintEqui][1]);
            	nbrMintEqui++;
            	Para8.setX(null);
        		Para8.setY(null);
            	nbrMtext1.setText("M"+nbrMintEqui);
            	panelDrawing.repaint();
            }
        });
        
        // Action de Retour
        Retour.addActionListener((ActionEvent evt) -> {
        	if (nbrMintEqui>0) {
        		nbrMintEqui--;
        		nbrMtext1.setText("M"+nbrMintEqui);
        		Para8.setX(MCoordEqui[nbrMintEqui][0]);
        		Para8.setY(MCoordEqui[nbrMintEqui][1]);
        		panelDrawing.repaint();
        	}
            
        });
        panEquipote.add(Bouton);
        panPara.add(panEquipote);
    
    // Sous-panneau FieldLines
        panFieldLines.setLayout(new GridLayout(0,1));
        
        // Texte "Equipotentielle"
    	EntryABM Para9 = new EntryABM("Lignes de Champ", "", true);
    	panFieldLines.add(Para9.getTextPanel());
    	
    	// Saisie des points M0
    	Para10 = new EntryABM("", false, p);

        JPanel MXYfield = new JPanel(new GridBagLayout());
        JLabel nbrMtext2 = new JLabel("M"+nbrMintFieldLines+"'");
        MXYfield.add(nbrMtext2);
        MXYfield.add(Para10.getXY());
        panFieldLines.add(MXYfield);
        
        // Bouton Valider
        JPanel BoutonFieldLines = new JPanel(new GridBagLayout());
        JButton Valider2 = new JButton("Valider"); 
        BoutonFieldLines.add(Valider2);
        
        // Bouton Retour
        JButton Retour1 = new JButton("Retour"); 
        BoutonFieldLines.add(Retour1);
    	
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
        
        // Action d' Entrer
        Valider2.addActionListener((ActionEvent evt) -> {
        	boolean pass=true;
        	pass=Para10.check("M0");
            if (pass) {
            	MCoordFieldLines[nbrMintFieldLines][0]=Para10.getX();
            	MCoordFieldLines[nbrMintFieldLines][1]=Para10.getY();
            	System.out.println(MCoordFieldLines[nbrMintFieldLines][0]+" "+MCoordFieldLines[nbrMintFieldLines][1]);
            	nbrMintFieldLines++;
            	Para10.setX(null);
        		Para10.setY(null);
            	nbrMtext2.setText("M"+nbrMintFieldLines+"'");
            	panelDrawing.repaint();
            }
        });
        
        // Action de Retour
        Retour1.addActionListener((ActionEvent evt) -> {
        	if (nbrMintFieldLines>0) {
        		nbrMintFieldLines--;
        		nbrMtext2.setText("M"+nbrMintFieldLines);
        		Para10.setX(MCoordFieldLines[nbrMintFieldLines][0]);
        		Para10.setY(MCoordFieldLines[nbrMintFieldLines][1]);
        		panelDrawing.repaint();
        	}
            
        });
        panFieldLines.add(BoutonFieldLines);
        panPara.add(panFieldLines);
        
}
    public void setpanelDrawing(Drawing panelDrawing){
        Control.panelDrawing = panelDrawing;
    }
    public void setpanelHelp(Help panelHelp){
        Control.panelHelp = panelHelp;
    }
}
