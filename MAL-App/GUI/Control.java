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
    public boolean gradientSelected=false;
    public boolean champSelected=false;
    public boolean equipoteSelected=false;
    public boolean champLigneSelected=false;
    public boolean affCoordSelected=false;
    public boolean affMSelected=false;
    public boolean affQSelected=false;
    public boolean affChampLigneDirection=false;
    public boolean gradientCurseurSelected=false;
    
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
           Zoom.add(ZoomIn);
           Zoom.add(ZoomOut);
           
           
       	// Bouton Valider
           JButton ValiderEch = new JButton("Valider"); 
           
        // Listener de Zoom
           ZoomIn.addActionListener((ActionEvent evt) -> {
        	   Container a = new Container();
        	   a.setBounds(0, 0, panelDrawing.getWidth(), panelDrawing.getHeight());
        	   if (a.contains(Conversion.doublepixelX(p.getA().getPoint().getX()*2), Conversion.doublepixelY(p.getA().getPoint().getY()*2)) && a.contains(Conversion.doublepixelX(p.getB().getPoint().getX()*2), Conversion.doublepixelY(p.getB().getPoint().getY()*2))) {
        		   zoomAction = true;
                  	Ech2.setText(String.valueOf(grad/2));
              		Ech3.setText(String.valueOf(xmax/2));
              		ValiderEch.doClick();
        	   }
        	   else ZoomIn.setEnabled(false);
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
        		grad = Math.abs(Ech2.getTextDouble());
        		xmax = Math.abs(Ech3.getTextDouble());
        		
        		if (grad==0) grad=1;
        		if (xmax==0) xmax=1;
        		
        		Ech2.setText(String.valueOf(grad));
        		Ech3.setText(String.valueOf(xmax));
        		
        		ech = (panelDrawing.getWidth()-40)/(2*xmax);
        		
        		// Setters de Drawing et de Conversion
        		panelDrawing.repaint();
        		Conversion.setEch(ech);
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
               panelHelp.setText("Cliquez sur l'icone enregistrer pour sauvegarder une image du graphe. Cliquez sur la rubrique de votre choix");
           }
           panelDrawing.repaint();
            });
        
    }

    // Creation de la page 3 : Choix des parametres graphiques
    private void genPanPara() {
    	panPara.setLayout(new GridBagLayout());
    	GridBagConstraints gbc = new GridBagConstraints();
        gbc.weightx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
    	
    	
    	// Sous-panneau Gradient
    	
    	
    	// Text "Nombre de Couleurs" et de la zone de saisie associé
    	EntryABM Para3 = new EntryABM("Nombre de Couleurs :", "25", false);
    	Para3.getTextPanel().setVisible(false);
    	
    	// JButton ValiderNbrColor
    	JButton validerNbrCouleur = new JButton("Valider"); 
    	validerNbrCouleur.setVisible(false);
    	
    	validerNbrCouleur.addActionListener((ActionEvent evt) -> {
    		if (gradientSelected) {
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
    	
    	// Potentiel précis
        JRadioButton affCurseur = new JRadioButton("Potentiel precis");
        affCurseur.setVisible(false);
        
        affCurseur.addActionListener((ActionEvent evt) -> {
            if (gradientCurseurSelected) panelHelp.setText("Appuyez sur le graphique pour un potentiel précis");
            if (!gradientCurseurSelected) panelHelp.setText("");

        	panelDrawing.repaint();
            });
    	ChangeListener gradientCurseurSelectedListener = new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				AbstractButton aButton = (AbstractButton)e.getSource();
    	        ButtonModel aModel = aButton.getModel();
    	        gradientCurseurSelected = aModel.isSelected();
			}
    	    };
    	affCurseur.addChangeListener(gradientCurseurSelectedListener);
    	
    	
    	// Sous-panneau Equipote
    	
    	
    	// Saisie des points M0
    	Para8 = new EntryABM("", false, p);

        JPanel MXYequi = new JPanel(new GridBagLayout());
        JLabel nbrMtext1 = new JLabel("M"+nbrMintEqui);
        MXYequi.add(nbrMtext1);
        MXYequi.add(Para8.getXY());
        MXYequi.setVisible(false);
        
        // Bouton Valider
        JButton Valider1 = new JButton("Valider"); 
        Valider1.setVisible(false);
        
        Valider1.addActionListener((ActionEvent evt) -> {
        	boolean pass=true;
        	pass=Para8.check("M0");
            if (pass) {
            	MCoordEqui[nbrMintEqui][0]=Para8.getX();
            	MCoordEqui[nbrMintEqui][1]=Para8.getY();
            	nbrMintEqui++;
            	Para8.setX(null);
        		Para8.setY(null);
            	nbrMtext1.setText("M"+nbrMintEqui);
            	panelDrawing.repaint();
            }
        });
        
        // Bouton Retour
        JButton Retour = new JButton("Retour"); 
        Retour.setVisible(false);
        
        Retour.addActionListener((ActionEvent evt) -> {
        	if (nbrMintEqui>0) {
        		nbrMintEqui--;
        		nbrMtext1.setText("M"+nbrMintEqui);
        		Para8.setX(MCoordEqui[nbrMintEqui][0]);
        		Para8.setY(MCoordEqui[nbrMintEqui][1]);
        		panelDrawing.repaint();
        	}
            
        });
        
    	
    	// Sous-panneau Field
        
        
    	// Text "Densite vectoriel" et de la zone de saisie associé
    	EntryABM Para5 = new EntryABM("Densite vectoriel :", "30", false);
    	Para5.getTextPanel().setVisible(false);
    	
    	// Text "Longueur des vecteurs" et de la zone de saisie associé
    	EntryABM Para6 = new EntryABM("Longueur des vecteurs :", "20", false);
    	Para6.getTextPanel().setVisible(false);
    	
    	// JButton ValiderDensiteLongueur
    	JButton ValiderDensiteLongueur = new JButton("Valider"); 
    	
    	
    	// Listener de ValiderDensiteLongueur
    	ValiderDensiteLongueur.addActionListener((ActionEvent evt) -> {
    		boolean passField1=true, passField2=true;
    		if (champSelected) {
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
    	ValiderDensiteLongueur.setVisible(false);
    	
    
    // Sous-panneau FieldLines
    	
    	
    	// Saisie des points M0
    	Para10 = new EntryABM("", false, p);

        JPanel MXYfield = new JPanel(new GridBagLayout());
        JLabel nbrMtext2 = new JLabel("M"+nbrMintFieldLines+"'");
        MXYfield.add(nbrMtext2);
        MXYfield.add(Para10.getXY());
        MXYfield.setVisible(false);
        
        // Bouton Valider
        JButton Valider2 = new JButton("Valider"); 
        Valider2.setVisible(false);
        
        Valider2.addActionListener((ActionEvent evt) -> {
        	boolean pass=true;
        	pass=Para10.check("M0");
            if (pass) {
            	MCoordFieldLines[nbrMintFieldLines][0]=Para10.getX();
            	MCoordFieldLines[nbrMintFieldLines][1]=Para10.getY();
            	nbrMintFieldLines++;
            	Para10.setX(null);
        		Para10.setY(null);
            	nbrMtext2.setText("M"+nbrMintFieldLines+"'");
            	panelDrawing.repaint();
            }
        });
        
        // Bouton Retour
        JButton Retour1 = new JButton("Retour");
        
        Retour1.setVisible(false);
    	
        Retour1.addActionListener((ActionEvent evt) -> {
        	if (nbrMintFieldLines>0) {
        		nbrMintFieldLines--;
        		nbrMtext2.setText("M"+nbrMintFieldLines);
        		Para10.setX(MCoordFieldLines[nbrMintFieldLines][0]);
        		Para10.setY(MCoordFieldLines[nbrMintFieldLines][1]);
        		panelDrawing.repaint();
        	}
            
        });
        
     // Afficher la direction des lignes de champ
        JRadioButton FieldLinesDirection = new JRadioButton("Direction lignes de champ");
        
        ChangeListener FieldLinesDirectionselectedListener = new ChangeListener() {
    		@Override
    		public void stateChanged(ChangeEvent e) {
    			AbstractButton aButton = (AbstractButton)e.getSource();
                ButtonModel aModel = aButton.getModel();
                affChampLigneDirection = aModel.isSelected();
                panelDrawing.repaint();
    		}
            };
        FieldLinesDirection.addChangeListener(FieldLinesDirectionselectedListener);
        FieldLinesDirection.setVisible(false);

    	// Groupe de JRadioButton (choix des parametres)
    	JPanel buttonPot = new JPanel();
    	buttonPot.setLayout(new GridBagLayout());
    	buttonPot.setAlignmentX(Component.LEFT_ALIGNMENT);
    	JRadioButton gradient = new JRadioButton("Gradient");
        JRadioButton equipote = new JRadioButton("Équipotentielle");
        buttonPot.add(gradient,gbc);
        buttonPot.add(Para3.getTextPanel(),gbc);
        buttonPot.add(validerNbrCouleur,gbc);
        buttonPot.add(affCurseur,gbc);
        buttonPot.add(equipote,gbc);
        buttonPot.add(MXYequi,gbc);
        buttonPot.add(Valider1,gbc);
        buttonPot.add(Retour,gbc);
        buttonPot.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        
    	JPanel buttonCha = new JPanel();
    	buttonCha.setLayout(new GridBagLayout());
    	buttonCha.setAlignmentX(Component.LEFT_ALIGNMENT);
    	JRadioButton champ = new JRadioButton("Champ");
        JRadioButton champLigne = new JRadioButton("Ligne de Champ");
        buttonCha.add(champ,gbc);
        buttonCha.add(Para5.getTextPanel(),gbc);
        buttonCha.add(Para6.getTextPanel(),gbc);
        buttonCha.add(ValiderDensiteLongueur,gbc);
        buttonCha.add(champLigne,gbc);
        buttonCha.add(MXYfield,gbc);
        buttonCha.add(Valider2,gbc);
        buttonCha.add(Retour1,gbc);
        buttonCha.add(FieldLinesDirection,gbc);
        buttonCha.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        
    	JPanel buttonAut = new JPanel();
    	buttonAut.setLayout(new GridBagLayout());
    	buttonAut.setAlignmentX(Component.LEFT_ALIGNMENT);
    	JRadioButton affCoord = new JRadioButton("Afficher les Coordonnées");
        JRadioButton affQ = new JRadioButton("Afficher les Charges");
        buttonAut.add(affCoord,gbc);
        buttonAut.add(affQ,gbc);
        buttonAut.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));

    	// Texte "Potentiel:"
    	EntryABM paraPot = new EntryABM("Potentiel:", "", true);
    	panPara.add(paraPot.getTextPanel(), gbc);
    	panPara.add(buttonPot, gbc);
    	
    	// Texte "Champ:"
    	EntryABM paraCha = new EntryABM("Champ:", "", true);
    	panPara.add(paraCha.getTextPanel(), gbc);
    	panPara.add(buttonCha, gbc);
    	
    	// Texte "Autre:"
    	EntryABM paraAut = new EntryABM("Autre:", "", true);
    	panPara.add(paraAut.getTextPanel(), gbc);
    	panPara.add(buttonAut, gbc);
    	
    	// Listener des JRadioButton
    	ChangeListener gradientSelectedListener = new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				AbstractButton aButton = (AbstractButton)e.getSource();
    	        ButtonModel aModel = aButton.getModel();
    	        gradientSelected = aModel.isSelected();
			}
    	    };
    	gradient.addChangeListener(gradientSelectedListener);
    	ChangeListener champSelectedListener = new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				AbstractButton aButton = (AbstractButton)e.getSource();
    	        ButtonModel aModel = aButton.getModel();
    	        champSelected = aModel.isSelected();
			}
    	    };
    	champ.addChangeListener(champSelectedListener);
    	ChangeListener equipoteSelectedListener = new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				AbstractButton aButton = (AbstractButton)e.getSource();
    	        ButtonModel aModel = aButton.getModel();
    	        equipoteSelected = aModel.isSelected();
			}
    	    };
    	 equipote.addChangeListener(equipoteSelectedListener);
    	 ChangeListener champLigneSelectedListener = new ChangeListener() {
 			@Override
 			public void stateChanged(ChangeEvent e) {
 				AbstractButton aButton = (AbstractButton)e.getSource();
     	        ButtonModel aModel = aButton.getModel();
     	        champLigneSelected = aModel.isSelected();
 			}
     	    };
     	 champLigne.addChangeListener(champLigneSelectedListener);
    	 ChangeListener affCoordSelectedListener = new ChangeListener() {
    		@Override
    		public void stateChanged(ChangeEvent e) {
    			AbstractButton aButton = (AbstractButton)e.getSource();
                ButtonModel aModel = aButton.getModel();
                affCoordSelected = aModel.isSelected();
    		}
            };
        affCoord.addChangeListener(affCoordSelectedListener);
        ChangeListener affQSelectedListener = new ChangeListener() {
    		@Override
    		public void stateChanged(ChangeEvent e) {
    			AbstractButton aButton = (AbstractButton)e.getSource();
                ButtonModel aModel = aButton.getModel();
                affQSelected = aModel.isSelected();
    		}
            };
        affQ.addChangeListener(affQSelectedListener);
        
        gradient.addActionListener((ActionEvent evt) -> {
        	if (!gradientSelected) {
        		panelHelp.setText("");
        		Para3.getTextPanel().setVisible(false);
                validerNbrCouleur.setVisible(false);
                affCurseur.setVisible(false);
        	}
        	else {
        		panelHelp.setText("Gradient: Vous pouvez choisir le nombre de couleurs");
                Para3.getTextPanel().setVisible(true);
                validerNbrCouleur.setVisible(true);
                affCurseur.setVisible(true);
        	}
        	panelDrawing.repaint();
            });
        champ.addActionListener((ActionEvent evt) -> {
            if (champSelected) {
            	panelHelp.setText("Champ électrique: Vous pouvez choisir la longueur et la densité des vecteurs");
            	Para5.getTextPanel().setVisible(true);
            	Para6.getTextPanel().setVisible(true);
            	ValiderDensiteLongueur.setVisible(true);
            }
            if (!champSelected) {
            	panelHelp.setText("");
            	Para5.getTextPanel().setVisible(false);
            	Para6.getTextPanel().setVisible(false);
            	ValiderDensiteLongueur.setVisible(false);
            }

        	panelDrawing.repaint();
            });
        equipote.addActionListener((ActionEvent evt) -> {
                if (equipoteSelected) {
                	panelHelp.setText("Equipotentielle: Sélectionnez un point M sur le graphe et validez. Vous pouvez aussi ajouter d'autres points ou en supprimer en cliquant sur retour ");
                	MXYequi.setVisible(true);
                	Valider1.setVisible(true);
                    Retour.setVisible(true);
                }
                if (!equipoteSelected) {
                	panelHelp.setText("");
                	MXYequi.setVisible(false);
                	Valider1.setVisible(false);
                    Retour.setVisible(false);
                }

                panelDrawing.repaint();
            });
        champLigne.addActionListener((ActionEvent evt) -> {
        	panelDrawing.repaint();
                if (champLigneSelected) {
                	panelHelp.setText("Lignes de champ: Sélectionnez un point M sur le graphe et validez. Vous pouvez aussi ajouter d'autres points ou en supprimer en cliquant sur retour. Vous pouvez aussi ajouter la direction des lignes de champ. ");
                	MXYfield.setVisible(true);
                    Valider2.setVisible(true);
                    Retour1.setVisible(true);
                    FieldLinesDirection.setVisible(true);
                }
                if (!champLigneSelected) {
                	panelHelp.setText("");
                	MXYfield.setVisible(false);
                    Valider2.setVisible(false);
                    Retour1.setVisible(false);
                    FieldLinesDirection.setVisible(false);
                }

            });
        affCoord.addActionListener((ActionEvent evt) -> {
                if (affCoordSelected) panelHelp.setText("Affichage des coordonnées des points");
                if (!affCoordSelected) panelHelp.setText("");
        	panelDrawing.repaint();
            });
        affQ.addActionListener((ActionEvent evt) -> {
            if (affQSelected) panelHelp.setText("Affichage des charges");
                if (!affQSelected) panelHelp.setText("");
        	panelDrawing.repaint();
            });
        JPanel size = new JPanel();
        size.setPreferredSize(new Dimension(240, 1));
        panPara.add(size);
}
    public void setpanelDrawing(Drawing panelDrawing){
        Control.panelDrawing = panelDrawing;
    }
    public void setpanelHelp(Help panelHelp){
        Control.panelHelp = panelHelp;
    }
}
